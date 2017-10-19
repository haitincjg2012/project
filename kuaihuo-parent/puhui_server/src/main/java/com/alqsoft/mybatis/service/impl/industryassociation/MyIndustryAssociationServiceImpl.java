package com.alqsoft.mybatis.service.impl.industryassociation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.entity.bankcard.BankCard;
import com.alqsoft.entity.industryassociation.IndustryAssociation;
import com.alqsoft.mybatis.dao.industryassociation.MyIndustryAssociationDao;
import com.alqsoft.mybatis.service.bankcard.MyBankCardService;
import com.alqsoft.mybatis.service.industryassociation.MyIndustryAssociationService;


@Service
@Transactional(readOnly=true)
public class MyIndustryAssociationServiceImpl implements MyIndustryAssociationService {

	@Autowired
	private MyIndustryAssociationDao myIndustryAssociationDao;
	
	@Autowired
	private MyBankCardService myBankCardService;

	/**
	 * 行业协会后台——查询身份信息
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findIndustryAssociationById(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return myIndustryAssociationDao.findIndustryAssociationById(params);
	}
	/**
	 * 行业协会后台——查询身份信息条数
	 */
	@Override
	public int findIndustryAssociationCountById(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return myIndustryAssociationDao.findIndustryAssociationCountById(params);
	}
	
	
	@Override
	public IndustryAssociation getIndustryAssociationById(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return myIndustryAssociationDao.getIndustryAssociationById(params);
	}
	
	
	/**
	 * 行业协会后台--检测绑定银行卡
	 * @return
	 */
	@Override
	public Result checkBankCard(IndustryAssociation industryAssociation) {
		
		Result result=new Result();
		
		Map<String,Object> bankCard=new HashMap<String,Object>();
		
		Long industryAssociationId=industryAssociation.getId();
		
		String payName="";
		String card="";
		if(null==industryAssociation.getPayname()){
			payName="";
		}else{
			payName=industryAssociation.getPayname().toString();
		}
		if(null==industryAssociation.getCard()){
			card="";
		}else{
			card=industryAssociation.getCard().toString();
		}
		
		if("".equals(payName) || "".equals(card)){//未进行身份认证
			
				result.setCode(1);
				
		}else{//已经进行身份认证
			
			List<Map<String,Object>> bankCardList=myBankCardService.getBankCardByIndustryAssociationId(industryAssociationId);//根据行业协会ID获取银行卡信息
			
			if(bankCardList.size()>0){//已经绑定银行卡信息
				if(bankCardList.size()==1){
					bankCard=bankCardList.get(0);
					
					result.setCode(2);
					result.setContent(bankCard);
					
				}else{//银行卡信息查询异常,多条数据
					result.setCode(4);
					
				}
				
			}else{//没有绑定银行卡信息
				
				bankCard.put("name", payName);
				bankCard.put("card", card);
				
				result.setCode(3);
				result.setContent(bankCard);
			}
			
		}
		
		
		return result;
	}
	/**
	 * 行业协会后台--修改银行卡--根据会议协会ID查询银行卡信息
	 * @return
	 */
	@Override
	public Map<String,Object> getBankCardIndustryAssociationId(Long industryAssociationId) {
		
		List<Map<String,Object>> bankCardList=myBankCardService.getBankCardByIndustryAssociationId(industryAssociationId);//根据行业协会ID获取银行卡信息
		
		if(bankCardList.size()>0){//已经绑定银行卡信息
			Map<String,Object> bankCard=bankCardList.get(0);
			return 	bankCard;
		}	
		return 	null;	
	}
	/**
	 * 行业协会后台--修改银行卡--修改次数
	 * @return
	 */
	@Override
	public int getBankCardCountByIndustryAssociationId(Long industryAssociationId) {
		
		int count=myBankCardService.getBankCardCountByIndustryAssociationId(industryAssociationId);
		
		return count;
	}
	
	/**
	 * 行业协会后台--提现页面--查询银行信息
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getIndustryAssociationBankCardMsg(Long industryAssociationId) {
		
		return myBankCardService.getIndustryAssociationBankCardMsg(industryAssociationId);
	}
	
	/**
	 * 行业协会后台--根据手机号查询行业协会
	 */
	@Override
	public IndustryAssociation getAssociationByPhone(Map<String, Object> param) {
		
		return myIndustryAssociationDao.getAssociationByPhone(param);
	}
	/**
	 * 添加行业协会验证，名称是否重复存在
	 */
	@Override
	public int findAssociationLikeName(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return myIndustryAssociationDao.findAssociationLikeName(param);
	}
	
}
