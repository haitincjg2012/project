package com.alqsoft.controller.pc.view.sendmsg;

import org.alqframework.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alqsoft.service.sendmsg.SendMsgByPhoneService;

/**
 * 
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年4月25日 下午1:52:34
 * 
 */
@RequestMapping("pc/view/code")
@RestController
public class SendMsgController {
	@Autowired
	private SendMsgByPhoneService sendMsgByPhoneService;

	@RequestMapping(value = "sendMsg")
	public Result SendMsgByPhone(@RequestParam("phone") String phone) {

		Result result = sendMsgByPhoneService.SendMsgByPhone(phone);
		return result;
	}

}
