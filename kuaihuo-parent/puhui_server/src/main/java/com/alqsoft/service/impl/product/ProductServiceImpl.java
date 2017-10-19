package com.alqsoft.service.impl.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alqsoft.entity.hotrecommend.HotRecommend;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.order.Order;
import com.alqsoft.entity.ordercomment.OrderComment;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.alqframework.utils.DoubleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.dao.product.ProductDao;
import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.productattachment.ProductAttachment;
import com.alqsoft.entity.productdetail.ProductDetail;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.entity.producttype.ProductType;
import com.alqsoft.service.ordercomment.OrderCommentService;
import com.alqsoft.service.product.ProductService;
import com.alqsoft.service.productattachment.ProductAttachmentService;
import com.alqsoft.service.productdetail.ProductDetailService;
import com.alqsoft.service.productspecification.ProductSpecificationService;
import com.alqsoft.service.producttype.ProductTypeService;

import oracle.net.aso.o;

@Service
@Transactional(readOnly=true)
public class ProductServiceImpl implements ProductService{
	
	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductSpecificationService productSpecificationService;
	@Autowired
	private ProductDetailService productDetailService;
	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private ProductAttachmentService productAttachmentService;
	@Autowired
	private OrderCommentService orderCommentService;
	
	//@CacheEvict(key="'alq_product'+#arg0",value="alq_product")
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	//@Cacheable(key="'alq_product'+#arg0",value="alq_product")
	@Override
	public Product get(Long arg0) {
		// TODO Auto-generated method stub
		return productDao.findOne(arg0);
	}

	@Transactional
	//@CacheEvict(key="'alq_product'+#arg0.id",value="alq_product")
	@Override
	public Product saveAndModify(Product arg0) {
		// TODO Auto-generated method stub
		return productDao.save(arg0);
	}

	@Transactional
	@Override
	public Product getRowLock(Long id) {
		// TODO Auto-generated method stub
		return productDao.getRowLock(id);
	}


	@Override
	@Transactional
	public void updateBatchProductCancelOrSale(Long hunterid,Integer status, Collection<Long> productids) {
		// TODO Auto-generated method stub
		 productDao.updateBatchProductCancelOrSale(hunterid,status, productids);
		
	}
	
	/**
	 * 商品管理---批量上架和下架
	 * 1：下架，2：上架
	 */
	@Transactional
	@Override
	public Result updateBatchProductSaleOrCancel(Long hunterid, String productid,Integer type) {
		String [] productids = productid.split(",");
		Collection<Long> pids=new ArrayList<Long>();
		for(int j=0;j<productids.length;j++){
			pids.add(Long.valueOf(productids[j]));
		}
		try{
			if(type==1){
				//下架
				 productDao.updateBatchProductCancelOrSale(hunterid,0,pids);
				// productDao.updateProductSaleNumZeroForCancel(pids);//商品销量清零
			}else{
				productDao.updateBatchProductCancelOrSale(hunterid,1,pids);
			}
			List<Product> pro = getProdcutByIdsForType(pids);//根据多个商品的id得到这些商品分类的id
			//=====================更新商品分类一二级分类中的数量===========================
			for(Product data : pro){
				 if(data.getProductType()==null||data.getProductType().getId()==null){
					continue;
				 }
				 Long kindid = data.getProductType().getId();
				 	ProductType producttype1 = productTypeService.get(kindid);//查询该商品所属的分类，更新该分类包一二级分类商品的数量
					long count1 = productTypeService.getProductCountByTypeId(kindid);
					producttype1.setSumProduct(count1);
					productTypeService.saveAndModify(producttype1);
				
				if(producttype1.getParent()!=null&&producttype1.getParent().getId()!=null){
					long num  = getProcutAllNumByParentType(producttype1.getParent().getId(),hunterid);
					ProductType producttype2 = productTypeService.get(producttype1.getParent().getId());
					producttype2.setSumProduct(num);
					productTypeService.saveAndModify(producttype2);
				}
			}
			return ResultUtils.returnSuccess("处理成功");
		}catch(Exception e){
			logger.error("商品管理批量上架和下架：批发商id："+hunterid+","+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtils.returnError("处理失败");
		}
	}

	/**
	 * 商品的批量分类更新
	 */
	@Override
	@Transactional
	public Result updateBatchProductKind(Long hunterid, String productid, Long kindid) {
		try{
			String [] productids = productid.split(",");
			Collection<Long> pids=new ArrayList<Long>();
			for(int j=0;j<productids.length;j++){
				Product everyproduct = this.get(Long.valueOf(productids[j]));
				if(everyproduct==null){
					return ResultUtils.returnError("商品id:"+productids[j]+",不存在此商品");
				}
				if(everyproduct.getHunter().getId()==null||everyproduct.getHunter().getId().longValue()!=hunterid.longValue()){
					return ResultUtils.returnError("商品id:"+productids[j]+",关联批发商信息不符");
				}
				pids.add(Long.valueOf(productids[j]));
			}
			List<Product> pro = getProdcutByIdsForType(pids);//根据多个商品的id得到这些商品原来分类的id，只更新这些分类id的商品数量
			updateBatchProductKind(hunterid, pids, kindid);//更新商品分类
			productSpecificationService.updateProductSpecification(pids, kindid);//更新商品规格表里的类别
			//=====================更新批量到此分类下的商品数量===========================
				ProductType producttype1 = productTypeService.get(kindid);
				long count1 = productTypeService.getProductCountByTypeId(kindid);
				producttype1.setSumProduct(count1);
				productTypeService.saveAndModify(producttype1);
			if(producttype1.getParent()!=null&&producttype1.getParent().getId()!=null){//如果批量到此分类有一级分类，需要更新一级分类的商品数量
				//查询该一级分类下
				long count2 =  getProcutAllNumByParentType(producttype1.getParent().getId(),hunterid);
				ProductType producttype2 = productTypeService.get(producttype1.getParent().getId());
				producttype2.setSumProduct(count2);
				productTypeService.saveAndModify(producttype2);
			}
			//===============原来商品也有分类要更新涉及到这些的商品的分类数量  在pro里==================
			for(Product data : pro){
				 if(data.getProductType()==null||data.getProductType().getId()==null){
					continue;
				 }
				 Long kindid1 = data.getProductType().getId();
				 	if(kindid1.longValue()==kindid.longValue()){//相同的分类id上面已经判断更新过了
				 		continue;
				 	}
				 	ProductType producttype2 = productTypeService.get(kindid1);//查询该商品所属的分类，更新该分类包一二级分类商品的数量
					long count2 = productTypeService.getProductCountByTypeId(kindid1);
					producttype2.setSumProduct(count2);
					productTypeService.saveAndModify(producttype2);
				
				if(producttype2.getParent()!=null&&producttype2.getParent().getId()!=null){
					if(producttype1.getParent()!=null&&producttype1.getParent().getId()!=null&&(producttype2.getParent().getId()==producttype1.getParent().getId())){
						continue;//如果一级分类相等，就不用在更新了，上面已经更新过了
					}
					long num  = getProcutAllNumByParentType(producttype2.getParent().getId(),hunterid);
					ProductType producttype3 = productTypeService.get(producttype2.getParent().getId());
					producttype3.setSumProduct(num);
					productTypeService.saveAndModify(producttype3);
				}
			}
			
			return ResultUtils.returnSuccess("分类成功");
		}catch(Exception e){
			logger.error("商品管理批量分类：批发商id："+hunterid+","+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtils.returnError("处理失败");
		}
		
	}

	@Override
	@Transactional
	public void updateBatchProductKind(Long hunterid, Collection<Long> productids, Long kindid) {
		
		productDao.updateBatchProductKind(hunterid,productids,kindid);
		
	}

	/**
	 * 商品添加或编辑
	 */
	@Override
	@Transactional
	public Result saveHunterProduct(Product product, List<ProductSpecification> productSpecification,
			List<ProductDetail> productDetail,List<ProductAttachment> productimage) {
		try {
			 boolean productIdIsnull=false;
	//==========================================商品基本信息的新增和编辑===================================================
				Long kindid=product.getProductType().getId();//商品分类id
				Product productdb=null;
			if(product.getId()==null){
				productIdIsnull=true;
				//新增商品
				productdb =this.saveAndModify(product);
			}else{
				//编辑更新商品
				productdb=this.get(product.getId());
				if(productdb==null){
					return ResultUtils.returnError("该商品不存在");
				}
				Integer status =productdb.getStatus()==null?0:productdb.getStatus();
				if(status==1){
					return ResultUtils.returnError("该商品在出售中，请先下架在编辑");
				}
				productdb.setImageurl(product.getImageurl());
				productdb.setName(product.getName());
				productdb.setProductType(product.getProductType());
				
				//添加商品的起批数量
				productdb.setStartNum(product.getStartNum());
				
				/*//默认支持支付定金是，否支持订金 0否 1支持  --------------默认全部都是1  都支持定金
				productdb.setIsSubscription(1);//是否支持定金状态，默认支持定金
				//
				productdb.setSubscriptionMoney(product.getSubscriptionMoney());//定金的价格
*/				this.saveAndModify(productdb);
			}
	//===========================================商品轮播图的新增和编辑====================================================
			//编辑商品的轮播图，有可能删除，新增还有可能原来的图片更新排序
			Collection<Long> newpattids=new ArrayList<Long>();
			for(ProductAttachment patts :productimage){
				ProductAttachment lunbodb = productAttachmentService.get(patts.getId());
				if(lunbodb==null){
					return ResultUtils.returnError("商品图片信息错误,不存在id值："+patts.getId());
				}
				if(lunbodb.getProduct()!=null&&lunbodb.getProduct().getId()!=null&&lunbodb.getProduct().getId().longValue()!=productdb.getId().longValue()){
					return ResultUtils.returnError("商品图片信息错误,非法id值："+patts.getId());
				}
				newpattids.add(patts.getId());
			}
			if(productIdIsnull){
				//===========================新增商品--轮播图片==========================
				//productAttachmentService.updateProcutAttachmentWithProduct(newpattids, product.getId());
				  for(ProductAttachment patts :productimage){
						ProductAttachment productAttachmentdb = productAttachmentService.get(patts.getId());
						productAttachmentdb.setProduct(productdb);
						productAttachmentdb.setSortNum(patts.getSortNum());
						productAttachmentService.saveAndModify(productAttachmentdb);
					}
			}else{
				//===========================编辑商品--轮播图片==========================
				//原来商品关联的轮播图
				List<ProductAttachment> oldproimages = productAttachmentService.findProductAttachmentByProductId(product.getId());
				Collection<Long> oldproimageids = new ArrayList<Long>();//编辑前原来的轮播图片
				Collection<Long> upda =  new ArrayList<Long>();//要更新的轮播图片
				Collection<Long> dele = new ArrayList<Long>();//要删除的轮播图片
				Collection<Long> sav  = new ArrayList<Long>();//新添加的商品图片
				for(ProductAttachment old :oldproimages){
					oldproimageids.add(old.getId());
				}
				for(Long i : oldproimageids){//包含的需要更新，不包含的需要删除
					if(newpattids.contains(i)){
						upda.add(i);
					}else{
						dele.add(i);
					}
				}
				for(Long j : newpattids){//编辑时新增的轮播图
					if(!upda.contains(j)){
						sav.add(j);
					}
				}
		/*		if(upda.size()>0){//更新轮播图片
					for(Long u :upda){
						for(ProductAttachment patts :productimage){
							if(u.longValue()==patts.getId().longValue()){
								ProductAttachment productAttachmentdb = productAttachmentService.get(u);
								productAttachmentdb.setSortNum(patts.getSortNum());
								productAttachmentService.saveAndModify(productAttachmentdb);
							}
						}
					}
				}*/
				
			    for(ProductAttachment patts :productimage){
					ProductAttachment productAttachmentdb = productAttachmentService.get(patts.getId());
					productAttachmentdb.setProduct(productdb);
					productAttachmentdb.setSortNum(patts.getSortNum());
					productAttachmentService.saveAndModify(productAttachmentdb);
				}
				if(dele.size()>0){//删除轮播图片
					productAttachmentService.deleteProductAttachmentById(dele);
				}
		/*		if(sav.size()>0){//新增的与商品id相关联
					for(Long s :sav){
						for(ProductAttachment add :productimage){
							if(s.longValue()==add.getId().longValue()){
								ProductAttachment productAttachmentdb1 = productAttachmentService.get(s);
								productAttachmentdb1.setProduct(productdb);
								productAttachmentdb1.setSortNum(add.getSortNum());
								productAttachmentService.saveAndModify(productAttachmentdb1);
							}
						}
					}
					//productAttachmentService.updateProcutAttachmentWithProduct(sav, product.getId());
				}*/
				
			}
	//==================================================维护商品分类的数量==================================================
			if(productIdIsnull){
			    ProductType productTypedb=productTypeService.getRowLock(productdb.getProductType().getId());
			    if(productTypedb==null){
			    	return ResultUtils.returnError("该商品类别不存在");
			    }
				productTypedb.setSumProduct(productTypedb.getSumProduct()==null?1:productTypedb.getSumProduct()+1);
				productTypeService.saveAndModify(productTypedb);//更新商品分类的数量
			    if(productTypedb.getParent()!=null&&productTypedb.getParent().getId()!=null){
			    	//一级分类加上
			    	ProductType typeonedb = productTypeService.get(productTypedb.getParent().getId());
			    	typeonedb.setSumProduct(typeonedb.getSumProduct()==null?1:typeonedb.getSumProduct()+1);
			    	productTypeService.saveAndModify(typeonedb);
			    }
			}
			//修改了商品分类和原来的商品分类不同
			if((!productIdIsnull&&kindid.longValue()!=productdb.getProductType().getId().longValue())){
				this.updateBatchProductKind(product.getHunter().getId(), product.getId().toString(), productdb.getProductType().getId());
			}
	//=================================================维护商品规格===========================================================
			if(productIdIsnull){//新增
				for(ProductSpecification spec :productSpecification){
					spec.setId(null);//只做新增商品规格
					//Long saleprice = spec.getPrice()==null?0:spec.getPrice()*10/4;
					//修改销售价格@wudi，销售价格就是录入的价格
					
					Long spe=(Long)(spec.getPrice()==null?0:spec.getPrice());
					
					/*Double saleprice=DoubleUtils.scaleDouble(spe/100,2);//销售价乘以2.5四舍五入
  				    logger.info("------------------------添加商品saleprice："+saleprice);
					Long sale = new BigDecimal(saleprice.toString()).multiply(new BigDecimal(100)).longValue();
					logger.info("------------------------添加商品sale："+saleprice);*/
					
					Double esubprice=DoubleUtils.round(spe*0.2,0);//定金价
					Long eesubprice = esubprice.longValue();
					
					spec.setIsDelete(0);
					spec.setSalePrice(spe);
					spec.setProduct(productdb);
					spec.setProductType(productdb.getProductType());
					spec.setSubscriptionMoney(eesubprice);
					productSpecificationService.saveAndModify(spec);
				}
			}else{//编辑更新，里面有更新或新增或删除
				List<ProductSpecification> oldspecs = productSpecificationService.findProductSpecificationByProductId(product.getId());//编辑之前的商品规格
				Collection<Long> newspeids = new ArrayList<Long>();//更新的商品规格id，不包括新增的规格id，应为新增的商品规格id一定不在之前的商品规格id里
				Collection<Long> oldspeids = new ArrayList<Long>();//之前的商品规格id
				for(ProductSpecification old : oldspecs){
					oldspeids.add(old.getId());
				}
				for(ProductSpecification i : productSpecification){
						if(i.getId()==null){//新增的商品规格
							i.setId(null);//只做新增商品规格
							Long spe=(Long)(i.getPrice()==null?0:i.getPrice());
							
							//修改销售价格@wudi
							/*Double saleprice=DoubleUtils.scaleDouble(spe/100,2);//销售价
							Long sale = new BigDecimal(saleprice.toString()).multiply(new BigDecimal(100)).longValue();
							Double subprice=DoubleUtils.scaleDouble(sale*0.3,2);//定金价格
							Long sub = new BigDecimal(subprice.toString()).multiply(new BigDecimal(100)).longValue();*/
							
							Double esubprice=DoubleUtils.round(spe*0.2,0);//定金价
							Long eesubprice = esubprice.longValue();
							
							i.setIsDelete(0);
							i.setSalePrice(spe);//销售价格
							i.setSubscriptionMoney(eesubprice);//定金价格
							i.setProduct(productdb);
							i.setProductType(productdb.getProductType());
							productSpecificationService.saveAndModify(i);
						}else{//编辑商品规格
				ProductSpecification productSpecificationdb  =	productSpecificationService.get(i.getId());
							if(productSpecificationdb==null){
								return ResultUtils.returnError("参数错误，不存在此规格id:"+i.getId());
							}
							if(productSpecificationdb.getProduct()==null||productSpecificationdb.getProduct().getId()==null){
								return ResultUtils.returnError("错误的数据，编辑的此规格未关联商品");
							}
							if(productSpecificationdb.getProduct().getId().longValue()!=product.getId().longValue()){
								return ResultUtils.returnError("参数错误，编辑的规格关联商品id不符");
							}
							productSpecificationdb.setProductType(productdb.getProductType());
							productSpecificationdb.setPrice(i.getPrice());
							productSpecificationdb.setContent(i.getContent());
							productSpecificationdb.setLimitNum(i.getLimitNum());
							//销售价格
							Long esale=(Long)(i.getPrice()==null?0:i.getPrice());
							//修改销售价格@wudi
							/*Double esaleprice=DoubleUtils.scaleDouble(esale/100,2);//销售价
							//Long eesale = new BigDecimal(esaleprice).multiply(new BigDecimal(100)).longValue();
							Long eesale = new BigDecimal(esaleprice.toString()).multiply(new BigDecimal(100)).longValue();
							*/
							/*Double esale=(double)1;*/
							Double esubprice=DoubleUtils.round(esale*0.2,0);//定金价
							Long eesubprice = esubprice.longValue();
					
							
							productSpecificationdb.setSalePrice(esale);//销售价格
							productSpecificationdb.setIsDelete(0);
							productSpecificationdb.setNum(i.getNum());
							productSpecificationdb.setSubscriptionMoney(eesubprice);//修改定金
							productSpecificationService.saveAndModify(productSpecificationdb);
							newspeids.add(i.getId());//进入更新的规格id，和以前的规格id做比对，不存在的就删除相应的规格id
						}
					}
				//比对更新的商品规格id，不存在的则删除
				Collection<Long> delespec = new ArrayList<Long>();//需要删除的商品规格
				for(Long o :oldspeids){
					if(!newspeids.contains(o)){
						delespec.add(o);
					}
				}
				if(delespec.size()>0){//删除商品规格
					productSpecificationService.deleteProductSpecificationByIds(delespec);
				}
			}
	//================================================维护商品详情==============================================================
			if(productIdIsnull){//新增商品详情
				for(ProductDetail detail : productDetail){
					detail.setId(null);
					detail.setProduct(productdb);
					productDetailService.saveAndModify(detail);
				}
			}else{//编辑商品详情
				List<ProductDetail> oldprdetials = productDetailService.findProductDetailByProductIdToPicture(product.getId());//只查询是图片类型的商品id
				Collection<Long> oldprdetialIds=new ArrayList<Long>();//编辑前的图片详情
				Collection<Long> newprdetialIds=new ArrayList<Long>();//编辑要更新的图片，不能删除
				for(ProductDetail oldprdetial :oldprdetials){
					oldprdetialIds.add(oldprdetial.getId());
				}
				for(ProductDetail newprdetail :productDetail){
					if(newprdetail.getType()==1&&newprdetail.getId()!=null){//图片
						newprdetialIds.add(newprdetail.getId());
						ProductDetail  productdetaildb  = productDetailService.get(newprdetail.getId());
						if(productdetaildb==null){
							return ResultUtils.returnError("参数错误，不存在此详情id:"+newprdetail.getId());
						}
						productdetaildb.setSortNum(newprdetail.getSortNum());
						productdetaildb.setProduct(productdb);
						productDetailService.saveAndModify(productdetaildb);
					}
				}
				//删除所有关联的详情记录除了有用到的图片type=1详情
				if(newprdetialIds.size()==0){
					newprdetialIds.add(0L);
				}
				productDetailService.deleteProductDetailByIds(newprdetialIds, product.getId());
				for(ProductDetail p : productDetail){
					if(p.getType()==1&&p.getId()!=null){
						continue;
					}else{
						p.setId(null);
						p.setProduct(productdb);
						productDetailService.saveAndModify(p);
					}
				}
				
			}
			if(productIdIsnull){
				return ResultUtils.returnSuccess("添加成功");
			}else{
				return ResultUtils.returnSuccess("编辑成功");
			}
			
		} catch (Exception e) {
			logger.error("商品添加和编辑接口："+e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
			return ResultUtils.returnError("处理失败");
		}
		
	}

	/**
	 * 根据多个商品的id得到这些商品分类的id
	 */
	@Override
	public List<Product> getProdcutByIdsForType(Collection<Long> productid) {
		// TODO Auto-generated method stub
		return productDao.getProdcutByIdsForType(productid);
	}

	/**
	 * 获取所有商品列表
	 * @return
	 */
	@Override
	public List<Product> getAllProductList() {
		return productDao.getAllProductList();
	}

	@Override
	public int setHotRecommendNull(Long rid) {
		return productDao.setHotRecommendNull(rid);
	}

	@Override
	public List<Product> getProductIdsByRecommendId(HotRecommend recommend) {
		return productDao.getProductIdsByRecommendId(recommend);
	}

	/**
	 * 查询一级商品二级分类的所有总上架的商品数
	 */
	@Override
	public int getProcutAllNumByParentType(Long parenttypeid, Long hunterid) {
		// TODO Auto-generated method stub
		return productDao.getProcutAllNumByParentType(parenttypeid, hunterid);
	}

	@Override
	public String getProductOptionsByName(String name) {
		name= "%"+name +"%";
		List<Product> products= productDao.getProductByName(name);
		StringBuilder builder = new StringBuilder();
		products.forEach(e-> builder.append("<option value=").append(e.getId()).append( " >").append(e.getName()).append("</option>"));
		return builder.toString();
	}

	@Override
	public String getProductOptionsByHotId(String hid) {
		List<Product> products= productDao.getProductByHotId(hid);
		StringBuilder builder = new StringBuilder();
		products.forEach(e->builder.append("<option selected='selected' value=").append(e.getId()).append( " >").append(e.getName()).append("</option>"));
		return builder.toString();
	}

	@Override
	public void updateCommentNumOrder(Order order) {
		Product product = this.get(order.getProduct().getId());
		OrderComment orderComment = this.orderCommentService.getCommentByOrderId(order.getId());
		if(orderComment.getStartNum().intValue() == 5){
			product.setNiceCommentNum(product.getNiceCommentNum().longValue() - 1);
		}else if(orderComment.getStartNum().intValue() >=3 && orderComment.getStartNum().intValue() <= 4){
			product.setCommonCommentNum(product.getCommonCommentNum().longValue() - 1);
		}else if(orderComment.getStartNum().intValue() >=0 && orderComment.getStartNum().intValue() < 3){
			product.setBadCommentNum(product.getBadCommentNum().longValue() - 1);
		}
		this.saveAndModify(product);
		logger.info("订单:"+order.getId()+"退款,商品评论更新成功");
		
	}
}
