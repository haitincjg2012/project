package com.ph.shopping.common.util.other.delayed;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
/**
 * 
 * @ClassName:  CacheDelayd   
 * @Description:超时限制队列   
 * @author: 李杰
 * @date:   2017年6月1日 下午2:29:53   
 * @param <T>  
 * @Copyright: 2017
 */
public class CacheDelayd<T> implements Delayed{
	/**
	 * 初始化时间
	 */
	private static final long INIT_TIME = System.nanoTime();
	/**
	 * 
	 * @Title: now   
	 * @Description: 得到当前最新时间   
	 * @param: @return      
	 * @return: long      
	 * @throws
	 */
	final static long now() {

		return System.nanoTime() - INIT_TIME;
	}

	/**
	 * 标识
	 */
	private static final AtomicLong sequencer = new AtomicLong(0);
	/**
	 * 超时时间
	 */
	private final long time;
	/**
	 * 内容信息
	 */
	private final T content;
	/**
	 * 标识number
	 */
	private final long sequenceNumber;
	
	
	public CacheDelayd(T content,long timeout) {
		this.time = now() + timeout;
        this.content = content;
        this.sequenceNumber = sequencer.getAndIncrement();
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	public int compareTo(Delayed other) {
		if (other == this) {
			return 0;
		}
		if (other instanceof CacheDelayd) {
			CacheDelayd cache = (CacheDelayd) other;
			long diff = time - cache.time;
			if (diff < 0)
				return -1;
			else if (diff > 0)
				return 1;
			else if (sequenceNumber < cache.sequenceNumber)
				return -1;
			else
				return 1;
		}
		long d = (getDelay(TimeUnit.NANOSECONDS) - other.getDelay(TimeUnit.NANOSECONDS));
		return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		
		return unit.convert(time - now(), TimeUnit.NANOSECONDS);
	}
	/**
	 * 
	 * @Title: getContent   
	 * @Description: 得到当前内容详情 
	 * @param: @return      
	 * @return: T      
	 * @throws
	 */
	public T getContent() {

		return this.content;
	}
}

