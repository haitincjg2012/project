package com.ph.shopping.facade.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ph.shopping.common.util.bean.VoDtoEntityUtil;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.date.DoubleUtils;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.CompartmentMapper;

import cm.ph.shopping.facade.order.dto.AddRestaurantOrSeatDTO;
import cm.ph.shopping.facade.order.dto.DishDTO;
import cm.ph.shopping.facade.order.dto.MerchantRestaurantImageDTO;
import cm.ph.shopping.facade.order.entity.Dish;
import cm.ph.shopping.facade.order.entity.DishAttachment;
import cm.ph.shopping.facade.order.service.CompartmentService;

@Component
@Service(version = "1.0.0")
@Transactional(readOnly = true)
public class CompartmentServiceImpl implements CompartmentService {

	private static Logger logger = LoggerFactory.getLogger(CompartmentServiceImpl.class);

	@Autowired
	private CompartmentMapper compartmentMapper;

	@Override
	public Result findRestaurantList(DishDTO dishDto) {
		Result result=new Result();
		try {
			//提取起始页码数值
            dishDto.setBeginIndex((dishDto.getPageNum()-1)*dishDto.getPageSize());
            RowBounds rowBounds=new RowBounds(dishDto.getBeginIndex(),dishDto.getPageSize());
            
			List<DishDTO> findRestaurantList = compartmentMapper.findRestaurantList(dishDto,rowBounds);
			for (int i = 0; i < findRestaurantList.size(); i++) {
				List<Dish> findRestaurantListDel = compartmentMapper.findRestaurantListDel(findRestaurantList.get(i).getId());
				if(findRestaurantListDel!=null && !findRestaurantListDel.isEmpty()){
					findRestaurantList.get(i).setMoney(Double.parseDouble(DoubleUtils.formatRound(findRestaurantList.get(i).getMoney()/ 10000.00, 2)));
					findRestaurantList.get(i).setIsChoose(2);
				}else{
					findRestaurantList.get(i).setMoney(Double.parseDouble(DoubleUtils.formatRound(findRestaurantList.get(i).getMoney()/ 10000.00, 2)));
					findRestaurantList.get(i).setIsChoose(1);
				}
			}
			result.setCode("200");
			result.setData(findRestaurantList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("查询餐位失败");
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
		return result;
	
	}

	@Override
	public Result findTypeDishName(Long merchantId, Long dishTypeId) {
		try {
			List<Map<String,Object>> compartTypeList = compartmentMapper.findTypeDishName(merchantId,dishTypeId);
			return ResultUtil.getResult(RespCode.Code.SUCCESS,compartTypeList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
	}

	@Transactional
	@Override
	public Result saveRestaurant(AddRestaurantOrSeatDTO addRestaurantOrSeatDTO) {
		Result result = null;
		try {
			if (addRestaurantOrSeatDTO == null) {
				return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
				
			}
				addRestaurantOrSeatDTO.setType(1);
				addRestaurantOrSeatDTO.setIsDelete(0l);
				addRestaurantOrSeatDTO.setSaleNum(0l);
				addRestaurantOrSeatDTO.setMoney(addRestaurantOrSeatDTO.getMoney()*10000);
			Long saveRestaurant = compartmentMapper.saveRestaurant(addRestaurantOrSeatDTO);
			 String [] imgAddres= addRestaurantOrSeatDTO.getAddress().split(",");
		     for (int i = 0; i < imgAddres.length; i++) {
		        	Long insertList = compartmentMapper.insertList(imgAddres[i].trim(),addRestaurantOrSeatDTO.getId());
				}
			if (saveRestaurant > 0) {
				result = ResultUtil.setResult(true, "餐位添加成功");
			} else {
				result = ResultUtil.setResult(false, "餐位添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = ResultUtil.setResult(false, "餐位添加异常,请求参数不全");
		}
		return result;
	}

/*	private void merchantImgInit(MerchantRestaurantImageDTO merchantImageDTO, Long id) {
//		将餐位id赋值给图片id
		merchantImageDTO.setId(id);
	}*/

	@Transactional
	@Override
	public Result delsRestaurant(String ids) {
		AddRestaurantOrSeatDTO addRestaurantOrSeatDTO = new AddRestaurantOrSeatDTO();
		if (null == ids) {
			return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR, ids);
		}
		String[] split = ids.split(",");
		for (int i = 0; i < split.length; i++) {
			List<AddRestaurantOrSeatDTO> delDishList = compartmentMapper.findDishDeleteId(split[i]);
			if (delDishList.isEmpty()) {
				Integer delsRestaurant = compartmentMapper.delsRestaurant(split[i]);
			}
		}
		// return ResultUtil.getResult(RespCode.Code.SUCCESS);
		/*
		 * else{ for(int j=0;j<delDishList.size();j++){
		 * if(delDishList.get(j).getStatus()!=2){ Integer delsRestaurant =
		 * compartmentMapper.delsRestaurant(delDishList.get(j).getDishId()); } }
		 * }
		 */
		return ResultUtil.getResult(RespCode.Code.SUCCESS);
	}

	@Transactional
	@Override
	public Result updateRestaurant(AddRestaurantOrSeatDTO addRestaurantOrSeatDTO) {
		logger.info("进入修改餐位方法:", JSON.toJSONString(addRestaurantOrSeatDTO));
		Result result = new Result();
		try {
			compartmentMapper.updateRestaurant(addRestaurantOrSeatDTO);
			result.setSuccess(true);
			result.setMessage("修改餐位成功");
			result.setCode("200");
		} catch (Exception e) {
			logger.error("修改餐位失败" + ResultUtil.getResult(RespCode.Code.FAIL));
			result.setSuccess(false);
			result.setMessage("服务器内部错误");
			result.setCode("0405");
		}
		return result;
	}

	//	餐位回显
	@Override
	public Result findId(Long id) {
		Result result = new Result();
		try {
			if (null != id) {
				Dish findId = compartmentMapper.findId(id);
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}
			return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		} catch (Exception e) {
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public Result findDelId(String ids) {
		try {
			List<AddRestaurantOrSeatDTO> delDishList =compartmentMapper.findDishDeleteId(ids);
			for (int i = 0; i < delDishList.size(); i++) {
				if(delDishList.get(i).getStatus()==2){
					return ResultUtil.getResult(RespCode.Code.LEAVE_TIME);
				}
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
		
	}

	@Override
	public Result findType(Long merchantId) {
		try {
			List compartTypeList = compartmentMapper.findType(merchantId);
			return ResultUtil.getResult(RespCode.Code.SUCCESS,compartTypeList);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
	}

	@Override
	public Result findType2() {
		List compartTypeList = compartmentMapper.findType2();
		return ResultUtil.getResult(RespCode.Code.SUCCESS,compartTypeList);
	}

}
