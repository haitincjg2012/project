package com.alqsoft.controller.mobile.view.bankcard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName  BankCardController
 * Date:     2017年3月15日  17:45:41 <br/>
 * @author   dinglanlan
 * @version  银行
 * @see 	 
 */
@RequestMapping("mobile/view/bankcard")
@Controller
public class BankCardController {

	/**
	 * 获取银行名称列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getbankcardlist",method=RequestMethod.POST)
	public String getbankcardlist(){
		 StringBuffer sb=new StringBuffer();
		 sb.append("{'code':1,'msg':'获取成功','content':[");
		 sb.append("{\"id\":\"ICBC\",\"text\":\"中国工商银行\"},");
		 sb.append("{\"id\":\"ABC\",\"text\":\"中国农业银行\"},");
		 sb.append("{\"id\":\"BOC\",\"text\":\"中国银行\"},");
		 sb.append("{\"id\":\"CCB\",\"text\":\"中国建设银行\"},");
		 sb.append("{\"id\":\"PSBC\",\"text\":\"中国邮政储蓄银行\"},");
		 sb.append("{\"id\":\"CGB\",\"text\":\"广发银行\"},");
		 sb.append("{\"id\":\"CEB\",\"text\":\"中国光大银行\"},");
		 sb.append("{\"id\":\"BCM\",\"text\":\"交通银行\"},");
		 sb.append("{\"id\":\"CMB\",\"text\":\"招商银行\"},");
		 sb.append("{\"id\":\"CIB\",\"text\":\"兴业银行\"},");
		 sb.append("{\"id\":\"PAB\",\"text\":\"平安银行\"},");
		 sb.append("{\"id\":\"CITIC\",\"text\":\"中信银行\"},");
		 sb.append("{\"id\":\"CMBC\",\"text\":\"中国民生银行\"},");
		 sb.append("{\"id\":\"HXB\",\"text\":\"华夏银行\"},");
		 sb.append("{\"id\":\"BOB\",\"text\":\"北京银行\"},");
		 sb.append("{\"id\":\"BOS\",\"text\":\"上海银行\"},");
		 sb.append("{\"id\":\"NBCB\",\"text\":\"宁波银行\"},");
		 sb.append("{\"id\":\"HZB\",\"text\":\"杭州银行\"},");
		 sb.append("{\"id\":\"GZCB\",\"text\":\"广州银行\"},");
		 sb.append("{\"id\":\"JSB\",\"text\":\"江苏银行\"}");
	 
		 sb.append("]}");
		
		return sb.toString();
	}
	
	
}
