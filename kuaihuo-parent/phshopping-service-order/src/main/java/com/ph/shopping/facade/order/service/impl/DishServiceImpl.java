package com.ph.shopping.facade.order.service.impl;


import cm.ph.shopping.facade.order.dto.DishDTO;
import cm.ph.shopping.facade.order.dto.DishTypeDTO;
import cm.ph.shopping.facade.order.service.IDishService;
import cm.ph.shopping.facade.order.vo.DishTypeVO;
import cm.ph.shopping.facade.order.vo.DishVO;
import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.core.RespCode;
import com.ph.shopping.common.util.core.ResultUtil;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.mapper.DishMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 商品业务层实现类
 * @author pz
 *
 */
@Component
@Service(version = "1.0.0")
@Transactional(readOnly=true)
public class DishServiceImpl implements IDishService {
	
	@Autowired
    private DishMapper dishMapper;
	
	@Autowired
    private IDishService iDishService;
	
	/**
	 * 商品管理界面的出售中和下架列表
	 * @param merchantid
	 * @param isDelete 0:出售中 1：下架
	 * 
	 */
	public Result getDishsSaleOrCancelList(Long merchantid,Integer isDelete,Integer pageNum,Integer pageSize,Integer type,Integer sort){
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("merchantid", merchantid);
			params.put("isDelete", isDelete);
			params.put("type", type);
			params.put("sort", sort);
			int index=(pageNum-1) * pageSize;
			RowBounds rowBounds=new RowBounds(index,pageSize);
			List<Map<String,Object>> dishList = dishMapper.getDishsSaleOrCancelList(params,rowBounds);
			for (int i = 0; i < dishList.size(); i++) {
				dishList.get(i).put("price",(Long)dishList.get(i).get("price")/10000);
			}
			Map<String,Object> resutdata =new HashMap<String,Object>();
			//dishList.forEach(e->e.put("saleNum", NumberFormat.getFormatNumber(String.valueOf(e.get("saleNum")))));
			resutdata.put("dishList", dishList);
			return ResultUtil.getResult(RespCode.Code.SUCCESS,dishList);
		} catch (Exception e2) {
			e2.printStackTrace();
			return ResultUtil.getResult(RespCode.Code.FAIL);
		}
	}
	

/*	@Override
	public DishVO getDishById(Long id) {
		
		return dishMapper.getDishById(id);
	}*/
	
	/**
	 * 批量商品的上架和下架
	 */
	@Override
	public void dishBatchSaleOrCancel(Long merchantid, String[] dishIds, Integer isDelete) {
		
		if(dishIds.length==0){
			ResultUtil.getResult(RespCode.Code.DISH_CHOOSE_ERROR);
		}
		dishMapper.dishBatchSaleOrCancel(merchantid, dishIds, isDelete);
	}
	
	

	/**
	 * 添加商品
	 */
	@Transactional
	@Override
	public Result addDish(DishDTO dishDTO) {
		try {
			if(dishDTO==null){
				return ResultUtil.getResult(RespCode.Code.DISH_ADD_ERROR);
			}
			//商品名称
			String name = dishDTO.getDishName();
			if(name==null||"".equals(name.replaceAll("\\s*", ""))){
				return ResultUtil.getResult(RespCode.Code.DISH_ADD_ERROR);
			}
			if(name.length()>30){
				return ResultUtil.getResult(RespCode.Code.DISH_ADD_WORDS_ERROR);
			}
			//==========================@author wudi 修改：描述非必填============================================
			//商品描述
			String description=dishDTO.getDescription();
			/*if(description==null||"".equals(description.replaceAll("\\s*", ""))){
				return ResultUtil.getResult(RespCode.Code.DISH_ADD_ERROR);
			}
			if(description.length()>30){
				return ResultUtil.getResult(RespCode.Code.DISH_ADD_WORDS_ERROR);
			}*/
			if(description!=null && !("".equals(description.replaceAll("\\s*", ""))) && description.length()>30){
				return ResultUtil.getResult(RespCode.Code.DISH_ADD_WORDS_ERROR);
			}
			//========================商品金额的单位可添加也可不添加=========================
			//商品金额单位
			/*String unit=dishDTO.getMoneyUnit();
			if(description==null||"".equals(description.replaceAll("\\s*", ""))){
				return ResultUtil.getResult(RespCode.Code.DISH_ADD_ERROR);
			}*/
			//=======================验证商品分类=============================
			Long dishTypeId=dishDTO.getDishTypeId();
			if(dishTypeId==null){
				return ResultUtil.getResult(RespCode.Code.DISH_ADDDISHTYPE_ERROR);
			}
			DishTypeVO dishTypeVO=iDishService.getDishTypeById(dishTypeId);
			if(dishTypeVO==null){
				return ResultUtil.getResult(RespCode.Code.DISH_DISHTYPE_ERROR);
			}
			dishDTO.setMoney(dishDTO.getMoney()*10000);
			dishDTO.setSaleNum(0l);
			Long count=dishMapper.addDish(dishDTO);
			 String [] imgAddres= dishDTO.getImgAddress().split(",");
		     List<String> list=new ArrayList<>();
		     for (int i = 0; i < imgAddres.length; i++) {
		        	Long insertList = dishMapper.insertList(imgAddres[i].trim(),dishDTO.getId());
				}
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
		
	}

	//查询商品分类
	@Override
	public List<DishTypeVO> getDishtypeList(Long merchantId,int type) {
		return dishMapper.getDishtypeList(merchantId,type);
	}
	
	//根据分类查询商品
	@Override
	public List<DishVO> getDishList(Long merchantid,Long dishTypeId) {
		int type=0;
		return dishMapper.getDishList(merchantid,dishTypeId,type);
	}

	/**
	 * 添加商品分类
	 */
	@Transactional
	@Override
	public Result addDishType(DishTypeDTO dishTypeDTO) {
		if(dishTypeDTO.getTypeName()==null && dishTypeDTO.getMerchantId()==null){
			return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
		}
		DishTypeVO dishTypeVO=dishMapper.getDishTypeName(dishTypeDTO.getTypeName(),dishTypeDTO.getMerchantId(),dishTypeDTO.getType());
		if(dishTypeVO==null){
			Integer count=dishMapper.addDishType(dishTypeDTO);
			if(count < 0){
				return ResultUtil.getResult(RespCode.Code.FAIL);
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		}
		return ResultUtil.getResult(RespCode.Code.CLASSIFICATION_EXISTS);
	}
	
	public DishTypeVO getDishTypeById(Long dishTypeId){
		return dishMapper.getDishTypeById(dishTypeId);
	}

//	修改
	@Transactional
	@Override
	public Result UpdateDish(DishDTO dishDTO) {
		try {
			dishMapper.deleteImg(dishDTO.getId());
			 String [] imgAddres= dishDTO.getImgAddress().split(",");
//		     for (int i = 0; i < imgAddres.length; i++) {
//		    	 //	修改商品图片
//		        	dishMapper.updateList(imgAddres[i].trim(),dishDTO.getId());
//				}
		    List<String> list=new ArrayList<>();
		    for (int i = 0; i < imgAddres.length; i++) {
		       Long insertList = dishMapper.insertList(imgAddres[i].trim(),dishDTO.getId());
			}
		    dishDTO.setMoney(dishDTO.getMoney()*10000);
			Integer count =dishMapper.UpdateDish(dishDTO);
			if(count < 0){
				return ResultUtil.getResult(RespCode.Code.FAIL);
			}
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 分类至
	 */
	@Transactional
	@Override
	public Result updateClassifyTo(Long dishTypeId,String ids) {
		try {	
			if(ids==null && dishTypeId==null){
				return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
			}
			String[] split = ids.split(",");
			//List<Integer> list=new ArrayList<Integer>();
			   for (int i = 0; i < split.length; i++) {
				   dishMapper.updateClassifyTo(dishTypeId,split[i]);
				}
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
		
	}

	
	@Transactional
	@Override
	public Result updateTypeName(Long id,String typeName) {
		try {
			dishMapper.updateTypeName(id,typeName);
			return ResultUtil.getResult(RespCode.Code.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
	}

	@Transactional
	@Override
	public Result deleteTypeId(Long id,Long merchantid,int type) {
		Long dishTypeId=null;
		try {
			dishTypeId=id;
			List<DishVO> dishList = dishMapper.getDishList(merchantid,dishTypeId,type);
			if(dishList!=null && !dishList.isEmpty()){
				return ResultUtil.getResult(RespCode.Code.GOODS_UNDER_CLASSIFICATION);
			}
			Integer count = dishMapper.deleteTypeId(id);
			if(count > 0){
				return ResultUtil.getResult(RespCode.Code.SUCCESS);
			}
				return ResultUtil.getResult(RespCode.Code.FAIL);
		} catch (Exception e) {
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
	}

/**
 * 商品回显
 */
	@SuppressWarnings("all")
	@Override
	public Result findDistOneId(DishDTO dishdto) {
		try {
			if(dishdto==null){
				return ResultUtil.getResult(RespCode.Code.REQUEST_DATA_ERROR);
			}
//			根据商品id查询出该商品所拥有的图片
			List<String> fingImgList = dishMapper.fingImgList(dishdto.getId());
			DishDTO dishdtos = new DishDTO(); 
			if(fingImgList.get(0)!=null){
				String imgs = "";
				for (int i = 0; i < fingImgList.size(); i++) {
					imgs += fingImgList.get(i)+",";
				}
				if (!imgs.equals("")) {
					List<DishDTO>	dishIdlist  = dishMapper.findDistOneId(dishdto.getId());
					dishdtos.setImgAddress(imgs);
					dishdtos.setDishName(dishIdlist.get(0).getDishName());
					dishdtos.setDescription(dishIdlist.get(0).getDescription());
					dishdtos.setMoney(dishIdlist.get(0).getMoney()/10000);
					dishdtos.setId(dishIdlist.get(0).getId());
					dishdtos.setTypeName(dishIdlist.get(0).getTypeName());
					dishdtos.setDishTypeId(dishIdlist.get(0).getDishTypeId());
					dishdtos.setMoneyUnit(dishIdlist.get(0).getMoneyUnit());
				}
			}
			return  ResultUtil.getResult(RespCode.Code.SUCCESS,dishdtos);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultUtil.getResult(RespCode.Code.INTERNAL_SERVER_ERROR);
		}
		
	}

}
