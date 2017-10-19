package com.ph.shopping.facade.order.service.impl;

import cm.ph.shopping.facade.order.service.AlqOrderService;
import cm.ph.shopping.facade.order.vo.AlqOrderVO;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.core.constant.PageConstant;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.AlqOrderMapper;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
@Service(version = "1.0.0")
@Transactional(readOnly = true)
public class AlqOrderServiceImpl implements AlqOrderService {


	private static Logger logger = LoggerFactory.getLogger(AlqOrderServiceImpl.class);

	@Autowired
	private AlqOrderMapper AlqOrderMapper;


	@Override
	public Result getAlqList(PageBean pageBean,AlqOrderVO alqOrderVO) {
		Result result=ResultUtil.getResult(RespCode.Code.NO_ORDER);
		logger.info("================后台查询批发订单开始================");
		//分页工具类
		if (pageBean != null){
			pageBean.setPageSize(pageBean.getPageSize() == 0 ? PageConstant.pageSize : pageBean.getPageSize());
			pageBean.setPageNum(pageBean.getPageNum() == 0 ? PageConstant.pageNum : pageBean.getPageNum());
			PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		}
		try{
		List<AlqOrderVO> alqList=AlqOrderMapper.getAlqList(alqOrderVO);  //查询列表

		PageInfo<AlqOrderVO> pageInfo=new PageInfo<AlqOrderVO>(alqList);
		//如果不为null返回数据
		if(alqList.size()!=0&&alqList!=null){
			result=ResultUtil.getResult(RespCode.Code.SUCCESS,pageInfo.getList(),pageInfo.getTotal());
		}
		logger.info("================后台查询批发订单结束================"+ result);
		}catch(Exception  e){
			logger.info("后台查询批发订单数据异常："+e);
		}
		return result;
	}

	/**
	 * 批发代理订单列表
	 * @param pageBean
	 * @param alqOrderVO
	 * @return
	 */
	@Override
	public Result getAlqPfList(PageBean pageBean, AlqOrderVO alqOrderVO) {
		Result result=ResultUtil.getResult(RespCode.Code.NO_ORDER);
		logger.info("================后台查询批发订单开始================");
		//分页工具类

		/*if (pageBean != null){
			pageBean.setPageSize(pageBean.getPageSize() == 0 ? PageConstant.pageSize : pageBean.getPageSize());
			pageBean.setPageNum(pageBean.getPageNum() == 0 ? PageConstant.pageNum : pageBean.getPageNum());
			PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
		}*/
		Map map = AlqOrderMapper.getLevel(alqOrderVO.getArgentId());
		alqOrderVO.setLevel(Long.valueOf(map.get("agentLevelId").toString()));
		alqOrderVO.setCityId((Long) map.get("cityId"));
		alqOrderVO.setCountyId((Long) map.get("countyId"));
		int total=AlqOrderMapper.getAlqPfListCount(alqOrderVO);
		List<AlqOrderVO> alqList=AlqOrderMapper.getAlqPfList(alqOrderVO,new RowBounds((pageBean.getPageNum()-1)*pageBean.getPageSize(),pageBean.getPageSize()));  //查询列表
		/*for (AlqOrderVO alqoredervo: alqList) {
			Long totalPrice=AlqOrderMapper.getOrderTotalPrice(alqOrderVO.getOrderNo());
			alqoredervo.setTotalPrice(totalPrice);
			alqoredervo.setRefundMsg("线上订单");
		}*/
		for(int i=0;i<alqList.size();i++){
			alqList.get(i).setTotalMoney(Double.valueOf(DoubleUtils.formatRound(AlqOrderMapper.getOrderTotalPrice(alqList.get(i).getOrderNo())/100.00,2)));
			alqList.get(i).setRefundMsg("线上订单");
			System.out.println("***********************");
		}

		//PageInfo<AlqOrderVO> pageInfo=new PageInfo<AlqOrderVO>(alqList);
		//如果不为null返回数据
		if(alqList.size()!=0&&alqList!=null){
			result=ResultUtil.getResult(RespCode.Code.SUCCESS,alqList,total);
		}
		logger.info("================后台查询批发订单结束================"+ result);
		return result;
	}

	@Override
	public Result getAlqOrderDetailVO(Long id) {
		logger.info("================后台查询批发详情开始================");
		Result result=ResultUtil.getResult(RespCode.Code.FAIL);
		AlqOrderVO orderVO=AlqOrderMapper.getAlqDetail(id);
		if(orderVO!=null){
			result=ResultUtil.getResult(RespCode.Code.SUCCESS,orderVO);
		}
		logger.info("================后台查询批发详情结束================"+ result);
		return result;
	}
}
