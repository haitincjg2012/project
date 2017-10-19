package com.ph.shopping.facade.spm.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.WarehouseAddressMapper;
import com.ph.shopping.facade.spm.dto.TakePointDTO;
import com.ph.shopping.facade.spm.dto.WarehouseAddressDTO;
import com.ph.shopping.facade.spm.entity.WarehouseAddress;
import com.ph.shopping.facade.spm.exception.WarehouseAddressException;
import com.ph.shopping.facade.spm.exception.WarehouseAddressExceptionEnum;
import com.ph.shopping.facade.spm.service.IWarehouseAddressService;
import com.ph.shopping.facade.spm.vo.WarehouseAddressVO;

/**
 * 
 * @ClassName: WarehouseAddressImpl
 * @Description: 仓库地址接口
 * @author 王强
 * @date 2017年6月1日 下午2:31:41
 */
@Component
@Service(version = "1.0.0")
public class WarehouseAddressImpl implements IWarehouseAddressService {

	// 创建日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	// 仓库地址Mapper
	@Autowired
	WarehouseAddressMapper warehouseAddressMapper;

	@Override
	public Result getWarehouseAddressVoPageList(PageBean page, WarehouseAddressDTO warehouseAddressDto) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<WarehouseAddressVO> list = (Page<WarehouseAddressVO>) warehouseAddressMapper
				.getWarehouseAddressVoListByPage(warehouseAddressDto);
		for (WarehouseAddressVO addressVO : list) {
			String str = "";
			String address = addressVO.getAddress();
			if (addressVO.getIsDefault() == 1 && addressVO.getIsDeliveryPoint() == 1) {
				str = "【提货】【默认】" + address;
			} else {
				if (addressVO.getIsDeliveryPoint() == 1) {
					str = "【提货】" + address;
				}

				if (addressVO.getIsDefault() == 1) {
					str = "【默认】" + address;
				}
			}

			if (!str.equals("")) {
				addressVO.setAddress(str);
			}
		}
		PageInfo<WarehouseAddressVO> pageInfo = new PageInfo<WarehouseAddressVO>(list);
		// return new Result(true, pageInfo.getList(), pageInfo.getTotal());
		return ResultUtil.getResult(RespCode.Code.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
	}

	@Override
	public List<WarehouseAddressVO> getWarehouseAddressVoList(Long userId) {
		List<WarehouseAddressVO> list = warehouseAddressMapper.getWarehouseAddressVoList(userId);
		return list;
	}

	@Override
	public WarehouseAddressVO getWarehouseAddressVoDetail(WarehouseAddressDTO warehouseAddressDto) {
		WarehouseAddressVO warehouseAddressDetail = warehouseAddressMapper
				.getWarehouseAddressDetail(warehouseAddressDto);
		return warehouseAddressDetail;
	}

	@Override
	public Result getWarehouseAddressById(WarehouseAddress warehouseAddress) {
		return ResultUtil.getResult(RespCode.Code.SUCCESS, warehouseAddressMapper.selectByPrimaryKey(warehouseAddress));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result addWarehouseAddress(WarehouseAddress warehouseAddress) {
		try {
			logger.info("新增仓库地址入参，warehouseAddress={}", JSON.toJSONString(warehouseAddress));
			// // 如果为默认地址，更新其他地址为非默认地址
			// if (SpmConstant.IS_YES_DEFAULT ==
			// warehouseAddress.getIsDefault()) {
			// warehouseAddressMapper.updateOtherNotDefault(warehouseAddress);
			// }
			// // 如果为提货点，更新其他地址为非提货点
			// if (SpmConstant.IS_YES_DELIVERYPOINT ==
			// warehouseAddress.getIsDeliveryPoint()) {
			// warehouseAddressMapper.updateOtherNotDeliveryPoint(warehouseAddress);
			// }
			if(warehouseAddressMapper.countIsDefault(warehouseAddress.getUserId()) <= 0) {
				warehouseAddress.setIsDefault((byte) 1);
			} else {
				warehouseAddress.setIsDefault((byte) 2);
			}
			warehouseAddress.setIsDeliveryPoint((byte) 2);
			warehouseAddress.setCreateTime(new Date());
			warehouseAddress.setUpdateTime(new Date());
			
			int res = warehouseAddressMapper.insertUseGeneratedKeys(warehouseAddress);
			if (res == 1) {
				logger.info("仓库地址新增成功!");
				return ResultUtil.getResult(RespCode.Code.SUCCESS, warehouseAddress);
			} else {
				return ResultUtil.getResult(RespCode.Code.FAIL);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("新增仓库地址异常，ex={}", ex);
			throw new WarehouseAddressException(WarehouseAddressExceptionEnum.ADD_ORDER_ADDRESS_EXCEPTION);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result updateWarehouseAddress(WarehouseAddress warehouseAddress) {
		try {
			logger.info("修改仓库地址入参，warehouseAddress={}", JSON.toJSONString(warehouseAddress));
			// // 如果为默认地址，更新其他地址为非默认地址
			// if (SpmConstant.IS_YES_DEFAULT ==
			// warehouseAddress.getIsDefault()) {
			// warehouseAddressMapper.updateOtherNotDefault(warehouseAddress);
			// }
			// // 如果为提货点，更新其他地址为非提货点
			// if (SpmConstant.IS_YES_DELIVERYPOINT ==
			// warehouseAddress.getIsDeliveryPoint()) {
			// warehouseAddressMapper.updateOtherNotDeliveryPoint(warehouseAddress);
			// }
			warehouseAddress.setUpdateTime(new Date());
			int res = warehouseAddressMapper.updateByPrimaryKeySelective(warehouseAddress);
			if (res == 1) {
				logger.info("仓库地址修改成功");
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} else {
				return ResultUtil.getResult(RespCode.Code.FAIL);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("修改仓库地址异常，ex={}", ex);
			throw new WarehouseAddressException(WarehouseAddressExceptionEnum.UPDATE_ORDER_ADDRESS_EXCEPTION);
		}
	}

	@Override
	public Result deleteWarehouseAddress(List<Long> list) {
		Result result = new Result(true);
		try {
			logger.info("删除仓库地址入参，list={}", list);
			// 逻辑删除仓库地址信息
			warehouseAddressMapper.deleteWarehouseAddress(list);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("删除仓库地址异常，ex={}", ex);
			throw new WarehouseAddressException(WarehouseAddressExceptionEnum.DELETE_ORDER_ADDRESS_EXCEPTION);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result updateWarehouseDefault(WarehouseAddress warehouseAddress) {
		try {
			logger.info("设置仓库地址为默认地址入参，warehouseAddress={}", JSON.toJSONString(warehouseAddress));
			// 更新其他地址为非默认地址
			warehouseAddressMapper.updateOtherNotDefault(warehouseAddress);
			// 更新设置仓库地址为默认地址
			int res = warehouseAddressMapper.updateByPrimaryKeySelective(warehouseAddress);
			if (res == 1) {
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} else {
				return ResultUtil.getResult(RespCode.Code.FAIL);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("设置默认仓库地址异常，ex={}", ex);
			throw new WarehouseAddressException(WarehouseAddressExceptionEnum.DEFAULT_ORDER_ADDRESS_EXCEPTION);
		}
	}

	@Override
	public Result updateWarehouseDeliveryPoint(WarehouseAddress warehouseAddress) {
		try {
			logger.info("设置仓库地址为默认提货点入参，warehouseAddress={}", JSON.toJSONString(warehouseAddress));
			// 更新其他地址为非提货点
			warehouseAddressMapper.updateOtherNotDeliveryPoint(warehouseAddress);
			// 设置仓库地址为提货点
			int res = warehouseAddressMapper.updateByPrimaryKeySelective(warehouseAddress);
			if (res == 1) {
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			} else {
				return ResultUtil.getResult(RespCode.Code.FAIL);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("设置默认仓库地址为默认提货点异常，ex={}", ex);
			throw new WarehouseAddressException(WarehouseAddressExceptionEnum.DELIVERY_POINT_ORDER_ADDRESS_EXCEPTION);
		}
	}
	
	@Override
	public Result deleteWareHouse(Long wareHouseId) {
		int res = warehouseAddressMapper.delWareHouse(wareHouseId);
		if(res == 1) {
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} else {
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
	}

	@Override
	public Result queryTakePoints(TakePointDTO takePointDTO) {
		return ResultUtil.getResult(RespCode.Code.SUCCESS, warehouseAddressMapper.getTakePoint(takePointDTO));
	}

	@Override
	public Result queryWareHouseById(Long id) {
		return ResultUtil.getResult(RespCode.Code.SUCCESS, warehouseAddressMapper.getWareHouseById(id));
	}
 
}
