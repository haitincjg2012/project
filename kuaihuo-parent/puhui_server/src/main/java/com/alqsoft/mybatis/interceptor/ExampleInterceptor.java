package com.alqsoft.mybatis.interceptor;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.alqsoft.mybatis.entity.user.MyUser;
/**
 * mybatis 拦截器
 * @author 张灿
 *
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class }) })
public class ExampleInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation arg0) throws Throwable {
		String methodName = arg0.getMethod().getName();
		if(methodName.equals("update")){
			Object parameter = arg0.getArgs()[1];
			if(parameter instanceof MyUser)
				((MyUser)parameter).setName("拦截成功");
		}
		return arg0.proceed();
	}

	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	@Override
	public void setProperties(Properties arg0) {
		System.out.println(arg0.getProperty("someProperty").toString());
	}
	
}
