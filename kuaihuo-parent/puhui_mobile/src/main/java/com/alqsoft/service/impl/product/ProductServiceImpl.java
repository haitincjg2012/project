	package com.alqsoft.service.impl.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alqsoft.dao.attachment.AttachmentDao;
import com.alqsoft.dao.hunter.HunterDao;
import com.alqsoft.dao.producttype.ProductTypeDao;
import com.alqsoft.dao.shopcart.ShopCartDao;
import com.alqsoft.utils.NumberFormat;
import com.alqsoft.vo.*;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.dao.product.ProductDao;
import com.alqsoft.entity.attachment.Attachment;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.productattachment.ProductAttachment;
import com.alqsoft.entity.productdetail.ProductDetail;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.entity.producttype.ProductType;
import com.alqsoft.rpc.mobile.RpcAttachmentService;
import com.alqsoft.rpc.mobile.RpcProductAttachmentService;
import com.alqsoft.rpc.mobile.RpcProductService;
import com.alqsoft.service.attachment.AttachmentService;
import com.alqsoft.service.hunterproductdetail.HunterProductDetailService;
import com.alqsoft.service.product.ProductService;
import com.alqsoft.service.productattachment.ProductAttachmentService;
import com.alqsoft.service.productspecification.ProductSpecificationService;
import com.alqsoft.service.producttype.ProductTypeService;

	/**
 * 商品管理
 * @author Administrator
 *
 */
@Service
@Transactional(readOnly=true)
public class ProductServiceImpl implements ProductService{
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

		@Autowired
		private AttachmentDao attachmentDao;
		@Autowired
		private ShopCartDao shopCartDao;
		@Autowired
		private HunterDao hunterDao;
		@Autowired
		private ProductTypeDao protucttypeDao;
		@Autowired
		private ProductDao prodctDao;
		@Autowired
		private RpcProductService rpcProductService;
		@Autowired
		private ProductTypeService productTypeService;
		@Autowired
		private AttachmentService attachmentService;
		@Autowired
		private ProductAttachmentService productAttachmentService;
		@Autowired
		private RpcProductAttachmentService rpcProductAttachmentService;
		@Autowired
		private ProductSpecificationService productSpecificationService;
		@Autowired
		private HunterProductDetailService hunterProductDetailService;


		/**
	 * 商品出售中列表销售中或下架的 默认列表显示的是每个商品规格时间最早的 status : 0 下架 1出售中
	 */
	@Override
	public Result findProductSaleOrCancelList(Long hunterid,Integer status,Integer type,Integer page,Integer rows) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("hunterid", hunterid);
			params.put("status", status);
			params.put("type", type);
			params.put("page", page);
			params.put("rows", rows);
			params.put("startIndex", (page-1)*rows);
			params.put("endIndex",rows);
			List<Map<String,Object>> productlist =findProductSaleOrCancelList(params);
			productlist.forEach(e->e.put("salenum", NumberFormat.getFormatNumber(String.valueOf(e.get("salenum")))));
			Map<String,Object> resutdata =new HashMap<String,Object>();
			resutdata.put("productlist", productlist);
			return ResultUtils.returnSuccess("查询成功", resutdata);
		}catch(Exception e){
			logger.error("查询商品管理，售中，下架列表："+e.getMessage());
			return ResultUtils.returnError("查询失败");
		}
	}

	@Override
	public List<Map<String, Object>> findProductSaleOrCancelList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return prodctDao.findProductSaleOrCancelList(params);
	}

	@Override
	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		return prodctDao.getProductById(id);
	}
	/**
	 * 批量商品的上架和下架，多个商品id以逗号分隔符字符串拼接
	 */
	@Override
	public Result updateBatchProductSaleOrCancel(Long hunterid, String productid,Integer type) {
		if(hunterid==null){
			return ResultUtils.returnError("批发商id不能为空");
		}
		if(productid==null||"".equals(productid)){
			return ResultUtils.returnError("商品id不能为空");
		}
		if(type!=1&&type!=2){
			return ResultUtils.returnError("批量处理参数值type非法");
		}
		String [] productids = productid.split(",");
		if(productids.length==0){
			return ResultUtils.returnError("请选择商品");
		}
		//==========验证商品id为long类型=============================
		Long checkid=null;
		try{
			for(int i=0;i<productids.length;i++){
				checkid=Long.valueOf(productids[i]);
			}
		}catch(Exception e){
			return ResultUtils.returnError("商品id参数类型错误:"+checkid);
		}
		for(int j=0;j<productids.length;j++){
			Product everyproduct = getProductById(Long.valueOf(productids[j]));
			if(everyproduct==null){
				return ResultUtils.returnError("商品id:"+productids[j]+",不存在此商品");
			}
			if(everyproduct.getHunter().getId()==null||everyproduct.getHunter().getId().longValue()!=hunterid){
				return ResultUtils.returnError("商品id:"+productids[j]+",关联批发商信息不符");
			}
		}
		
		return rpcProductService.updateBatchProductSaleOrCancel(hunterid, productid,type);
	}

	/**
	 * 批量商品的分类
	 */
	@Override
	public Result updateBatchProductKind(Long hunterid, String productid, Long kindid) {
		if(hunterid==null){
			return ResultUtils.returnError("批发商id不能为空");
		}
		if(productid==null||"".equals(productid)){
			return ResultUtils.returnError("商品id不能为空");
		}
		if(kindid==null){
			return ResultUtils.returnError("商品类别id不能为空");
		}
		String [] productids = productid.split(",");
		if(productids.length==0){
			return ResultUtils.returnError("请选择商品");
		}
		//==========验证商品id为long类型=============================
		String checkid=null;
		try{
			for(int i=0;i<productids.length;i++){
				checkid=productids[i];
				Long.valueOf(checkid);
			}
		}catch(Exception e){
			return ResultUtils.returnError("商品id参数类型错误:"+checkid);
		}
		ProductType producttype=productTypeService.getProductTypeById(kindid);
		if(producttype==null){
			return ResultUtils.returnError("商品分类不存在");
		}
		if(producttype.getHunter()==null||producttype.getHunter().getId()==null){
			return ResultUtils.returnError("商品分类信息错误，未关联批发商");
		}
		if(producttype.getHunter().getId().longValue()!=hunterid){
			return ResultUtils.returnError("商品分类信息,关联批发商与您不符");
		}
		if(productTypeService.selectProductTypeSon(kindid)>0){
			return ResultUtils.returnError("请选择此分类下的二级分类");
		}
		for(int j=0;j<productids.length;j++){
			Product everyproduct = getProductById(Long.valueOf(productids[j]));
			if(everyproduct==null){
				return ResultUtils.returnError("商品id:"+productids[j]+",不存在此商品");
			}
			if(everyproduct.getHunter().getId()==null||everyproduct.getHunter().getId().longValue()!=hunterid){
				return ResultUtils.returnError("商品id:"+productids[j]+",关联批发商信息不符");
			}
		}
		return rpcProductService.updateBatchProductKind(hunterid,productid,kindid);
	}

	/**
	 * 上传商品图片
	 */
	@Override
	public Result mobileUploadProductAttachment(MultipartFile urlfile, Object[] backUrl, String module,
			String[] extendFile) {
		// TODO Auto-generated method stub
		return attachmentService.mobileUploadAttachment(urlfile,backUrl,module,extendFile);
	}

	/**
	 *商品管理---批发商添加商品
	 */
	@Override
	public Result saveProduct(ProductVo productVo, Long hunterId) {
		// TODO Auto-generated method stub
		if(productVo==null){
			return ResultUtils.returnError("商品参数不能为空");
		}
		if(productVo.getId()!=null){
			Map<String,Object> product= getProductBaseMsgById(productVo.getId());
			if(product==null){
				return ResultUtils.returnError("该商品不存在");
			}
		}
		//=======================验证商品名称=============================
		String name = productVo.getName();
		if(name==null||"".equals(name.replaceAll("\\s*", ""))){
			return ResultUtils.returnError("请填写商品名称或描述");
		}
		if(name.length()>30){
			return ResultUtils.returnError("商品名称或描述不能超过30字符");
		}
		//=======================验证商品轮播图片=============================
		List<ProductPictureVo> productimaglist = productVo.getProductImags();
		if(productimaglist==null||productimaglist.size()==0){
			return ResultUtils.returnError("请上传商品图片");
		}
		if(productimaglist.size()>4){
			return ResultUtils.returnError("商品图片不能超过4张");
		}
		//===========根据商品轮播的sortnum排序升序=============
		Collections.sort(productimaglist, new Comparator<ProductPictureVo>(){
			@Override
			public int compare(ProductPictureVo o1, ProductPictureVo o2) {
				if(o1.getSortNum()==null){
					o1.setSortNum(0);
				}
				if(o2.getSortNum()==null){
					o2.setSortNum(0);
				}
				//按照sortnum进行升序排列  
				if(o1.getSortNum() > o2.getSortNum()){
					return 1;
				}
				if(o1.getSortNum() == o2.getSortNum()){
					return 0;
				}
				return -1;
			}

		});
		List<ProductAttachment> productAttachments = new ArrayList<ProductAttachment>();//给server端传的值
		for(ProductPictureVo productimag :productimaglist){
			ProductAttachment productAttachment = new ProductAttachment();
			if(productimag.getId()==null){
				return ResultUtils.returnError("商品图片信息错误,图片id为空");
			}
			productAttachment.setId(productimag.getId());
			productAttachment.setSortNum(productimag.getSortNum());
			productAttachments.add(productAttachment);
		}
	/*	String pictureids=productVo.getPictureids();
		if(pictureids==null||"".equals(pictureids)){
			return ResultUtils.returnError("商品图片不能为空");
		}
		String [] picIds=pictureids.split(",");
		if(picIds.length==0){
			return ResultUtils.returnError("请上传商品图片");
		}
		if(picIds.length>4){
			return ResultUtils.returnError("商品图片不能超过4张");
		}
		String checkpicId=null;
		try{
			for(int i=0;i<picIds.length;i++){
				checkpicId=picIds[i];
				Long.valueOf(checkpicId);
			}
		}catch(Exception e){
		return ResultUtils.returnError("图片id参数类型错误:"+checkpicId);
		}*/
		//============================验证商品规格=================================
		List<ProductSpecificationVO> specifications = productVo.getSpecifications();
		List<ProductSpecification> secificationlist= new ArrayList<ProductSpecification>();//给server端
		if(specifications==null||specifications.size()==0){
			return ResultUtils.returnError("请添加商品规格");
		}

		//=====================商品起批数量=========================
				/*Integer startNum=productVo.getStartNum();
				if("".equals(startNum)||startNum==null||startNum<1){
					startNum=1;
				}*/


		for(ProductSpecificationVO ptype :specifications){
			String content = ptype.getContent();
			if(content==null||"".equals(content.replaceAll("\\s*", ""))){
				return ResultUtils.returnError("请输入商品单位");
			}
			if(content.length()>20){
				return ResultUtils.returnError("商品规格名称不能超过20字符");
			}
			String price = ptype.getPrice();
			String validateprice=validateMoney(price,"商品");
			if(!"SUCCESS".equals(validateprice)){
				return ResultUtils.returnError(validateprice);
			}
			/*Long num = ptype.getNum();//库存
			if(num==null||num<0){
				return ResultUtils.returnError("请填写库存数量");
			}else if(num<startNum){
				return ResultUtils.returnError("起批数量不能大于库存数量");
			}*/
			Long limitNum = ptype.getLimitNum();
			if(limitNum==null){
				return ResultUtils.returnError("请填写限购数量");
			}




			ProductSpecification specification = new ProductSpecification();
			specification.setId(ptype.getId());//新增规格或编辑规格
			specification.setContent(content);
			specification.setPrice(new BigDecimal(price).multiply(new BigDecimal(100)).longValue());
			//specification.setNum(num);
			specification.setLimitNum(limitNum);
			secificationlist.add(specification);
		}
		//=======================验证商品分类=============================
		Long productTypeId=productVo.getProductTypeId();
		if(productTypeId==null){
			return ResultUtils.returnError("请选择商品分类");
		}
		ProductType producttype=productTypeService.getProductTypeById(productTypeId);
		if(producttype==null){
			return ResultUtils.returnError("商品分类不存在");
		}
		if(productTypeService.selectProductTypeSon(productTypeId)>0){
			return ResultUtils.returnError("请选择此分类下的二级分类");
		}
		//=======================验证商品订金=============================
			/*Integer isSubscription = productVo.getIsSubscription()==null?0:productVo.getIsSubscription();
			if(isSubscription!=0&&isSubscription!=1){
				return ResultUtils.returnError("订金标识参数非法");
			}
			String subscriptionMoney=productVo.getSubscriptionMoney()==null?"0":productVo.getSubscriptionMoney();
			if(isSubscription==1){//支持订金
				String validatedj=validateMoney(subscriptionMoney,"订金");
				if(!"SUCCESS".equals(validatedj)){
					return ResultUtils.returnError(validatedj);
				}
			}*/
		//=======================验证商品详情=============================
		List<ProductDetailVO> productDetails = productVo.getProductDetails();
		if(productDetails==null||productDetails.size()==0){
			return ResultUtils.returnError("请添加商品详情");
		}
		int dpic=0;//详情中图片数量
		int dword=0;//详情中的文本数量
		List<ProductDetail> pdetailList = new ArrayList<ProductDetail>();//给server端传值
		for(ProductDetailVO pdetail : productDetails){
			Integer type=pdetail.getType();//0是文本  1是图片  2是分隔符
			String content = pdetail.getContent();
			Long pictureId=pdetail.getPictureId();
			Integer sortNum=pdetail.getSortNum();//显示的详情顺序
			if(type==null||(type!=0&&type!=1&&type!=2)){
				return ResultUtils.returnError("商品详情类型参数错误");
			}
			if(type==0 && StringUtils.isBlank(content)){
				return ResultUtils.returnError("商品详情文本参数错误");
			}else{
				dword++;
			}
			if(type==1&&pictureId==null){
				continue;
				//return ResultUtils.returnError("商品详情图片参数错误");
			}else{
				dpic++;
			}


			if( type==2 && !StringUtils.isBlank(content)){
				return ResultUtils.returnError("商品详情分隔符参数错误");
			}
			if(sortNum==null){
				return ResultUtils.returnError("商品详情参数sortnum不能为空");
			}
			ProductDetail productDetail = new ProductDetail();
			productDetail.setId(pdetail.getId()==null?null:Long.valueOf(pdetail.getId()));//新增或编辑详情
			productDetail.setType(type);
			productDetail.setSortNum(sortNum);
			if(type==1){
				Attachment imageAttachment = new Attachment();
				imageAttachment.setId(pictureId);
				productDetail.setImageAttachment(imageAttachment);
			}
			if(type==0){
				productDetail.setContent(content);
			}
			pdetailList.add(productDetail);

		}
		if(dpic==0&&dword==0){
			return ResultUtils.returnError("请填写商品详情");
		}
		//========================商品基本信息==================================
		ProductAttachment defaultpic=productAttachmentService.getProductAttachmentById(productimaglist.get(0).getId());//默认显示第一张图片
		if(defaultpic==null||defaultpic.getAddress()==null){
			return ResultUtils.returnError("商品图片信息错误");
		}




		Product product = new Product();
		product.setId(productVo.getId());//新增或编辑商品信息
		product.setName(name);//商品名称
		product.setImageurl(defaultpic.getAddress());//默认显示第一张图片
		ProductType productType = new ProductType();
		productType.setId(productTypeId);
		product.setProductType(productType);//商品分类
		Hunter hunter=new Hunter();
		hunter.setId(hunterId);
		product.setHunter(hunter);//批发商
	/*	product.setIsSubscription(isSubscription);//是否支持订金 0否 1支持
		Long djmoney=new BigDecimal(subscriptionMoney).multiply(new BigDecimal(100)).longValue();
		product.setSubscriptionMoney(isSubscription==1?djmoney:0);//订金
*/		product.setStatus(1);//状态 0 null 下架 1出售中
		//product.setStartNum(startNum);//保存起批数量

		return rpcProductService.saveHunterProduct(product,secificationlist,pdetailList,productAttachments);

	}

	/**
	 * 验证金额
	 * @param money
	 * @param erroword
	 * @return
	 */
	 public static String validateMoney(String money,String erroword){
		  	erroword=erroword==null?"":erroword;
			if(money==null||"".equals(money.replaceAll("\\s*", ""))){
				return "请填写"+erroword+"金额";
			}
			BigDecimal dj=null;
			try{
				dj=new BigDecimal(money);
			}catch(Exception e){
				return erroword+"金额格式不正确";
			}
			BigDecimal zero = new BigDecimal(0);
			if(zero.compareTo(dj)==0){//等于0
				return erroword+"金额不能等于0";
			}
			if(zero.compareTo(dj)==1){//小于0
				return erroword+"金额值非法";
			}
			if(money.indexOf(".")!=-1){//有小数点，小数点后位数不能大于2
				if(money.substring(money.indexOf(".")+1, money.length()).length()>2){
					return erroword+"金额,最多两位小数";
				}
			}
		return "SUCCESS";
	}

	 /**
	  * 上传商品详情图片
	  */
	@Override
	public Result mobileUploadProductDetailAttachment(MultipartFile urlfile, Object[] backUrl, String module,
			String[] extendFile) {
		Result result=attachmentService.mobileUploadAttachment(urlfile,backUrl,module,extendFile);
		logger.info("上传商品详情图片:"+result.getContent().toString());
		System.out.println("上传商品详情图片:"+result.getContent().toString());
		return result;
	}

	/**
	 * 上传商品轮播图片，最多不超过4张
	 */
	@Override
	public Result saveProductAttachment(Attachment attachment) {
		try {
			ProductAttachment productattachment = new ProductAttachment();
			productattachment.setAddress(attachment.getAddress());
			productattachment.setName(attachment.getName());
			productattachment=rpcProductAttachmentService.mobileUploadProductAttachment(productattachment);
			System.out.println("商品轮播图片上传idxx:"+productattachment.getId());
			logger.info("商品轮播图片上传idxx:"+productattachment.getId());
			return ResultUtils.returnSuccess("上传成功", productattachment);
		} catch (Exception e) {
			logger.error("商品轮播图片上传:"+e.getMessage());
			return ResultUtils.returnError("上传失败");
		}
	}

	/**
	 * 上传商品详情图片
	 */
	@Override
	public Result saveProductDetailAttachment(Attachment attachment) {
		return attachmentService.saveAttachment(attachment);
	}

	/**
	 * 根据商品id获取商品的所有信息，编辑时会用到
	 */
	@Override
	public Result getProductAllMsg(Long hunterid, Long productid) {
		// TODO Auto-generated method stub
		try {
		//=========查询商品基本的信息============================
	    if(productid==null){
	    	return ResultUtils.returnError("商品id不能为空");
	    }
		Map<String,Object> product= getProductBaseMsgById(productid);
		if(product==null){
			return ResultUtils.returnError("该商品不存在");
		}
		if(product.get("hunterId")==null){
			return ResultUtils.returnError("该商品信息错误，未关联批发商信息");
		}
		if(Long.valueOf(product.get("hunterId").toString()).longValue()!=hunterid.longValue()){
			return ResultUtils.returnError("商品信息与您批发商身份不符，无法查看编辑");
		}
		//=======查询商品规格信息===================================
		List<ProductSpecification> productSpecifications = productSpecificationService.getProductSpecificationByProductId(productid);
		//======查询商品详情信息====================================
		List<Map<String, Object>> productDetails = hunterProductDetailService.getProductDetailByProductId(productid);
		//=======查询商品的轮播图=====================================
		List<Map<String,Object>> productImags = productAttachmentService.getProductAttachmentByProductId(productid);
		
		product.put("productImags", productImags);
		product.put("specifications", productSpecifications);
		product.put("productDetails",productDetails);
		return ResultUtils.returnSuccess("查询成功", product);
		} catch (Exception e) {
			logger.error("编辑商品时查询商品详情回显："+e.getMessage());
			return ResultUtils.returnError("查询失败");
		}
	}

	@Override
	public Map<String, Object> getProductBaseMsgById(Long productid) {
		// TODO Auto-generated method stub
		return prodctDao.getProductBaseMsgById(productid);
	}

	/**
	 * 根据商品分类查询商品
	 */
	@Override
	public Result findProductByTypeList(Long productTypeId, Integer page, Integer rows) {
		if(productTypeId==null){
			return ResultUtils.returnError("请选择商品分类");
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("productTypeId",productTypeId);
		params.put("startIndex", (page-1)*rows);
		params.put("endIndex",rows);
		List<Map<String,Object>> productlist = prodctDao.findProductByTypeList(params);
		//销量格式化
		productlist.forEach(e->e.put("salenum",NumberFormat.getFormatNumber(String.valueOf(e.get("salenum")))));
		return ResultUtils.returnSuccess("查询成功", productlist);
	}

	@Override
	public Result getAllProduct(Long hunterId) {
		Result result = new Result();

		//获取回显参数
		Hunter hunter = hunterDao.getById(hunterId);
		String logoImg = attachmentDao.getLogoImgByHunterId(hunterId);
		if(hunter==null){

			result.setCode(0);
			result.setMsg("该商户不存在");
			return result;
		}
		ProductDisplayVo productDisplayVo = new ProductDisplayVo();
        productDisplayVo.setHunterId(hunterId);
		productDisplayVo.setLogoImage(logoImg);
		productDisplayVo.setNickname(hunter.getNickname());
		productDisplayVo.setDetail(hunter.getDetail());
		productDisplayVo.setPhone(hunter.getPhone());
		productDisplayVo.setBackgroundImage(hunter.getBackgroundImage());
		productDisplayVo.setAgreeStartTime(hunter.getAgreeStartTime());
		productDisplayVo.setAgreeEndTime(hunter.getAgreeEndTime());
		productDisplayVo.setSendStartTime(hunter.getSendStartTime());
		productDisplayVo.setSendEndTime(hunter.getSendEndTime());
		productDisplayVo.setStartMoney(hunter.getStartMoney());

		//查询商家所有商品分类
		List<ProcudtTypeVO> po = protucttypeDao.getProductTypeByHunterId(hunterId);
		for(int i=0;i<po.size();i++){
			if(po.get(i).getParentId()!=null){

			}
		}
		productDisplayVo.setProcudtTypeVO(po);

		result.setCode(1);
		result.setMsg("查询成功");
		result.setContent(productDisplayVo);
		return result;
	}

		@Override
		public Result getProductByType(Long hunterId, Long productTypeId,String phone,String uuid,Integer currentPage, Integer numPage) {
			Result result = new Result();
			Integer cpage = (currentPage-1)*numPage;
			List<ProductVo> pd = null;

			if(productTypeId == 0){
				 pd = prodctDao.getProductByHunterId(hunterId,cpage,numPage);
			}else {
				 pd = prodctDao.getProductByHunterIdAndTypeId(hunterId, productTypeId, cpage, numPage);

			}
			List<ProductShowVO> psvo = new ArrayList<>();
			for(ProductVo productVo:pd){
				ProductShowVO ps = new ProductShowVO();
				//查询商品规格信息
				List<ProductSpecificationVO> productSpecifications = productSpecificationService.getProductSpecificationVOList(productVo.getId());
				if(productSpecifications!=null){
					ps.setContentId(productSpecifications.get(0).getId());
					ps.setContent(productSpecifications.get(0).getContent());
					ps.setPrice(productSpecifications.get(0).getPrice());
                    //查询购物车数量
					if(phone!=null && uuid !=null) {
						Long buyNum = shopCartDao.getShopCartBuyNum(phone,uuid, productSpecifications.get(0).getId());
						ps.setBuyNum(buyNum);
					}
				}
				// 查询商品详情信息
				/*List<ProductDetailVO> productDetails = hunterProductDetailService.getProductDetailVoByProductId(productVo.getId());
				if(productDetails!=null){
					List<String> dis = new ArrayList<>();
					for(int i=0;i<productDetails.size();i++){
						dis.add(productDetails.get(i).getContent());
					}
					ps.setDescribe(dis);
				}*/
				//查询商品的轮播图
				List<ProductPictureVo> productImags = productAttachmentService.getProductPictureVOByProductId(productVo.getId());
				if(productImags!=null){
                    List<String> img = new ArrayList<>();
					for(int i=0;i<productImags.size();i++){
					    img.add(productImags.get(i).getAdress());
                    }
                    ps.setImgaddress(img);
				}
				ps.setId(productVo.getId());
				ps.setName(productVo.getName());
				Long commentNumAll = productVo.getBadCommentNum()+productVo.getCommonCommentNum()+productVo.getNiceCommentNum();
				ps.setCommentNumAll(commentNumAll);
				ps.setNiceCommentNum(productVo.getNiceCommentNum());
				ps.setStartNum(productVo.getStartNum());
				ps.setSaleNum(productVo.getSaleNum());
				ps.setProductTypeId(productVo.getProductTypeId());

				psvo.add(ps);

			}
			result.setCode(1);
			result.setMsg("查询成功");
			result.setContent(psvo);

			return result;
		}
	}
