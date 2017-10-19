package com.alqsoft.kuaidi100;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.entity.kuaidi100.KuaiDi100;
import com.alqsoft.entity.kuaidi100.KuaiDiData;
import com.alqsoft.init.InitParam;
import com.alqsoft.utils.kuaidi100.KuaiDi100Util;

/**
 * 
 * @Title: KuaiDi100Test.java
 * @Description: 快递100 测试类
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年3月26日 下午6:29:42
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class KuaiDi100Test {
	@Autowired
	private InitParam initParam;
	
	@Test
	public void kuaidiTest(){
		KuaiDi100 kuaiDi100 = new KuaiDi100Util().getKuaiDiJson(initParam.getProperties(), "ems",
					"5025410031104", "0", "1", "asc");
			if (kuaiDi100 != null) {
				List<KuaiDiData> kuaiDiDataList = kuaiDi100.getKuaiDiDataList();
				System.out.println(kuaiDiDataList.toString());
			}
		}

}
