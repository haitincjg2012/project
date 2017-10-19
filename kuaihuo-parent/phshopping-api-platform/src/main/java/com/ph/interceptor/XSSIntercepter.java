package com.ph.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @项目：phshopping-api-platform
 *
 * @描述：过滤XSS攻击脚本
 *
 * @作者： Mr.chang
 *
 * @创建时间：2017年3月25日
 *
 * @Copyright @2017 by Mr.chang
 */
public class XSSIntercepter extends HttpServletRequestWrapper{

	public XSSIntercepter(HttpServletRequest request) {
		super(request);
	}
	
	 public String[] getParameterValues(String parameter)
	    {
	        String[] values = super.getParameterValues(parameter);
	        if (values==null)
	        {
	            return null;
	        }
	        int count = values.length;
	        String[] encodedValues = new String[count];
	        for (int i = 0; i < count; i++)
	        {
	            encodedValues[i] = cleanXSS(values[i]);
	        }
	        return encodedValues;
	    }
	 
	    public String getParameter(String parameter)
	    {
	        String value = super.getParameter(parameter);
	        if (value == null)
	        {
	            return null;
	        }
	        return cleanXSS(value);
	    }
	 
	    public String getHeader(String name)
	    {
	        String value = super.getHeader(name);
	        if (value == null)
	            return null;
	        return cleanXSS(value);
	    }
	 
	    /**
	     * 清理带有xss脚本数据
	     * @param value
	     * @return
	     */
	    private String cleanXSS(String value)
	    {
	        value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
	        value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
	        value = value.replaceAll("'", "& #39;");
	        value = value.replaceAll("eval\\((.*)\\)", "");
	        value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
	        value = value.replaceAll("script", "");
	        return value;
	    }

}
