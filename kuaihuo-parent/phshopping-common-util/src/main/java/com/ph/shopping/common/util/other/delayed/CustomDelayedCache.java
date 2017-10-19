package com.ph.shopping.common.util.other.delayed;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @ClassName:  CustomDelayedCache   
 * @Description:自定义超时缓存   
 * @author: 李杰
 * @date:   2017年6月1日 下午2:47:09   
 * @param <K>
 * @param <V>  
 * @Copyright: 2017
 */
public class CustomDelayedCache<K,V> {
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomDelayedCache.class);

	// 最大错误次数
	private static final int ERROR_MAXNUM = 5;
	// 缓存数据对象
	private ConcurrentMap<K, V> cacheObjMap;
	// 超时队列对象
	private DelayQueue<CacheDelayd<CacheContent<K, V>>> dq;
	// error 计数
	private final AtomicInteger ai = new AtomicInteger(1);
	// 处理异常重试
	private final CyclicBarrier barrier = new CyclicBarrier(ERROR_MAXNUM, new Runnable() {

		@Override
		public void run() {
			// 释放资源
			destroy();
			LOG.warn("custom delayed cache handle error , error time max num greater than " + ERROR_MAXNUM);
		}

	});
	/**
	 * 默认 超时时常 5 分钟
	 */
	private volatile long time = 10 * 60 * 1000;
	/**
	 * 默认单位毫秒
	 */
	private volatile TimeUnit unit = TimeUnit.MILLISECONDS;

	public CustomDelayedCache() {
		this.cacheObjMap = new ConcurrentHashMap<K, V>();
		this.dq = new DelayQueue<CacheDelayd<CacheContent<K, V>>>();
		init();
	}

	public CustomDelayedCache(ConcurrentMap<K, V> cacheObjMap, DelayQueue<CacheDelayd<CacheContent<K, V>>> dq) {
		this.cacheObjMap = cacheObjMap;
		this.dq = dq;
		init();
	}

	public CustomDelayedCache(long time, TimeUnit unit) {
		this.time = time;
		this.unit = unit;
		this.cacheObjMap = new ConcurrentHashMap<K, V>();
		this.dq = new DelayQueue<CacheDelayd<CacheContent<K, V>>>();
		init();
	}

	public CustomDelayedCache(ConcurrentMap<K, V> cacheObjMap, DelayQueue<CacheDelayd<CacheContent<K, V>>> dq,
			long time, TimeUnit unit) {
		this.cacheObjMap = cacheObjMap;
		this.dq = dq;
		this.time = time;
		this.unit = unit;
		init();
	}
	/**
	 * 
	 * @Title: init   
	 * @Description: 初始化方法   
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	private void init() {
		
		Runnable cacheTask = new Runnable() {

			@Override
			public void run() {
				// 执行超时处理
				cacheCheck();
			}
		};
		final Thread daemonThread = new ThreadTask().newThread(cacheTask);
		// 设置为守护线程
		daemonThread.setDaemon(true);
		daemonThread.setName("custom delayed cache daemon");
		daemonThread.start();
	}

	/**
	 * 
	 * @ClassName:  ThreadTask   
	 * @Description:  
	 * @author: lijie
	 * @date:   2017年4月16日 下午12:35:00     
	 * @Copyright: 2017
	 */
	protected class ThreadTask implements ThreadFactory {

		@Override
		public Thread newThread(Runnable r) {
			Thread th = new Thread(r);
			th.setUncaughtExceptionHandler(new ThreadErrorTask());
			return th;
		}

	}
	/**
	 * 
	 * @ClassName:  ThreadErrorTask   
	 * @Description:线程异常处理   
	 * @author: 李杰
	 * @date:   2017年6月1日 下午2:49:29     
	 * @Copyright: 2017
	 */
	protected class ThreadErrorTask implements Thread.UncaughtExceptionHandler {

		@Override
		public void uncaughtException(Thread t, Throwable e) {
			LOG.error("CustomCache Handle error ", e);
			LOG.info("error time :" + ai.get());
			if (!t.isInterrupted()) {
				t.interrupt();
			}
			ai.incrementAndGet();
			// 出现错误时清空缓存 防止一直增长
			clear();
			// 重新初始化
			init();
			try {
				barrier.await();
			} catch (InterruptedException e1) {
				LOG.error(e1.getMessage(), e1);
			} catch (BrokenBarrierException e1) {
				LOG.error(e1.getMessage(), e1);
			}
		}

	}
	/**
	 * 
	 * @Title: cacheDaemonCheck   
	 * @Description: 处理超时   
	 * @param:       
	 * @return: void      
	 * @throws
	 */
	private void cacheCheck() {
		if (LOG.isInfoEnabled())
			LOG.info("cache service started.");

		for (;;) {
			try {
				CacheDelayd<CacheContent<K, V>> delayItem = dq.take();
				if (null != delayItem) {
					// 超时对象处理
					CacheContent<K, V> pair = delayItem.getContent();
					cacheObjMap.remove(pair.getName(), pair.getContent());
				}
			} catch (InterruptedException e) {
				if (LOG.isErrorEnabled())
					LOG.error("", e);
				break;
			}
		}

		if (LOG.isInfoEnabled())
			LOG.info("cache service stopped.");
	}

	/**
	 * 
	 * @Title: put   
	 * @Description: 添加缓存对象
	 * @param: @param key
	 * @param: @param value
	 * @param: @param time
	 * @param: @param unit      
	 * @return: void      
	 * @throws
	 */
	public void put(K key, V value) {
		V oldValue = cacheObjMap.put(key, value);
		if (null != oldValue)
			dq.remove(key);

		long nanoTime = TimeUnit.NANOSECONDS.convert(time, unit);
		dq.put(new CacheDelayd<CacheContent<K, V>>(new CacheContent<K, V>(key, value), nanoTime));
	}
	/**
	 * 
	 * @Title: get   
	 * @Description: 获取缓存对象   
	 * @param: @param key
	 * @param: @return      
	 * @return: V      
	 * @throws
	 */
	public V get(K key) {
		return cacheObjMap.get(key);
	}
	/**
	 * 
	 * @Title: clear   
	 * @Description: 清空缓存   
	 * @param:       
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	public void clear(){
		dq.clear();
		cacheObjMap.clear();
	}
	/**
	 * 
	 * @Title: size   
	 * @Description:缓存数据大小   
	 * @param: @return      
	 * @return: int
	 * @author：李杰      
	 * @throws
	 */
	public int size(){
		
		return cacheObjMap.size();
	}
	/**
	 * 
	 * @Title: destroy   
	 * @Description: 释放资源   
	 * @param:       
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	public void destroy() {
		cacheObjMap = null;
		dq = null;
	}
}