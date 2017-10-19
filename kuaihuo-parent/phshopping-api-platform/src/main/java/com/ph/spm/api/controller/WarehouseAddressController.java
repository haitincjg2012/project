package com.ph.spm.api.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ph.base.BaseController;
import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.permission.vo.SessionUserVO;
import com.ph.shopping.facade.spm.constant.OrderAddressIsableEnum;
import com.ph.shopping.facade.spm.dto.TakePointDTO;
import com.ph.shopping.facade.spm.dto.WarehouseAddressDTO;
import com.ph.shopping.facade.spm.entity.WarehouseAddress;
import com.ph.shopping.facade.spm.service.IMerchantService;
import com.ph.shopping.facade.spm.service.IPositionService;
import com.ph.shopping.facade.spm.service.IWarehouseAddressService;
import com.ph.shopping.facade.spm.vo.WarehouseAddressVO;

/**
 * 
 * @ClassName: WarehouseAddressController
 * @Description: 仓库地址
 * @author 王强
 * @date 2017年5月31日 上午9:22:11
 */
@Controller
@RequestMapping("web/warehouseaddress")
public class WarehouseAddressController extends BaseController {

	// 创建日志
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	// 仓库地址接口
	@Reference(version = "1.0.0")
	private IWarehouseAddressService iWarehouseAddressService;

	// 商户接口，调用获取省市区的方法
	@Reference(version = "1.0.0")
	private IMerchantService iMerchantService;

	@Reference(version = "1.0.0")
	private IPositionService iPositionService;

	/**
	 * ======================================页面跳转===============================
	 */

	/**
	 * 
	 * @Title: toListWarehouseAddressPage
	 * @Description: 跳转到仓库地址页面
	 * @author 王强
	 * @date 2017年5月31日 上午9:22:29
	 * @return
	 */
	@RequestMapping(value = "/tolistpage", method = RequestMethod.GET)
	public String toListWarehouseAddressPage() {
		return "account/account_depot";
	}

	// /**
	// * 跳转仓库地址新增页面
	// *
	// * @return 新增页面
	// * @author liuy @createTime：2017年3月27日
	// */
	// @RequestMapping(value = "/addPage", method = RequestMethod.GET)
	// public String toAddWarehouseAddressPage() {
	// return "/account/warehouseAddress/add";
	// }

	// /**
	// * 跳转仓库地址修改页面（根据id获取仓库地址信息）
	// *
	// * @param warehouseAddressDto
	// * 查询条件
	// * @return Result
	// * @author liuy @createTime：2017年3月27日
	// */
	// @RequestMapping(value = "/updatepage", method = RequestMethod.GET)
	// public Object toUpdateWarehouseAddressPage(@ModelAttribute
	// WarehouseAddressDTO warehouseAddressDto) {
	// ModelAndView mv = new ModelAndView("/account/warehouseAddress/update");
	// WarehouseAddressVO warehouseAddressDetailVo =
	// iWarehouseAddressService.getWarehouseAddressVoDetail(warehouseAddressDto);
	// mv.addObject("warehouseAddress", warehouseAddressDetailVo);
	// return mv;
	// }

	/**
	 * ======================================数据操作===============================
	 */

	/**
	 * 
	 * @Title: getWarehouseAddressVoListByPage
	 * @Description:仓库地址分页
	 * @author 王强
	 * @date 2017年6月1日 下午2:30:00
	 * @param page
	 * @param warehouseAddressDto
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pagelist", method = RequestMethod.GET)
	@ResponseBody
	public Result getWarehouseAddressVoListByPage(@ModelAttribute PageBean page,
			@ModelAttribute WarehouseAddressDTO warehouseAddressDto, HttpServletRequest request) {
		SessionUserVO sessionUserVo = getLoginUser(); // 获取session
		if (sessionUserVo != null && sessionUserVo.getId() != null && sessionUserVo.getSessionRoleVo() != null
				&& sessionUserVo.getSessionRoleVo().get(0) != null) {
			warehouseAddressDto.setUserId(sessionUserVo.getId()); // 设置
		}
		warehouseAddressDto.setIsable(OrderAddressIsableEnum.ORDER_ADDRESS_ISABLE_YES.getCode());// 查询条件是否可用为：可用
		return iWarehouseAddressService.getWarehouseAddressVoPageList(page, warehouseAddressDto);
	}

	/**
	 * 
	 * @Title: getWarehouseAddressVoList
	 * @Description: 仓库地址列表不分页
	 * @author 王强
	 * @date 2017年6月1日 下午2:29:46
	 * @param warehouseAddress
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object getWarehouseAddressVoList() {
		SessionUserVO sessionUserVo = getLoginUser(); // 获取session
		if (sessionUserVo != null && sessionUserVo.getId() != null && sessionUserVo.getSessionRoleVo() != null
				&& sessionUserVo.getSessionRoleVo().get(0) != null) {
			List<WarehouseAddressVO> warehouseAddressList = iWarehouseAddressService
					.getWarehouseAddressVoList(sessionUserVo.getId());
			return new Result(true, warehouseAddressList, warehouseAddressList.size());
		} else {
			return null;
		}
		// warehouseAddress.setIsable(OrderAddressIsableEnum.ORDER_ADDRESS_ISABLE_YES.getCode());//
		// 查询条件是否可用为：可用
	}

	/**
	 * 
	 * @Title: addWarehouseAddress
	 * @Description: 新增仓库地址
	 * @author 王强
	 * @date 2017年6月1日 下午2:05:59
	 * @param warehouseAddressDTO
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public Object addWarehouseAddress(@ModelAttribute WarehouseAddressDTO warehouseAddressDTO) {
		SessionUserVO sessionUserVo = getLoginUser(); // 获取session
		warehouseAddressDTO.setUserId(sessionUserVo.getId()); // 设置管理Id（商户、供应商、代理商的Id，即登录Id）
		warehouseAddressDTO.setIsable(OrderAddressIsableEnum.ORDER_ADDRESS_ISABLE_YES.getCode());// 可用

		// 将区域最后一级（区Id）设置为positionId
		// warehouseAddressDTO.setPositionId(warehouseAddressDTO.getPositionId());
		// warehouseAddressDTO.setProvinceId(warehouseAddressDTO.getProvinceId());
		// warehouseAddressDTO.setCityId(warehouseAddressDTO.getCityId());
		// warehouseAddressDTO.setCountyId(warehouseAddressDTO.getCountyId());
		warehouseAddressDTO.setAddress(warehouseAddressDTO.getProvinceName() + warehouseAddressDTO.getCityName()
				+ warehouseAddressDTO.getCountyName() + warehouseAddressDTO.getDetailAddress());
		// 开始新增
		WarehouseAddress warehouseAddress = new WarehouseAddress();
		BeanUtils.copyProperties(warehouseAddressDTO, warehouseAddress);
		warehouseAddress.setCreaterId(sessionUserVo.getId());
		return iWarehouseAddressService.addWarehouseAddress(warehouseAddress);

	}

	/**
	 * 
	 * @Title: getWareHouseById
	 * @Description:通过ID查询仓库地址
	 * @author 王强
	 * @date 2017年6月1日 上午9:56:04
	 * @param addressDTO
	 * @return
	 */
	@RequestMapping(value = "getwarehousebyid", method = RequestMethod.POST)
	@ResponseBody
	public Result getWareHouseById(WarehouseAddressDTO addressDTO) {
		WarehouseAddress warehouseAddress = new WarehouseAddress();
		warehouseAddress.setId(addressDTO.getWareHouseId());
		return iWarehouseAddressService.getWarehouseAddressById(warehouseAddress);
	}

	/**
	 * 修改仓库地址
	 * 
	 * @param warehouseAddress
	 *            仓库地址实体
	 * @return Result
	 * @author liuy @createTime：2017年3月27日
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object updateWarehouseAddress(@ModelAttribute WarehouseAddressDTO addressDTO) {
		SessionUserVO sessionUserVo = getLoginUser(); // 获取session
		WarehouseAddress warehouseAddress = new WarehouseAddress();
		BeanUtils.copyProperties(addressDTO, warehouseAddress);
		if (sessionUserVo != null && sessionUserVo.getId() != null && sessionUserVo.getSessionRoleVo() != null
				&& sessionUserVo.getSessionRoleVo().get(0) != null) {
			warehouseAddress.setUpdaterId(sessionUserVo.getId()); // 设置创建人、 更新人
			warehouseAddress.setUserId(sessionUserVo.getId()); // 设置管理Id（商户、供应商、代理商的Id，即登录Id）
			warehouseAddress.setAddress(addressDTO.getProvinceName() + addressDTO.getCityName()
					+ addressDTO.getCountyName() + addressDTO.getDetailAddress());
		}

		warehouseAddress.setId(addressDTO.getWareHouseId());

		// 开始修改
		return iWarehouseAddressService.updateWarehouseAddress(warehouseAddress);
	}

	/**
	 * 设置仓库地址为默认地址
	 *
	 * @param warehouseAddress
	 *            仓库地址实体
	 * @return Result
	 * @author liuy @createTime：2017年3月27日
	 */
	@RequestMapping(value = "/updatewarehousedefault", method = RequestMethod.POST)
	@ResponseBody
	public Object updateWarehouseDefault(@ModelAttribute WarehouseAddressDTO addressDTO) {
		SessionUserVO sessionUserVo = getLoginUser(); // 获取session
		WarehouseAddress warehouseAddress = new WarehouseAddress();
		if (sessionUserVo != null && sessionUserVo.getId() != null && sessionUserVo.getSessionRoleVo() != null
				&& sessionUserVo.getSessionRoleVo().get(0) != null) {
			warehouseAddress.setUpdaterId(sessionUserVo.getId()); // 设置 更新人
			warehouseAddress.setUserId(sessionUserVo.getId()); // 设置管理Id（商户、供应商、代理商的Id，即登录Id）
		}
		warehouseAddress.setUpdateTime(new Date()); // 设置 更新时间
		warehouseAddress.setIsDefault(addressDTO.getIsDefault());
		warehouseAddress.setId(addressDTO.getWareHouseId());
		return iWarehouseAddressService.updateWarehouseDefault(warehouseAddress);// 修改仓库地址表
	}

	/**
	 * 设置仓库地址为提货点
	 *
	 * @param warehouseAddress
	 *            仓库地址实体
	 * @return Result
	 * @author liuy @createTime：2017年3月27日
	 */
	@RequestMapping(value = "/updatewarehousedeliverypoint", method = RequestMethod.POST)
	@ResponseBody
	public Object updateWarehouseDeliveryPoint(@ModelAttribute WarehouseAddressDTO addressDTO) {
		// 获取session
		SessionUserVO sessionUserVo = getLoginUser();
		WarehouseAddress warehouseAddress = new WarehouseAddress();
		// 设置 更新人
		if (sessionUserVo != null && sessionUserVo.getId() != null && sessionUserVo.getSessionRoleVo() != null
				&& sessionUserVo.getSessionRoleVo().get(0) != null) {
			warehouseAddress.setUpdaterId(sessionUserVo.getId());
			warehouseAddress.setUserId(sessionUserVo.getId());// 设置管理Id（商户、供应商、代理商的Id，即登录Id）
		}
		// 设置 更新时间
		warehouseAddress.setUpdateTime(new Date());
		warehouseAddress.setIsDeliveryPoint(addressDTO.getIsDeliveryPoint());
		warehouseAddress.setId(addressDTO.getWareHouseId());
		// 修改仓库地址表
		return iWarehouseAddressService.updateWarehouseDeliveryPoint(warehouseAddress);
	}

	/**
	 * 批量删除仓库地址
	 *
	 * @param warehouseAddressIds
	 *            仓库地址Id字符串，以逗号分隔
	 * @return Result
	 * @author liuy @createTime：2017年3月27日
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteWarehouseAddress(String warehouseAddressIds) {
		Result result = new Result(true);
		String[] sIds = warehouseAddressIds.split(",");
		Long[] str2 = new Long[sIds.length];
		for (int i = 0; i < sIds.length; i++) {
			str2[i] = Long.valueOf(sIds[i]);
		}
		List<Long> list = new ArrayList<Long>();
		Collections.addAll(list, str2);
		result = this.iWarehouseAddressService.deleteWarehouseAddress(list);
		return result;
	}

	/**
	 * 
	 * @Title: deleteWareHouseAddress
	 * @Description: 删除仓库地址
	 * @author 王强
	 * @date 2017年6月1日 下午3:42:05
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "delwarehouseaddress", method = RequestMethod.POST)
	@ResponseBody
	public Result deleteWareHouseAddress(Long wareHouseId) {
		return iWarehouseAddressService.deleteWareHouse(wareHouseId);
	}

	/**
	 * 
	 * @Title: getTakePoints
	 * @Description: 获取提货点
	 * @author 王强
	 * @date 2017年6月21日 下午9:28:08
	 * @param takePointDTO
	 * @return
	 */
	@RequestMapping(value = "gettakepoints", method = RequestMethod.POST)
	@ResponseBody
	public Result getTakePoints(TakePointDTO takePointDTO) {
		return iWarehouseAddressService.queryTakePoints(takePointDTO);
	}
}
