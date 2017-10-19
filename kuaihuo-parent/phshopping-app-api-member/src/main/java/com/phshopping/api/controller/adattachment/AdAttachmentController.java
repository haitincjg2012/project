package com.phshopping.api.controller.adattachment;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.service.IAdAttachment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页展示数据
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年8月28日 下午5:03:10
 *
 */
@Controller
@RequestMapping("api/member/adattachment")
public class AdAttachmentController {


	@Reference(version = "1.0.0")
	private IAdAttachment  iAdAttachment;

	/**
	 * 首页数据的添加
	 * 经度 ，纬度
	 * @return
	 */
	@RequestMapping("/showdataattachment")
	@ResponseBody
	public Result getShowDataAdAttachment(
			@RequestParam("longitude")String longitude,
			@RequestParam("latitude")String latitude,
			@RequestParam(value="page" ,defaultValue="1") Integer page,
			@RequestParam(value="pagesize" ,defaultValue="10") Integer pagesize,
			@RequestParam(value="localAddress",defaultValue = "0")String localAddress){
		Result result = new  Result ();
		if (longitude==null || latitude==null) {
			result.setCode("0");
			result.setMessage("请输入经纬度");
			return result;
		}
		result =	iAdAttachment.getShowDataAdAttachment(longitude,latitude,(page-1)*pagesize,pagesize,localAddress);
		return result;
	}
	/**
	 *
	 * 附近商店
	 * @return
	 */
	@RequestMapping("/nearbyshopping")
	@ResponseBody
	public Result getNearByShopping(
			@RequestParam("longitude")String longitude,
			@RequestParam("latitude")String latitude,
			@RequestParam(value="page" ,defaultValue="1") Integer page,
			@RequestParam(value="pagesize" ,defaultValue="10") Integer pagesize,
			@RequestParam(value="industry1" ,defaultValue="-1") Long industry1,
			@RequestParam(value="industry2" ,defaultValue="-2") Long industry2
	){
		Result result = new  Result ();
		if (longitude==null || latitude==null) {
			result.setCode("0");
			result.setMessage("请输入经纬度");
			return result;
		}
		result = iAdAttachment.getNearByShopping(longitude,latitude,(page-1)*pagesize,pagesize,industry1,industry2);
		return result;
	}
	/**
	 *
	 * 获取所有的一级分类或则2级分类
	 * type 为1获取一级，为2获取2级
	 * @return
	 */
	@RequestMapping("/allfirst")
	@ResponseBody
	public Result getAllIndustry(@RequestParam("type")Integer type,

								 @RequestParam(value="parentId",defaultValue="0")Long parentId){
		Result result = new Result();
		if (type==null ) {
			result.setCode("0");
			result.setMessage("请输入经纬度");
			return result;

		}
	                 result=iAdAttachment.getAllIndustry(type,parentId);

		return result;
	}

	/**
	 * 搜索接口
	 * @param datas   输入的参数
	 * @param longitude 经度
	 * @param latitude  维度
	 * @param pagesize 当前页展示的数据
	 * @return
	 */
	@RequestMapping("/finddata")
	@ResponseBody
	public Result findData(      @RequestParam("datas")String datas,
								 @RequestParam("longitude")String longitude,
						         @RequestParam("latitude")String latitude,
								 @RequestParam(value="page" ,defaultValue="1") Integer page,
								 @RequestParam(value="pagesize" ,defaultValue="10") Integer pagesize){
		Result result = new Result();
		if (datas==null ) {
			result.setCode("0");
			result.setMessage("请输入将要搜寻的数据");
			return result;

		}
		result=iAdAttachment.findData(datas,(page-1)*pagesize,pagesize,longitude,latitude);

		return result;
	}

	/**
	 * 手机版本跟新状态
	 * @return type，1是快火，2是掌柜，3是批发
	 */
	@RequestMapping("/appversion")
	@ResponseBody
    public  Result getAppVersion(@RequestParam("type")Integer type, String client_type){

		return  iAdAttachment.getAppVersion(type, client_type);
	}

}
