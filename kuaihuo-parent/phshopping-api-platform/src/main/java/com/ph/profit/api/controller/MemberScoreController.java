package com.ph.profit.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.profit.constant.ProfitConstantEnum;
import com.ph.shopping.facade.profit.dto.MemberScoreDetailedDTO;
import com.ph.shopping.facade.profit.service.IMemberScoreService;
import com.ph.shopping.facade.profit.vo.MemberScoreDetailedVO;

/**
 * 
* @ClassName: MemberScoreController
* @Description: 会员余额Controller
* @author 王强
* @date 2017年6月9日 下午5:20:02
 */
@Controller
@RequestMapping("web/memberscore")
public class MemberScoreController extends BaseController {
	
	@Reference(version = "1.0.0")
	private IMemberScoreService iMemberScoreService;
	/**
	 * 
	* @Title: toMemberScorePage
	* @Description: 跳转到会员余额页面
	* @author 王强
	* @date  2017年6月9日 下午10:24:30
	* @return
	 */
	@RequestMapping(value="tomemberscore",method = RequestMethod.GET)
	public String toMemberScorePage() {
		return "finance/finance_memberNum";
	}
	
	/**
	 * 账户结算下会员余额list
	* @Title: getMemberScoreDetailedList
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月12日 下午7:45:21
	* @param memberScoreDetailedDTO
	* @return
	 */
	@RequestMapping(value="getMemberScoreDetailedList",method = RequestMethod.GET)
	@ResponseBody
	public Object getMemberScoreDetailedList(@ModelAttribute MemberScoreDetailedDTO memberScoreDetailedDTO){
		return iMemberScoreService.getMemberScoreDetailedList(memberScoreDetailedDTO);
	}
	/**
	 * 导出会员余额EXCEL
	* @Title: getMemberScoreDetailedEXCEL
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月12日 下午8:57:16
	* @param memberScoreDetailedDTO
	* @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMemberScoreDetailedEXCEL", method = RequestMethod.GET)
	public Object getMemberScoreDetailedEXCEL(@ModelAttribute MemberScoreDetailedDTO memberScoreDetailedDTO,HttpServletRequest request, HttpServletResponse response){
		try {
			Result memberScoreDetailedEXCEL = iMemberScoreService.getMemberScoreDetailedEXCEL(memberScoreDetailedDTO);
			List<MemberScoreDetailedVO> data = (List<MemberScoreDetailedVO>) memberScoreDetailedEXCEL
					.getData();
			
			List<Object[]> data2 = new ArrayList<Object[]>();
			
			for (MemberScoreDetailedVO profit : data) {
				Object[] standard = new Object[14];
				standard[0] = profit.getTelPhone();
				if(profit.getIsMarketing() == 1){
					standard[1] = "推广师";
				}else{
					standard[1] = "普通会员";
				}
				standard[2] = profit.getMemberName();
				standard[3] = profit.getReturnScore1();
				standard[4] = profit.getReturnScoreOnline1();
				
				standard[5] = profit.getStandbyScore1();
				standard[6] = profit.getEnableScore1();
				standard[7] = profit.getDrawcashScore1();
				standard[8] = profit.getPayTotalScore1();
				standard[9] = profit.getProfitScore1();
				standard[10] = profit.getBalanceDifference1();
				
				if(profit.getStatus() == 0){
					standard[11] = "未冻结";
				}else if(profit.getStatus() == 1){
					standard[11] = "已冻结";
				}
				
				standard[12] = profit.getReturnScore1();
				standard[13] = profit.getReturnScoreOnline1();
				data2.add(standard);
			}
			ExcelBean excelbean = new ExcelBean();
			String[] title = new String[] {"会员账号", "会员类型", "会员姓名","待用转可用积分","线上订单退款", "待用积分", "可用积分", "已提现积分", "已支付积分",
					"分润积分","平衡差", "状态","待用转可用","线上订单退款"};
			excelbean.setTableHeader(title);
			excelbean.setName("会员余额表");
			excelbean.setSheetData(data2);
			ExcelUtil.download(request, response, excelbean);
		} catch (Exception e) {
			logger.error("导出异常", e);
		}
		return null;
	}
	
	/**
	 * 启用会员积分
	* @Title: enableMemberScore
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月13日 上午9:31:52
	* @return
	 */
	@RequestMapping(value = "/enableMemberScore", method = RequestMethod.POST)
	@ResponseBody
	public Object enableMemberScore(Long id){
		return iMemberScoreService.updateMemberScoreStatus(id, ProfitConstantEnum.ENABLE.getCode());
	}
	
	/**
	 * 冻结会员积分
	* @Title: disableMemberScore
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @author Mr.Dong
	* @date  2017年6月13日 上午9:32:36
	* @return
	 */
	@RequestMapping(value = "/disableMemberScore", method = RequestMethod.POST)
	@ResponseBody
	public Object disableMemberScore(Long id){
		return iMemberScoreService.updateMemberScoreStatus(id, ProfitConstantEnum.DISABLE.getCode());
	}
}	



