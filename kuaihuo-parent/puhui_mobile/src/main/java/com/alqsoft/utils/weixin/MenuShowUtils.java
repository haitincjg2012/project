package com.alqsoft.utils.weixin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sd4324530.fastweixin.api.MenuAPI;
import com.github.sd4324530.fastweixin.api.config.ApiConfig;
import com.github.sd4324530.fastweixin.api.entity.Menu;
import com.github.sd4324530.fastweixin.api.entity.MenuButton;
import com.github.sd4324530.fastweixin.api.enums.MenuType;
import com.github.sd4324530.fastweixin.api.enums.ResultType;

/**
 * @Title: MenuShowUtils.java
 * @Description: 微信菜单栏创建
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2014年12月30日 下午5:36:39
 * Copyright © 2013 厦门卓讯信息技术有限公司 All rights reserved.
 * 
 */
public class MenuShowUtils {
	private static final Logger LOG = LoggerFactory.getLogger(MenuShowUtils.class);
	
	
	/**
	 * 
	* @Title: createMenu 
	* @Description: 创建菜单栏目并返回相关状态
	* @param @param config
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String createMenu(ApiConfig config) {
        MenuAPI menuAPI = new MenuAPI(config);
        //先删除之前的菜单
        menuAPI.deleteMenu();
        
        Menu menu = new Menu();
        //###准备一级主菜单
        MenuButton mytigofoodMain = new MenuButton();
        mytigofoodMain.setType(MenuType.VIEW);
        mytigofoodMain.setName("去商城");
        mytigofoodMain.setUrl("http://www.tigofood.com/weixin/view/main/index");
        
        MenuButton activityMain = new MenuButton();
        activityMain.setType(MenuType.CLICK);
        activityMain.setKey("activity");
        activityMain.setName("活动");
        
       /* MenuButton helpMain = new MenuButton();
        helpMain.setType(MenuType.CLICK);
        helpMain.setKey("help");
        helpMain.setName("帮助");*/
        MenuButton linkManMain = new MenuButton();
        linkManMain.setType(MenuType.VIEW);
        linkManMain.setName("联系我们");
        linkManMain.setUrl("http://www.tigofood.com/weixin/view/member/turnContact-index");
        
        //准备子菜单 (最多5个)
        MenuButton activityMainPurchase = new MenuButton();
        activityMainPurchase.setName("预购");
        activityMainPurchase.setType(MenuType.VIEW);
        activityMainPurchase.setUrl("http://www.tigofood.com/weixin/view/goodyugou/goodYugouList");
        
        MenuButton activityMainRedPacket = new MenuButton();
        activityMainRedPacket.setName("送红包");
        activityMainRedPacket.setType(MenuType.VIEW);
        activityMainRedPacket.setUrl("http://www.tigofood.com/weixin/after/redpacket/turnRedSend");
        
        /*MenuButton activityMainSecondKill = new MenuButton();
        activityMainSecondKill.setName("秒杀");
        activityMainSecondKill.setType(MenuType.VIEW);
        activityMainSecondKill.setUrl("http://www.tigofood.com/weixin/view/goodseckill/goodSeckillList");*/
        MenuButton activityMainCard = new MenuButton();
        activityMainCard.setName("我的卡片");
        activityMainCard.setType(MenuType.VIEW);
        activityMainCard.setUrl("http://www.tigofood.com/weixin/after/yearcard/yearcard");
        
        MenuButton activityMainGift = new MenuButton();
        activityMainGift.setName("礼券使用");
        activityMainGift.setType(MenuType.VIEW);
        activityMainGift.setUrl("http://www.tigofood.com/weixin/after/giftticket/giftticket-use");
        
        
        /*MenuButton helpDownApp = new MenuButton();
        helpDownApp.setType(MenuType.VIEW);
        helpDownApp.setName("下载app");
        helpDownApp.setUrl("http://www.tigofood.com/download.jsp");
        
        MenuButton helpAboutUs = new MenuButton();
        helpAboutUs.setName("关于我们");
        helpAboutUs.setType(MenuType.VIEW);
        helpAboutUs.setUrl("http://www.tigofood.com/weixin/view/member/turnContact");
        
        MenuButton helpLinking = new MenuButton();
        helpLinking.setName("联系我们");
        helpLinking.setType(MenuType.VIEW);
        helpLinking.setUrl("http://www.tigofood.com/weixin/view/member/turnContact");*/
        
        //加入子菜单
        
        //活动
        List<MenuButton> activityList = new ArrayList<MenuButton>();
        activityList.add(activityMainPurchase);
        activityList.add(activityMainRedPacket);
//        activityList.add(activityMainSecondKill);
        activityList.add(activityMainCard);
        activityList.add(activityMainGift);
        
       //帮助
//        List<MenuButton> listHelp = new ArrayList<MenuButton>();
//        listHelp.add(helpDownApp);
//        listHelp.add(helpAboutUs);
//        listHelp.add(helpLinking);
        
        //将子菜单放入主菜单里
        activityMain.setSubButton(activityList);
//        helpMain.setSubButton(listHelp);
        //把一级菜单加入集合中
        List<MenuButton> mainList = new ArrayList<MenuButton>();
        mainList.add(mytigofoodMain);
        mainList.add(activityMain);
//        mainList.add(helpMain);
        mainList.add(linkManMain);
        
        //将主菜单加入请求对象
        menu.setButton(mainList);
//        LOG.debug(menu.toJsonString());
        System.out.println("微信自定义菜单==："+menu.toJsonString());
        //创建菜单
        ResultType resultType = menuAPI.createMenu(menu);
        LOG.debug(resultType.getCode().toString());
        System.out.println(resultType.toString());
        return resultType.getCode().toString();
    }
	
}
