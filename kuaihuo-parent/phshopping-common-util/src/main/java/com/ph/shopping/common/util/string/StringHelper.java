package com.ph.shopping.common.util.string;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

	/**
	 * 
	 * @Title: renderString   
	 * @Description: 替换指定格式 (#{money},参数{"money":110})  
	 * @param: @param content
	 * @param: @param map
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	public static String renderString(String content, Map<String, String> map) {
		if (null != content && null != map) {
			Set<Entry<String, String>> sets = map.entrySet();
			for (Entry<String, String> entry : sets) {
				String regex = "\\#\\{" + entry.getKey() + "\\}";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(content);
				content = matcher.replaceAll(entry.getValue());
			}
		}
		return content;
	}
}
