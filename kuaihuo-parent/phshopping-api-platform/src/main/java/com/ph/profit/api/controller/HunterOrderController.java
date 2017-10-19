package com.ph.profit.api.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.core.customenum.MessageEnum;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.MessageInfo;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.verifycode.VerifyUtil;
import com.ph.shopping.facade.profit.dto.HunterOrderProfitDTO;
import com.ph.shopping.facade.profit.request.CountyDTO;
import com.ph.shopping.facade.profit.request.HunterDTO;
import com.ph.shopping.facade.profit.request.PositionDTO;
import com.ph.shopping.facade.profit.request.TwonDTO;
import com.ph.shopping.facade.profit.service.IHunterOrderProfitService;
import com.ph.shopping.facade.profit.vo.HunterOrderProfitVO;

@Controller
@RequestMapping("web/hunter")
public class HunterOrderController extends Hunter {

	//批发商订单service
	@Reference(version = "1.0.0")
	private IHunterOrderProfitService iHunterOrderProfitService;

	/********************************************* 给北京提供接口 ********************/
	/**
	 * 
	 * @Title: findAreaList
	 * @Description: 获取省市区列表
	 * @author 王强
	 * @date 2017年4月6日 上午10:36:59
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findarea", method = RequestMethod.POST)
	@ResponseBody
	public MessageInfo findArea(TwonDTO request) {
		try {
			logger.info("获取省市区列表:"+JSON.toJSONString(request));
			return iHunterOrderProfitService.getAreaList(request.getTownId());
		} catch (Exception e) {
			logger.error("获取省市县列表失败");
			e.printStackTrace();
			return getMessageInfo(MessageEnum.EXCEPTION);
		}
	}

	/**
	 * 
	 * @Title: findTownList
	 * @Description: 获取乡镇列表
	 * @author 王强
	 * @date 2017年4月6日 上午10:36:47
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findtownlist", method = RequestMethod.POST)
	@ResponseBody
	public MessageInfo findTownList(CountyDTO request) {
		try {
			logger.info("获取乡镇列表:"+JSON.toJSONString(request));
			return iHunterOrderProfitService.getTownList(request.getCountyId());
		} catch (Exception e) {
			logger.error("获取乡镇列表失败");
			return getMessageInfo(MessageEnum.EXCEPTION);
		}
	}

	/**
	 * 
	 * @Title: getProvince
	 * @Description: 查询省
	 * @author 王强
	 * @date 2017年4月7日 上午11:26:15
	 * @return
	 */
	@RequestMapping(value = "getprovinces", method = RequestMethod.POST)
	@ResponseBody
	public MessageInfo getProvince() {
		try {
			return iHunterOrderProfitService.getProvinces();
		} catch (Exception e) {
			e.printStackTrace();
			return getMessageInfo(MessageEnum.EXCEPTION);
		}
	}

	/**
	 * 
	 * @Title: getCity
	 * @Description: 查询市
	 * @author 王强
	 * @date 2017年4月7日 上午11:35:11
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getcities", method = RequestMethod.POST)
	@ResponseBody
	public MessageInfo getCity(PositionDTO request) {
		try {
			logger.info("查询市:"+JSON.toJSONString(request));
			return iHunterOrderProfitService.getCitys(request.getProvinceId());
		} catch (Exception e) {
			logger.error("查询市异常", e.getMessage());
			e.printStackTrace();
			return getMessageInfo(MessageEnum.EXCEPTION);
		}
	}

	/**
	 * 
	 * @Title: getCounty
	 * @Description: 查询区、县
	 * @author 王强
	 * @date 2017年4月7日 上午11:35:11
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getcounties", method = RequestMethod.POST)
	@ResponseBody
	public MessageInfo getCounty(PositionDTO request) {
		try {
			logger.info("查询区、县:"+JSON.toJSONString(request));
			return iHunterOrderProfitService.getCountys(request.getCityId());
		} catch (Exception e) {
			logger.error("查询区县异常", e.getMessage());
			e.printStackTrace();
			return getMessageInfo(MessageEnum.EXCEPTION);
		}
	}

	/**
	 * 
	 * @Title: getTown
	 * @Description: 查询乡镇(社区)
	 * @author 王强
	 * @date 2017年4月7日 上午11:35:11
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "gettowns", method = RequestMethod.POST)
	@ResponseBody
	public MessageInfo getTown(PositionDTO request) {
		try {
			logger.info("查询乡镇(社区):"+JSON.toJSONString(request));
			return iHunterOrderProfitService.getTowns(request.getCountyId());
		} catch (Exception e) {
			logger.error("查询乡镇异常", e.getMessage());
			e.printStackTrace();
			return getMessageInfo(MessageEnum.EXCEPTION);
		}
	}

	/*----------------------------------------------------上面是强哥写的没动--------------------------------------------------------------------*/
	

	/**
	 * 
	* @Title: hunterOrderProfitPage
	* @Description: TODO(批发商分润记录页面)
	* @author Mr.Dong
	* @date  2017年6月1日 下午5:32:05
	* @return
	 */
	@RequestMapping(value = "/hunterOrderProfitPage", method = RequestMethod.GET)
	public String hunterOrderProfitPage() {
		return "profit/profit_hunting";
	}
		

	/**
	 * 
	* @Title: hunterOrderProfitList
	* @Description: TODO(批发商订单分润记录list)
	* @author Mr.Dong
	* @date  2017年6月2日 上午11:41:24
	* @param hunterOrderProfitDTO
	* @param pagebean
	* @return
	 */
	@RequestMapping(value ="/hunterOrderProfitList",method = RequestMethod.POST)
	@ResponseBody
	public Result hunterOrderProfitList(HunterOrderProfitDTO hunterOrderProfitDTO,PageBean pagebean) {
		return iHunterOrderProfitService.hunterOrderProfitList(hunterOrderProfitDTO,pagebean);
	}
	
	/**
	 * 
	* @Title: getOnLineOrderProfitEXCEL
	* @Description: TODO(批发商订单导出EXCEL)
	* @author Mr.Dong
	* @date  2017年6月2日 上午10:57:41
	* @param request
	* @param response
	* @param hunterOrderProfitDTO
	* @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getHunterOrderProfitEXCEL", method = RequestMethod.GET)
	public Object getOnLineOrderProfitEXCEL(HttpServletRequest request,
			HttpServletResponse response,HunterOrderProfitDTO hunterOrderProfitDTO) {
		 Result hunterOrderOrderProfitEXCEL = iHunterOrderProfitService.getHunterOrderOrderProfitEXCEL(hunterOrderProfitDTO);
		 List<HunterOrderProfitVO>  data = (List<HunterOrderProfitVO>) hunterOrderOrderProfitEXCEL
				.getData();
		List<Object[]> data2 = new ArrayList<Object[]>();
		for (HunterOrderProfitVO profit : data) {
			Object[] standard = new Object[25];
			standard[0] = profit.getOrderNo();
			standard[1] = profit.getOrderMoney1();
			standard[2] = profit.getHunterProfit1();
			standard[3] = profit.getPhProfit1();
			
			standard[4] = profit.getCompensationMoney1();
			standard[5] = profit.getGuildProfit1();
			standard[6] = profit.getYytProfit();
						
			standard[7] = profit.getChargeProfit1();
			standard[8] = profit.getPhChargeProfit1();
			standard[9] = profit.getYstChargeProfit1();
			standard[10] = profit.getCityAgentName();
			standard[11] = profit.getCityAgentProfit1();
			
			standard[12] = profit.getCountyAgentName();
			standard[13] = profit.getCountyAgentProfit1();
			
			standard[14] = profit.getTownAgentId();
			standard[15] = profit.getTownAgentProfit1();
			
			
			
			standard[16] = profit.getCityPromoterName();
			standard[17] = profit.getCityPromoterProfit1();
			standard[18] = profit.getCountyPromoterName();
			standard[19] = profit.getCountyPromoterProfit1();
			
			standard[20] = profit.getTownPromoterName();
			standard[21] = profit.getTownPromoterProfit1();
			
			standard[22] = profit.getChargeProfitTotal1();
			standard[23] = profit.getChargeProfitRemain1();			
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			standard[24] = df.format(profit.getCreateTime());
			if(profit.getFromSys() != null){
				if(profit.getFromSys().equals("0")){
					standard[25] = "批发商平台";
				}else{
					standard[25] = "普惠平台";
				}
			}
			data2.add(standard);
		}
		ExcelBean excelbean = new ExcelBean();
		excelbean.setTableHeader(new String[] {"订单号","订单金额","批发商分润", "普惠分润","批发商赔付金","工会","易耀通","管理费", "普惠管理费",
				"易商通管理费", "市代名称", "市级代理分润","县代名称", "县级代理分润","区代名称", "区级代理分润",
				"市代推广师名称","推广师推市级代理分润", "县代推广师名称", "推广师推县级代理分润", "区代推广师名称", "推广师推区级代理分润", "管理费支出合计","管理费剩余","分润时间(创建时间)","批发商来源"});
		excelbean.setSheetData(data2);
		excelbean.setName("批发商订单分润记录表");
		try {
			ExcelUtil.download(request, response, excelbean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 
	 * @Title: addHunterProfit
	 * @Description: 链接北京分润接口
	 * @author 王强
	 * @date 2017年4月5日 上午10:06:23
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "addhunterprofit", method = RequestMethod.POST)
	@ResponseBody
	public MessageInfo addHunterProfit(HunterDTO hunterDTO) {
		try {
			logger.info("批发商分润controller入参:"+JSON.toJSONString(hunterDTO));
			if(hunterDTO.getFromSys() == null){
				hunterDTO.setFromSys("0");//北京传null和0都是来源批发商平台
			}
			if (VerifyUtil.verifyEntityField(hunterDTO)) {
				return getMessageInfo(MessageEnum.NO_PARAM);
			}
			return iHunterOrderProfitService.addHunterProfit(hunterDTO);
		} catch (Exception e) {
			e.printStackTrace();
			return getMessageInfo(MessageEnum.EXCEPTION);
		}
	}

	/**
	 * 快火订单批发分润
	 * @return
	 */
	@RequestMapping(value = "/khOrderProfitPage", method = RequestMethod.GET)
	public String hunterOrderProfitPageUrl() {
		return "profit/profit_kh_list";
	}

	/**
	 * 快火批发商分润展示
	 * @param hunterOrderProfitDTO
	 * @param pagebean
	 * @return
	 */
	@RequestMapping(value = "/khOrderProfitDatalist", method = RequestMethod.POST)
    public @ResponseBody Result hunterOrderProfitPageList(HunterOrderProfitDTO hunterOrderProfitDTO,PageBean pagebean){
		return iHunterOrderProfitService.hunterOrderProfitPageList(hunterOrderProfitDTO,pagebean);
	}

}
