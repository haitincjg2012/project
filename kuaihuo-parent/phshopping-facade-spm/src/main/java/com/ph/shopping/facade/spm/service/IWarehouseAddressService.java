package com.ph.shopping.facade.spm.service;

import java.util.List;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.spm.dto.TakePointDTO;
import com.ph.shopping.facade.spm.dto.WarehouseAddressDTO;
import com.ph.shopping.facade.spm.entity.WarehouseAddress;
import com.ph.shopping.facade.spm.vo.WareHouseVO;
import com.ph.shopping.facade.spm.vo.WarehouseAddressVO;

/**
 * 
 * @ClassName: IWarehouseAddressService
 * @Description: 查询仓库地址
 * @author 王强
 * @date 2017年6月1日 上午9:51:03
 */
public interface IWarehouseAddressService {

	/**
	 * 分页获取仓库地址列表
	 * 
	 * @param page
	 *            分页对象
	 * @param warehouseAddressDto
	 *            查询条件
	 * @return Result
	 * @author liuy
	 * @createTime 2017年3月27日
	 */
	public Result getWarehouseAddressVoPageList(PageBean page, WarehouseAddressDTO warehouseAddressDto);

	/**
	 * 获取仓库地址列表
	 * 
	 * @param warehouseAddressDto
	 *            查询条件
	 * @return List<WarehouseAddressVo>
	 * @author liuy
	 * @createTime 2017年3月27日
	 */
	public List<WarehouseAddressVO> getWarehouseAddressVoList(Long userId);

	/**
	 * 新增仓库地址
	 * 
	 * @param warehouseAddress
	 *            仓库地址
	 * @return Result
	 * @author liuy
	 * @createTime 2017年3月27日
	 */
	public Result addWarehouseAddress(WarehouseAddress warehouseAddress);

	/**
	 * 修改仓库地址
	 * 
	 * @param warehouseAddress
	 *            仓库地址
	 * @return Result
	 * @author liuy
	 * @createTime 2017年3月27日
	 */
	public Result updateWarehouseAddress(WarehouseAddress warehouseAddress);

	/**
	 * 删除仓库地址
	 * 
	 * @param warehouseAddress
	 *            仓库地址
	 * @return Result
	 * @author liuy
	 * @createTime 2017年3月27日
	 */
	public Result deleteWarehouseAddress(List<Long> list);

	/**
	 * 通过id获取仓库地址详情
	 * 
	 * @param warehouseAddressDto
	 *            查询条件
	 * @return WarehouseAddressVo
	 * @author liuy
	 * @createTime 2017年3月27日
	 */
	public WarehouseAddressVO getWarehouseAddressVoDetail(WarehouseAddressDTO warehouseAddressDto);

	/**
	 * 根据Id查询仓库地址
	 * 
	 * @param warehouseAddress
	 *            仓库地址
	 * @return WarehouseAddress
	 * @author liuy
	 * @createTime 2017年3月27日
	 */
	public Result getWarehouseAddressById(WarehouseAddress warehouseAddress);

	/**
	 * 设置仓库地址为默认地址
	 * 
	 * @param warehouseAddress
	 *            仓库地址
	 * @author liuy
	 * @createTime 2017年3月27日
	 */
	public Result updateWarehouseDefault(WarehouseAddress warehouseAddress);

	/**
	 * 设置仓库地址为提货点
	 * 
	 * @param warehouseAddress
	 *            仓库地址
	 * @author liuy
	 * @createTime 2017年3月27日
	 */
	public Result updateWarehouseDeliveryPoint(WarehouseAddress warehouseAddress);

	/**
	 * 
	 * @Title: deleteWareHouse
	 * @Description: 删除仓库地址(物理删除)
	 * @author 王强
	 * @date 2017年6月1日 下午3:39:23
	 * @param userId
	 * @return
	 */
	public Result deleteWareHouse(Long wareHouseId);
	/**
	 * 
	* @Title: queryTakePoints
	* @Description: 查询提货点
	* @author 王强
	* @date  2017年6月21日 下午9:22:44
	* @param takePointDTO
	* @return
	 */
	public Result queryTakePoints(TakePointDTO takePointDTO);
	
	/**
	 * 
	* @Title: queryWareHouseById
	* @Description: 通过主键id查询仓库地址
	* @author 王强
	* @date  2017年7月4日 下午1:33:38
	* @param id
	* @return
	 */
	public Result queryWareHouseById(Long id);
	
}
