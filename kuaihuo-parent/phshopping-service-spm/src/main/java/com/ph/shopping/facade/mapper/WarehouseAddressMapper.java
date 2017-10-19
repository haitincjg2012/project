package com.ph.shopping.facade.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.spm.dto.TakePointDTO;
import com.ph.shopping.facade.spm.dto.WarehouseAddressDTO;
import com.ph.shopping.facade.spm.entity.WarehouseAddress;
import com.ph.shopping.facade.spm.vo.OnlineTakePointVO;
import com.ph.shopping.facade.spm.vo.WareHouseVO;
import com.ph.shopping.facade.spm.vo.WarehouseAddressVO;

/**
 * 
 * phshopping-service-spm
 *
 * @description：仓库地址Mapper
 *
 * @author：liuy
 *
 * @createTime：2017年3月25日
 *
 * @Copyright @2017 by liuy
 */
@Component
@Service(version="1.0.0")
public interface WarehouseAddressMapper  extends BaseMapper<WarehouseAddress>{
	
	/**
	 * 分页查询仓库地址信息
	 * @param warehouseAddressVo 仓库地址
	 * @return List<WarehouseAddressVo>
	 * @author liuy
     * @createTime 2017年3月25日
	 */
	public List<WarehouseAddressVO> getWarehouseAddressVoListByPage(WarehouseAddressDTO warehouseAddressDto);

	/**
	 * 查询仓库地址信息List
	 * @param warehouseAddressVo 仓库地址
	 * @return List<WarehouseAddressVo>
	 * @author liuy
     * @createTime 2017年3月25日
	 */
	public List<WarehouseAddressVO> getWarehouseAddressVoList(@Param("userId") Long userId);
	
	/**
	 * 查询仓库地址明细信息
	 * @param warehouseAddressDto 仓库地址
	 * @return WarehouseAddressVO
	 * @author liuy
     * @createTime 2017年3月25日
	 */
	public WarehouseAddressVO getWarehouseAddressDetail(WarehouseAddressDTO warehouseAddressDto);

	/**
	 * 仓库地址新增
	 * @param warehouseAddress 仓库地址
	 * @return WarehouseAddress
	 * @author liuy
     * @createTime 2017年3月25日
	 */
	public void addWarehouseAddress(WarehouseAddress warehouseAddress);
	
	/**
	 * 批量删除仓库地址信息
	 * @param list 
	 * @author liuy
     * @createTime 2017年3月25日
	 */
	public void deleteWarehouseAddress(List<Long> list);

	/**
	 * 设置除参数外的其他地址为非默认地址
	 * @param warehouseAddress
	 * @author liuy
     * @createTime 2017年3月25日
	 */
	public void updateOtherNotDefault(WarehouseAddress warehouseAddress);

	/**
	 * 设置除参数外的其他地址为非提货点
	 * @param warehouseAddress
	 * @author liuy
     * @createTime 2017年3月25日
	 */
	public void updateOtherNotDeliveryPoint(WarehouseAddress warehouseAddress);
	
	/**
	 * 
	* @Title: countIsDefault
	* @Description: 统计是否是第一条地址
	* @author 王强
	* @date  2017年6月1日 下午3:11:16
	* @param userId
	* @return
	 */
	public Long countIsDefault(@Param("userId") Long userId);

	/**
	 * 
	* @Title: delWareHouse
	* @Description: 删除仓库地址(物理删除)
	* @author 王强
	* @date  2017年6月1日 下午3:35:34
	* @param userId
	* @return
	 */
	public int delWareHouse(@Param("wareHouseId") Long wareHouseId);
	/**
	 * 
	* @Title: getTakePoint
	* @Description: 获取提货点
	* @author 王强
	* @date  2017年6月21日 下午9:18:39
	* @return
	 */
	public List<OnlineTakePointVO> getTakePoint(TakePointDTO takePointDTO);
	
	/**
	 * 
	* @Title: getWareHouseById
	* @Description: 查询仓库地址
	* @author 王强
	* @date  2017年7月4日 下午1:32:18
	* @param id
	 */
	WareHouseVO getWareHouseById(@Param("id") Long id);

}
