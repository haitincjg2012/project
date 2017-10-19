package com.alqsoft.aspect;

import javax.servlet.http.HttpServletRequest;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alqsoft.init.InitParamPc;
import com.alqsoft.utils.JedisUtils;

@Component  
@Aspect  
@Order(2)
public class AdviceExplosionproof {  
  
	@Autowired
	private InitParamPc initParam;
	
	
	private static Log logger = LogFactory
			.getLog(AdviceExplosionproof.class);
    
	// 方法执行的前后调用  
    @Around("@annotation(com.alqsoft.anno.Explosionproof )")
    public Object around(ProceedingJoinPoint point) throws Throwable {  
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder  
                .getRequestAttributes()).getRequest(); 
        logger.info(Thread.currentThread().getName()+"防爆线程名称------------------------");
        JedisUtils jedisUtils = JedisUtils.getRu(initParam);
        Result result = new Result();
        
        Object objSign=request.getParameter("sign");
        if(objSign == null || objSign.equals("null") || objSign.equals("")){
        	return ResultUtils.returnError("请求异常");
        }
        Boolean num = jedisUtils.exists(objSign.toString());
        if(!num){
    	    String setex = jedisUtils.setex(objSign.toString(), objSign.toString(), 10);//设置失效时间
    	    logger.info("方法:"+point.getSignature().getName()+"防爆通过，准备添加redis");
    	    if (setex.equals("OK")){
    	    	logger.info("方法:"+point.getSignature().getName()+"添加redis成功，准备执行");
    	    	Object object = point.proceed();
    	    	Class<? extends Object> class1 = object.getClass();
    	    	Class<? extends Result> class2 = result.getClass();
    	    	if(class1 != class2){
    	    		return object;
    	    	}
    	    	result = (Result) object;
    	    	if(result.getCode().intValue() != 1){
    	    		logger.info("方法:"+point.getSignature().getName()+"执行，返回失败，准备删除redis");
    	    		Long hdel = jedisUtils.del(objSign.toString());
    	    		if(hdel.longValue() > 0L){
    	    			logger.info("方法:"+point.getSignature().getName()+"删除redis成功");
    	    		}else{
    	    			logger.info("方法:"+point.getSignature().getName()+"删除redis失败");
    	    		}
    	    	}
    	    	return result;
    	    }else{
    	    	logger.info("方法:"+point.getSignature().getName()+"添加redis失败，拦截");
    			return ResultUtils.returnError("提交异常,请联系管理员");
    	    }
        }else{
        	logger.info("方法:"+point.getSignature().getName()+"防爆处理，拦截");
			return ResultUtils.returnError("请勿重复提交");
        }
    }  
}  