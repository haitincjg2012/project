package com.alqsoft.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import org.alqframework.utils.MyObjectUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.junit.Test;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.Lists;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-11-4 上午10:36:03
 * 
 */

public class BaseTest {
	@Test
	public void dsadfasd() {
		Map<String, String> valuesMap = new HashMap<String, String>();
		valuesMap.put("user_name", "张靠勤");
		valuesMap.put("goods_name", "振兵");
		StrSubstitutor sub = new StrSubstitutor(valuesMap);
		String templateString = "dear：${user_name}你关注的商品：${goods_name}已到货，由于此商品近期销售火爆，请及时购买！-------IWeb商场";
		System.out.println(sub.replace(templateString));
	}

	@Test
	public void dsdfsd() {
		
		
	}

	@Test
	public void sdfsd() {
		PolicyFactory policyFactory = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		System.out.println(policyFactory.sanitize("@"));
	}

	@Test
	public void asdfsdf() {
		Optional<String> optional=Optional.of("zhangkaoqin");
		System.out.println(optional.isPresent());
		System.out.println(optional.orElse("dsafasd"));
		System.out.println(optional.get());
	}
}
