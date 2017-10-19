package com.ph.queue;

import com.alibaba.fastjson.JSON;
import com.ph.queue.request.OrderTaskRequest;
import com.ph.shopping.common.util.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * @项目：phshopping-api-platform
 * @描述：订单生产任务
 * @作者： Mr.zheng
 * @创建时间：2017-03-28
 * @Copyright @2017 by Mr.zheng
 */
@Service
@SuppressWarnings("rawtypes")
public class OrderTask {

    private static Logger logger = LoggerFactory.getLogger(OrderTask.class);

    private ExecutorService execute = Executors.newCachedThreadPool();
    //解决先进先出
    
	private BlockingQueue<OrderTaskRequest> queue = new LinkedBlockingQueue<>(10);
    //创建返回值线程对象
    private Callable<Result> call;

    @Autowired
    private OrderService orderService;

    /**
     *
     * @Title: callSubmit
     * @Description: 下单放入队列中
     * @param @return    参数
     * @return Result    返回类型
     * @throws
     */
    public Result callSubmit(OrderTaskRequest request){
        Result ret = null;
        OrderTaskRequest resultQueue = null;
        try {
            queue.put(request);
            resultQueue = queue.take();
            if (null != resultQueue) {
                call = new CallExec(resultQueue);
                Future<Result> fr = execute.submit(call);
                ret = fr.get();
            }
        } catch (Exception e) {
            logger.error("下单异常,e={}", e);
        }
        return ret;
    }

    //创建线程处理
    protected class CallExec implements Callable<Result>{

        //初始化请求参数
        private OrderTaskRequest request;

        public CallExec(OrderTaskRequest request){
            this.request = request;
        }

        @Override
        public Result call() throws Exception {
            logger.info( "订单请求参数，request={}", JSON.toJSONString(request));
            Result result = orderService.callBack(request);
            logger.info( "订单请求返回值，result={}", JSON.toJSONString(result));
            return result;
        }
    }

}
