package cm.ph.shopping.facade.order.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.ph.shopping.common.util.result.Result;

import cm.ph.shopping.facade.order.dto.DishDTO;
import cm.ph.shopping.facade.order.dto.DishTypeDTO;
import cm.ph.shopping.facade.order.vo.DishTypeVO;
import cm.ph.shopping.facade.order.vo.DishVO;




/**
 * 商品业务层接口
 * @author pz
 *
 */
public interface IDishService {
	
	//商品管理界面的出售中和下架列表
	public Result getDishsSaleOrCancelList(Long merchantid,Integer isDelete, Integer pageNum, Integer pageSize,Integer type,Integer sort);
	//商品的批量上下架
	public void dishBatchSaleOrCancel(Long merchantid,String[] dishIds,Integer isDelete);
	//添加商品 
	public Result addDish(DishDTO dishDTO);
	//查询商品分类
	public List<DishTypeVO> getDishtypeList(Long merchantId,int type);
	//添加商品分类
	public Result addDishType(DishTypeDTO dishTypeDTO);
	//根据分类查询商品
	public List<DishVO> getDishList(Long merchantid,Long dishTypeId);
	//根据分类id查询分类
	public DishTypeVO getDishTypeById(Long dishTypeId);
	//修改商品
	public Result UpdateDish(DishDTO dishDTO);
	//分类至
	public Result updateClassifyTo(Long dishTypeId,String ids);
	//修改分类名称	
	public Result updateTypeName(Long id,String typeName);

	public Result deleteTypeId(Long id,Long merchantid,int type);
//	商品回显
	public Result findDistOneId(DishDTO dishdto);


}
