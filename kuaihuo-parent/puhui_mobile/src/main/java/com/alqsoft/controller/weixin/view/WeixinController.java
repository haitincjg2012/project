package com.alqsoft.controller.weixin.view;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alqsoft.init.InitParam;
import com.github.sd4324530.fastweixin.message.BaseMsg;
import com.github.sd4324530.fastweixin.message.TextMsg;
import com.github.sd4324530.fastweixin.message.req.BaseEvent;
import com.github.sd4324530.fastweixin.message.req.MenuEvent;
import com.github.sd4324530.fastweixin.message.req.TextReqMsg;
import com.github.sd4324530.fastweixin.servlet.WeixinControllerSupport;

/**
 * 
 * @Title: WeixinController.java
 * @Description: 微信入口控制器
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年1月7日 下午5:33:27
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 *
 */
@Controller
@RequestMapping("/weixin/view")
public class WeixinController  extends WeixinControllerSupport {
	@Autowired
	private InitParam initParam;
	
	@Override
	protected String getToken() {
		Map<String, String> map=initParam.getConstantMap();//获取常量值
		return map.get("WEIXIN_TOKEN").toString();
	}
	@Override
	protected BaseMsg handleSubscribe(BaseEvent event) {
		return  new TextMsg("欢迎关注xxxxxxxx公众平台!\n");
	}
	@Override
	protected BaseMsg handleTextMsg(TextReqMsg msg) {
		/*try{
		String content = msg.getContent();
		System.out.println("微信服务器返回值=============："+content);
		
		if(content.replaceAll("\\?", "？").equals("？")){//返回？显示主菜单
			return new TextMsg("我能帮助你？");
		}else{
			return new TextMsg("");
		}*/
		
		/*if("1".equals(content)){
			return new TextMsg("活动activity");
		}else if("2".equals(content)){
			return new TextMsg("搜索search");
		}else if("3".equals(content)){
			return new TextMsg(getResMsg());
		}else if("4".equals(content)){
			return new ImageMsg("tcOuHQv38fFA_bkoHWYArHReWD1Z6Q7wjlGoXhvpqZqp9TkY4B1w2BYlCsRgG0z1");
		}else if("5".equals(content)){
			Article article = new Article();
			article.setTitle("今天吃什么");
			article.setDescription("火锅猪脚鸡翅");
			article.setPicUrl("http://lilianweixin.sinaapp.com/image/IMG_1.JPG");
			article.setUrl("http://lilianweixin.sinaapp.com/test?param=啤酒炸鸡啦");
			NewsMsg newsMsg = new NewsMsg();
			newsMsg.add(article);
			System.out.println(newsMsg.toString());
			return newsMsg;
		} else if("6".equals(content)){
			Article article1 = new Article();
			article1.setTitle("早上吃什么");
			article1.setDescription("火锅猪脚鸡翅");
			article1.setPicUrl("http://lilianweixin.sinaapp.com/image/IMG_1.JPG");
			article1.setUrl("http://lilianweixin.sinaapp.com/test?param=火锅猪脚鸡翅啦");
			Article article2 = new Article();
			article2.setTitle("中午吃什么");
			article2.setDescription("火锅猪脚鸡翅");
			article2.setPicUrl("http://lilianweixin.sinaapp.com/image/IMG_1.JPG");
			article2.setUrl("http://lilianweixin.sinaapp.com/test?param=火锅猪脚鸡翅啦");
			Article article3 = new Article();
			article3.setTitle("晚上吃什么");
			article3.setDescription("火锅猪脚鸡翅");
			article3.setPicUrl("http://lilianweixin.sinaapp.com/image/IMG_1.JPG");
			article3.setUrl("http://lilianweixin.sinaapp.com/test?param=火锅猪脚鸡翅啦");
			NewsMsg newsMsg = new NewsMsg();
			newsMsg.add(article1);
			newsMsg.add(article2);
			newsMsg.add(article3);
			return newsMsg;
		}else{
			return new TextMsg("error");
		}*/
		/*}catch(WeixinException e){
			return new TextMsg("微信公众平台罢工啦！！");
		}*/
		return null;
	}
	
	private String getResMsg(){
		StringBuffer textMsg = new StringBuffer();
		textMsg.append("");
//		textMsg.append("功能开发中,敬请期待.....");
		/*textMsg.append("attention!\n");
		textMsg.append("\n\n");
		textMsg.append("1 activity\n");
		textMsg.append("2 search\n");
		textMsg.append("3 help\n");
		textMsg.append("4 image\n");
		textMsg.append("5 article\n");
		textMsg.append("6 articlelist\n");*/
		return textMsg.toString();
	}
	
	/**
	 * 自定义菜单点击事件
	 */
	@Override
	protected BaseMsg handleMenuClickEvent(MenuEvent event) {
		if("help".equals(event.getEventKey())){
			return new TextMsg(getResMsg());
		}
		return new TextMsg("##");
	}
	//使用安全模式时设置：密钥
	@Override 
	protected String getAESKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	 //使用安全模式时设置：APPID
	@Override
	protected String getAppId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
