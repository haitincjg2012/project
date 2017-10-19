/**  
 * @Title:  HeadhuntingController.java   
 * @Package com.ph.member.api.controller   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: 李杰    
 * @date:   2017年7月7日 下午3:52:45   
 * @version V1.0 
 * @Copyright: 2017
 */  
package com.ph.member.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ph.member.api.vo.HeadhuntingPageVO;
import com.ph.member.api.vo.HeadhuntingVO;
import com.ph.member.api.vo.syn.SynAddHeadhuntingVO;
import com.ph.member.api.vo.syn.SynHeadhuntingContent;
import com.ph.member.api.vo.syn.SynHeadhuntingDetailVO;
import com.ph.member.api.vo.syn.SynHeadhuntingVO;
import com.ph.shopping.common.core.config.properties.BjUrlProperties;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.core.customenum.SmsCodeType;
import com.ph.shopping.common.core.customenum.SmsSourceEnum;
import com.ph.shopping.common.core.other.annotation.Token;
import com.ph.shopping.common.core.other.sms.data.SmsSendData;
import com.ph.shopping.common.core.other.sms.handle.ISmsDataService;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelExportConfig;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.http.HttpClientUtils;
import com.ph.shopping.common.util.http.HttpResult;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.common.util.rsa.bj.BJRSACommonUtils;
import com.ph.shopping.common.util.rsa.bj.BJRSACommonUtils.CharSet;
import com.ph.shopping.common.util.smsCode.SmsCodeUtil;
import com.ph.shopping.facade.member.menum.MemberResultEnum;
import com.ph.shopping.facade.member.service.IMemberService;
import com.ph.shopping.facade.spm.service.IPositionService;
import com.ph.shopping.facade.spm.vo.PositionVO;

/**   
 * @ClassName:  HeadhuntingController   
 * @Description:批发商相关操作
 * @author: 李杰
 * @date:   2017年7月7日 下午3:52:45     
 * @Copyright: 2017
 */
@Controller
@RequestMapping("web/headhunting/")
public class HeadhuntingController {
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(HeadhuntingController.class);
	/**
	 * 会员相关操作服务
	 */
	@Reference(version = "1.0.0")
	private IMemberService memberService;
	
	/**短信发送服务*/
	@Autowired
	private ISmsDataService smsDataService;
	
	@Autowired
	private BjUrlProperties bjUrl;
	
	@Reference(version = "1.0.0")
	private IPositionService positionService;
	/**
	 * 
	 * @Title: toAddHeadhuntingPage   
	 * @Description: 跳转添加批发商页面
	 * @param: @param request
	 * @param: @param dto
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "toHeadhuntingDetailPage", method = RequestMethod.GET)
	public String toAddHeadhuntingPage(HttpServletRequest request, String accountNumber,Model model) {
		log.info("加载添加批发商数据页面入参 ： " + accountNumber);
		if(StringUtils.isNotBlank(accountNumber)){
			HeadhuntingPageVO hvo = loadListData(request, accountNumber, null, null);
			if(null != hvo){
				List<HeadhuntingVO> list = hvo.getData();
				if(null != list && !list.isEmpty()){
					model.addAttribute("headhuntingInfo", list.get(0));
				}
			}
		}
		return "headhunting/headhuntingDetail";
	}
	/**
	 * 
	 * @Title: addHeadhunting   
	 * @Description: 添加批发商数据操作
	 * @param: @param request
	 * @param: @param dto
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "addHeadhunting", method = RequestMethod.POST)
	@Token(key = "accountNumber")
	public @ResponseBody Result addHeadhunting(HttpServletRequest request, String accountNumber) {
		log.info("添加批发商数据入参 accountNumber : " + accountNumber);
		Result result = ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		if (StringUtils.isBlank(accountNumber)) {
			result.setMessage("账号不能为空");
			return result;
		}
		// 判断账号是否存在
		Result mresult = memberService.verifyPhoneIsExists(accountNumber);
		if (mresult.isSuccess()) {
			return ResultUtil.getResult(MemberResultEnum.HEADHUNTING_EXIST);
		}
		String pwd = SmsCodeUtil.getMemberPwdCode();
		// 此处需要调用北京接口添加数据
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", accountNumber);
		map.put("password", BJRSACommonUtils.encryptByPublicKey(pwd, CharSet.UTF8));
		SynAddHeadhuntingVO response = synAddHeadhunting(map);
		if (null != response) {
			if (response.isSuccess()) {
				// 数据添加完成发送短息密码给用户
				sendPwd(accountNumber, pwd);
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} else {
				result.setCode(response.getCode());
				result.setSuccess(false);
				result.setMessage(response.getMsg());
				return result;
			}
		}
		return ResultUtil.getResult(RespCode.Code.FAIL);
	}
	/**
	 * 
	 * @Title: synAddHeadhunting   
	 * @Description: 同步新增返回数据   
	 * @param: @param map
	 * @param: @return      
	 * @return: SynAddHeadhuntingVO
	 * @author：李杰      
	 * @throws
	 */
	private SynAddHeadhuntingVO synAddHeadhunting(Map<String, String> map) {
		SynAddHeadhuntingVO response = null;
		try {
			HttpResult result = HttpClientUtils.sendPost(bjUrl.getAddHeadhuntingUrl(), map);
			if (null != result) {
				String str = result.getResponseContent();
				if (StringUtils.isNotBlank(str)) {
					response = JSONObject.parseObject(str, SynAddHeadhuntingVO.class);
				}
			}
		} catch (Exception e) {
			log.error("调用北京新增批发商错误", e);
		}
		return response;
	}
	/**
	 * 
	 * @Title: sendPwd   
	 * @Description: 给批发商用户发送短信密码
	 * @param: @param accountNumber      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	private boolean sendPwd(String telPhone,String pwd){
		SmsSendData sendData = new SmsSendData();
		sendData.setMobile(telPhone);
		sendData.setSmsCode(pwd);
		sendData.setSourceEnum(SmsSourceEnum.HUNTER_MEMBER);
		sendData.setType(SmsCodeType.HUNTERMEMBER_REGISTER_PD);
		try {
			Result result = smsDataService.sendSmsHaveCode(sendData);
			log.info("给批发商用户发送短信密码 返回数据 result = {}",JSON.toJSONString(result));
			return result.isSuccess();
		} catch (Exception e) {
			log.error("给批发商用户发送短信密码异常",e);
		}
		return false;
	}
	/**
	 * 
	 * @Title: toListPage   
	 * @Description: 加载批发商列表页面
	 * @param: @param request
	 * @param: @param accountNumber
	 * @param: @return      
	 * @return: String
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping(value = "toListPage", method = RequestMethod.GET)
	public String toListPage(HttpServletRequest request){
		
		return "headhunting/headHuntinglist";
	}
	/**
	 * 
	 * @Title: loadListData   
	 * @Description: 加载批发商列表数据
	 * @param: @param request
	 * @param: @param accountNumber
	 * @param: @return      
	 * @return: List<HeadhuntingVO>
	 * @author：李杰      
	 * @throws
	 */
	private HeadhuntingPageVO loadListData(HttpServletRequest request, String accountNumber, Integer pageNum,
			Integer pageSize) {
		HeadhuntingPageVO page = new HeadhuntingPageVO();
		log.info("加载批发商数据列表参数 ： " + accountNumber + "  pageNum = " + pageNum + " pageSize = " + pageSize);
		// 此处需要调用北京接口获取数据
		try {
			SynHeadhuntingContent content = getSynHeadhuntingDetails(accountNumber, pageNum, pageSize);
			if (null != content) {
				List<HeadhuntingVO> result = new LinkedList<HeadhuntingVO>();
				List<SynHeadhuntingDetailVO> list = content.getHunterList();
				if (null != list && !list.isEmpty()) {
					Map<Long, String> townMap = getTownInfoByIds(list);
					for (SynHeadhuntingDetailVO vo : list) {
						HeadhuntingVO hvo = new HeadhuntingVO();
						hvo.setAccountNumber(vo.getPhone());
						hvo.setLaborUnionName(vo.getAssociationName());
						hvo.setTags(vo.getIndustryName());
						hvo.setNickName(vo.getNickname());
						hvo.setId(vo.getHunterId());
						hvo.setHeadImgUrl(vo.getLogoAddress());
						hvo.setDetailAddress(townMap.get(vo.getTownId()));
						result.add(hvo);
					}
				}
				page.setData(result);
				page.setTotal(content.getCount());
			}
		} catch (Exception e) {
			log.error("加载批发商数据列表错误", e);
		}
		return page;
	}
	/**
	 * 
	 * @Title: getTownInfoByIds   
	 * @Description: 得到乡镇信息数据  
	 * @param: @param ids
	 * @param: @return      
	 * @return: Map<Long,String>
	 * @author：李杰      
	 * @throws
	 */
	public Map<Long, String> getTownInfoByIds(List<SynHeadhuntingDetailVO> list) {
		List<Long> ids = new LinkedList<Long>();
		for (SynHeadhuntingDetailVO vo : list) {
			ids.add(vo.getTownId());
		}
		List<PositionVO> ps = positionService.getPositionVoByTownIds(ids);
		Map<Long, String> mapp = new HashMap<Long, String>();
		if (null != ps && !ps.isEmpty()) {
			for (PositionVO vo : ps) {
				mapp.put(vo.getTownId(), vo.getTownName());
			}
		}
		return mapp;
	}
	/**
	 * 
	 * @Title: getSynHeadhuntingDetails   
	 * @Description: 调用北京接口获取批发商数据
	 * @param: @param accountNumber
	 * @param: @param pageNum
	 * @param: @param pageSize
	 * @param: @return      
	 * @return: List<SynHeadhuntingDetailVO>
	 * @author：李杰      
	 * @throws
	 */
	private SynHeadhuntingContent getSynHeadhuntingDetails(String accountNumber, Integer pageNum, Integer pageSize) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", accountNumber == null ? null : accountNumber.trim());
		map.put("page", pageNum == null ? null : pageNum + "");
		map.put("rows", pageSize == null ? null : pageSize + "");
		try {
			HttpResult result = HttpClientUtils.sendPost(bjUrl.getGetHeadhuntingUrl(), map);
			if (null != result) {
				String str = result.getResponseContent();
				if (StringUtils.isNotBlank(str)) {
					SynHeadhuntingVO vo = JSONObject.parseObject(str, SynHeadhuntingVO.class);
					if (null != vo) {
						return vo.getContent();
					}
				}
			}
		} catch (Exception e) {
			log.error("调用北京新增批发商错误", e);
		}
		return null;
	}
	/**
	 * 
	 * @Title: getHeadhuntingList   
	 * @Description: 加载批发商列表数据
	 * @param: @param request
	 * @param: @param accountNumber
	 * @param: @return      
	 * @return: Result
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping("getHeadhuntingList")
	public @ResponseBody Result getHeadhuntingList(HttpServletRequest request, String accountNumber, Integer pageNum,
			Integer pageSize) {
		pageNum = pageNum == null ? PageConstant.pageNum : pageNum;
		pageSize = pageSize == null ? PageConstant.pageSize : pageSize;
		List<HeadhuntingVO> list = null;
		HeadhuntingPageVO hvo = loadListData(request, accountNumber, pageNum, pageSize);
		Integer total = 0;
		if (null != hvo) {
			list = hvo.getData();
			total = hvo.getTotal();
		}
		return ResultUtil.getResult(RespCode.Code.SUCCESS, list == null ? new ArrayList<>() : list,
				total == null ? 0 : total);
	}
	/**
	 * @throws Exception 
	 * @Title: exportHeadhuntingData   
	 * @Description: 导出批发商数据
	 * @param: @param request
	 * @param: @param reponse
	 * @param: @param accountNumber      
	 * @return: void
	 * @author：李杰      
	 * @throws
	 */
	@RequestMapping("exportHeadhuntingData")
	public void exportHeadhuntingData(HttpServletRequest request, HttpServletResponse response, String accountNumber,
			Integer pageNum, Integer pageSize) throws Exception {
		List<HeadhuntingVO> list = null;
		HeadhuntingPageVO hvo = loadListData(request, accountNumber, pageNum, pageSize);
		if (null != hvo) {
			list = hvo.getData();
		}
		if (null == list) {
			list = new ArrayList<HeadhuntingVO>();
		}
		ExcelUtil.download(request, response, getExcelBean(list));
	}
	/**
	 * 
	 * @Title: getExcelBean   
	 * @Description: 封装导出数据   
	 * @param: @param list
	 * @param: @return      
	 * @return: ExcelBean
	 * @author：李杰      
	 * @throws
	 */
	private ExcelBean getExcelBean(List<HeadhuntingVO> list) {
		ExcelBean excelBean = null;
		if (null == list || list.isEmpty()) {
			return excelBean;
		}
		List<Object[]> sheetData = ContainerUtil.aList();
		String name = "批发商数据列表";
		String[] tableHeads = { "批发商头像", "批发商账号", "批发商昵称", "所属工会", "批发商标签", "所属区域" };
		excelBean = ExcelUtil.getExcelHeadBean(tableHeads, name, sheetData);
		HashMap<Integer, ExcelBean> sheetsMap = new HashMap<Integer, ExcelBean>();
		Object[] content = null;
		int length = list.size();
		int olength = tableHeads.length;
		HeadhuntingVO vo;
		for (int j = 0; j < length; j++) {
			vo = list.get(j);
			int k = 0;
			content = new Object[olength];
			content[k++] = vo.getHeadImgUrl();
			content[k++] = vo.getAccountNumber();
			content[k++] = vo.getNickName();
			content[k++] = vo.getLaborUnionName();
			content[k++] = JSON.toJSONString(vo.getTags());
			content[k++] = vo.getDetailAddress();
			sheetData.add(content);
			if ((j + 1) % ExcelExportConfig.MAX_SIZE == 0 || (j + 1) == length) {
				int sheetNum = j / ExcelExportConfig.MAX_SIZE;
				ExcelBean littleExcelBean = new ExcelBean();
				littleExcelBean.setNum(sheetNum);
				littleExcelBean.setName(name);
				littleExcelBean
						.setSheetName("批发商数据列表" + (sheetNum * ExcelExportConfig.MAX_SIZE + 1) + "-" + (j + 1) + "条");
				littleExcelBean.setHeaderCenter("批发商数据列表");
				littleExcelBean.setTableHeader(tableHeads);
				littleExcelBean.setColWidth(ExcelUtil.getColWidth(tableHeads.length));
				littleExcelBean.setSheetData(sheetData);
				sheetsMap.put(sheetNum, littleExcelBean);
				sheetData = ContainerUtil.aList();
			}
		}
		excelBean.setSheets(sheetsMap);
		return excelBean;
	}
}
