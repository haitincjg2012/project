package com.ph.shopping.common.core.other.aspect;

import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import org.apache.commons.logging.Log;
import com.ph.shopping.common.util.result.Result;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component  
@Aspect  
@Order(2)
public class AdviceExplosionproof {

	@Autowired
	private ICacheService cacheService;


	private static Log logger = LogFactory
			.getLog(AdviceExplosionproof.class);
    
	// 方法执行的前后调用  
    @Around("@annotation(com.ph.shopping.common.core.other.annotation.Explosionproof)")
    public Object around(ProceedingJoinPoint point) throws Throwable {  
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder  
                .getRequestAttributes()).getRequest(); 
        logger.info(Thread.currentThread().getName()+"防爆线程名称------------------------");
        Result result = new Result();

        Object objSign=request.getParameter("sign");
        if(objSign == null || objSign.equals("null") || objSign.equals("")){
        	return ResultUtil.getResult(RespCode.Code.FAIL);
        }
        Boolean num = cacheService.exists(objSign.toString());
        if(!num){
    	    this.cacheService.set(objSign.toString(), objSign.toString(), 10L, TimeUnit.SECONDS);//设置失效时间
    	    logger.info("方法:"+point.getSignature().getName()+"防爆通过，准备添加redis");
			Boolean flag = cacheService.exists(objSign.toString());
    	    if (flag){
    	    	logger.info("方法:"+point.getSignature().getName()+"添加redis成功，准备执行");
    	    	Object object = point.proceed();
    	    	Class<? extends Object> class1 = object.getClass();
    	    	Class<? extends Result> class2 = result.getClass();
    	    	if(class1 != class2){
    	    		return object;
    	    	}
    	    	result = (Result) object;
    	    	if(result.isSuccess()){
    	    		logger.info("方法:"+point.getSignature().getName()+"执行，返回失败，准备删除redis");
    	    		this.cacheService.remove(objSign);
    	    	}
    	    	return result;
    	    }else{
    	    	logger.info("方法:"+point.getSignature().getName()+"添加redis失败，拦截");
    			return ResultUtil.getResult(RespCode.Code.FAIL);
    	    }
        }else{
        	logger.info("方法:"+point.getSignature().getName()+"防爆处理，拦截");
			return ResultUtil.getResult(RespCode.Code.FAIL);
        }
    }  
}  