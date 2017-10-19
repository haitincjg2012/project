package com.ph.shopping.facade.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import cm.ph.shopping.facade.order.dto.DishDTO;
import cm.ph.shopping.facade.order.dto.DishTypeDTO;
import cm.ph.shopping.facade.order.entity.Dish;
import cm.ph.shopping.facade.order.vo.DishTypeVO;
import cm.ph.shopping.facade.order.vo.DishVO;

@Repository
public interface DishMapper{
	
	//商品管理界面的出售中和下架列表 
	public List<Map<String, Object>> getDishsSaleOrCancelList(Map<String,Object> params, RowBounds rowBounds);
	
	//商品的批量上下架 
	public void dishBatchSaleOrCancel(@Param("merchantid") Long merchantid, @Param("dishIds") String[] dishIds, @Param("isDelete") Integer isDelete);
	
	//添加商品
	public Long addDish(DishDTO dishDTO);
	
	//查询商品分类  和餐位分类
	public List<DishTypeVO> getDishtypeList(Long merchantId,int type);
	
	//根据分类查询商品
	public List<DishVO> getDishList(@Param("merchantid") Long merchantid,@Param("dishTypeId") Long dishTypeId, @Param("type")int type);
	
	//添加分类
	public Integer addDishType(DishTypeDTO dishTypeDTO);
	//根据分类id查询分类
	public DishTypeVO getDishTypeById(Long dishTypeId);
	//修改商品 
	public Integer UpdateDish(DishDTO dishDTO);

	public void updateClassifyTo(Long dishTypeId, String ids);
	//修改分类名称
	public void updateTypeName(Long id,String typeName);

	public Integer deleteTypeId(Long id);
	
	//添加图片
//	public Long insertList(List<String> listImg);
	public Long insertList(@Param("trim")String trim, @Param("id")Long id);

	public List<String> fingImgList( Long id);

	public List<DishDTO> findDistOneId(Long id);

	public void updateList(@Param("trim")String trim,@Param("id")Long id);

	public void deleteImg(Long id);

	public DishTypeVO getDishTypeName(@Param("typeName")String typeName, @Param("merchantId")Long merchantId,  @Param("type")int type);

	public DishVO findDishById(Long id);
	

}
