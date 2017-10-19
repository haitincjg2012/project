package com.alqsoft.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alqsoft.anno.MemberAnno;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.member.MemberService;

/**
 * 
 * 
 * @author sunhuijie
 *
 * @date 2017年3月1日
 *
 */
@Component  
@Aspect  
@Order(1)
public class AdviceMemberLogin {  
	
	private static Log logger = LogFactory
			.getLog(AdviceMemberLogin.class);
	
	@Autowired
	private MemberService memberService;
    
	// 方法执行的前后调用  
    @Around("execution(* com.alqsoft.controller.mobile.after.*.*.*(..))") 
    public Object around(ProceedingJoinPoint point) throws Throwable {  
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder  
                .getRequestAttributes()).getRequest();
    	logger.info(Thread.currentThread().getName()+"登录=========线程名称===========");
    	Result result = new Result();
    	/*String requestURI = request.getRequestURI();
    	StringBuffer requestURL = request.getRequestURL();
    	String servletPath = request.getServletPath();
    	ServletContext servletContext = request.getServletContext();*/
    	String phone = request.getParameter("phone");
    	String uuid = request.getParameter("uuid");
    	//logger.info("phone:"+phone+"===============uuid:"+uuid+"=============requestURI:"+requestURI+"===============requestURL:"+requestURL+"==================servletPath:"+servletPath+"=====================servletContext:"+servletContext);
    	Map<String, Object> params = new HashMap<String, Object>();
		params.put("phone", phone);
		params.put("uuid", uuid);
		if(phone==null||phone.equals("")||uuid==null||uuid.equals("")){
			 result.setCode(101);
			 result.setMsg("用户未登录");
			 return result;
		}
		Member member = this.memberService.getMemberByIdAndUuid(params);
		if(member == null){
			 result.setCode(102);
			 result.setMsg("用户登录异常");
    		 return result;
		}
        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Annotation[][] methodAnnotations = method.getParameterAnnotations();
        int length=methodAnnotations.length;
        if(length>0){
	        for (int i=0;i<length;i++) {
	        	if(methodAnnotations[i].length>0){
		        	Annotation annotation=methodAnnotations[i][0];
		        	//访问目标方法的参数：
		        	Object[] args = point.getArgs();
		        	if(annotation.annotationType().getName().equals(MemberAnno.class.getName()))
		        	{
		        		 Member memberArg=(Member) args[i];
		        		 BeanUtils.copyProperties(memberArg, member);
		        	}
	        	}
			}
        }
        //执行方法
        Object object = point.proceed();
		return object;
    }  
}  