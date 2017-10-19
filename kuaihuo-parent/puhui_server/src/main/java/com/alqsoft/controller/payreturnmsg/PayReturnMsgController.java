package com.alqsoft.controller.payreturnmsg;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alqsoft.entity.alreadycashorder.AlreadyCashOrder;
import com.alqsoft.service.alreadycashorder.AlreadyCashOrderService;
import com.alqsoft.service.payreturnmsg.PayReturnMsgService;
import com.alqsoft.utils.RSACommonUtils;
import com.alqsoft.utils.RSACommonUtils.CharSet;


/**
 * @ClassName  PayReturnMsgController
 * Date:     2017年4月1日  14:45:41 <br/>
 * @author   dinglanlan
 * @version  批发商提现回调
 * @see 	 
 */
@Controller
@RequestMapping("ydmvc/whoAreYou")
public class PayReturnMsgController {
	
	
	private static Log logger = LogFactory.getLog(PayReturnMsgController.class);
	
	@Autowired
	private AlreadyCashOrderService alreadyCashOrderService;
	@Autowired
	private PayReturnMsgService payReturnMsgService;
	
	@RequestMapping("shenhuiyuan.do")
	@ResponseBody
	public String returnMsg(Model model, HttpServletRequest request, HttpServletResponse response)throws IOException {
		
		logger.info("易联代付回调开始------------------------------------------------");
		
		String orderNum2 = request.getParameter("shen");
		String payStatus2 = request.getParameter("hui");//2成功  3失败
		String money2 = request.getParameter("yuan");
		
		if (null != orderNum2 && null != payStatus2 && null != money2) {
			String orderNum = RSACommonUtils.decryptByPublicKey(orderNum2, CharSet.UTF8);
			String payStatus = RSACommonUtils.decryptByPublicKey(payStatus2, CharSet.UTF8);
			String money = RSACommonUtils.decryptByPublicKey(money2, CharSet.UTF8);
			Integer count = alreadyCashOrderService.findAlreadyCashOrderByMerSeqID(orderNum);
			if (count>0) {
				logger.info("回调方法执行中,订单号"+orderNum+",此订单的状态:"+payStatus+",已处理的记录查到:"+count+"条,不在回调执行范围内,返回statuserror");
				return "success";
			}
			
			Map<String, String> map = new HashMap<>();
			map.put("orderNum", orderNum);
			map.put("payStatus", payStatus);
			map.put("money", money);
			String call = payReturnMsgService.verifySingNotifyForYPay(map);
			
			if ("success".equals(call)) {
				if ("success".equals(call)) {
					logger.info("订单号"+orderNum+"回调方法处理完毕,返回的字符串"+call+",通知Pay收到回调");
					try {
						logger.info("回调方法处理完毕,返回的字符串"+call+",通知Pay工程修改记录");
						AlreadyCashOrder alreadyCashOrder = new AlreadyCashOrder();
						alreadyCashOrder.setMerSeqId(orderNum);
						alreadyCashOrder.setUpdateTime(new Date());
						alreadyCashOrderService.saveAndModify(alreadyCashOrder);
					} catch (Exception e) {
						return "success";
					}
				}
				return "success";
			}
			
		}else {
			return "NullError";
		}
		return "error";
	}
}
