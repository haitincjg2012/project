package com.ph.shopping.facade.product.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.ph.shopping.common.util.transform.MoneyTransUtil;
import com.ph.shopping.facade.mapper.ProductSkuMapper;
import com.ph.shopping.facade.product.dto.ProductSkuDTO;
import com.ph.shopping.facade.product.entity.ProductSku;
import com.ph.shopping.facade.product.service.IProductSkuService;
import com.ph.shopping.facade.product.vo.ProductSkuVO;

/**
 * 
 * @项目：phshopping-service-product
 *
 * @描述： 商品sku service层
 *
 * @作者： 杨颜光
 *
 * @创建时间：2017年5月12日 下午3:53:42
 *
 * @Copyright by  杨颜光
 */
@Component
@Service(version = "1.0.0")
public class ProductSkuServiceImpl implements IProductSkuService {
		
	
		@Autowired
		private   ProductSkuMapper productSkumapper;
		
		@Override
		public  List<ProductSku>  getProductSkuList(ProductSku productSku){
					//将所有价格除以一万
			List<ProductSku> list=this.productSkumapper.select(productSku);
				for (ProductSku sku : list) {
					sku.setReferencePrice(MoneyTransUtil.formatBigDecimalByDivisTwo(
							sku.getReferencePrice() == null ? new BigDecimal("0") : sku.getReferencePrice()));// 市场参考价
					sku.setRetailPrice(MoneyTransUtil
							.formatBigDecimalByDivisTwo(sku.getRetailPrice() == null ? new BigDecimal("0") : sku.getRetailPrice()));// 零售价
					sku.setPurchasePrice(MoneyTransUtil
							.formatBigDecimalByDivisTwo(sku.getPurchasePrice() == null ? new BigDecimal("0") : sku.getPurchasePrice()));// 进货价
					sku.setSettlementPrice(MoneyTransUtil.formatBigDecimalByDivisTwo(
							sku.getSettlementPrice() == null ? new BigDecimal("0") : sku.getSettlementPrice()));// 结算价
					sku.setFreight(MoneyTransUtil
							.formatBigDecimalByDivisTwo(sku.getFreight() == null ? new BigDecimal("0") : sku.getFreight()));// 物流费
				}
	    	return  list;
	    }
	    
		@Override
		public  ProductSku  getProductSku(ProductSku productSku){
				ProductSku sku=this.productSkumapper.selectOne(productSku);
				//将所有价格除以一万
				sku.setReferencePrice(MoneyTransUtil.formatBigDecimalByDivisTwo(
						sku.getReferencePrice() == null ? new BigDecimal("0") : sku.getReferencePrice()));// 市场参考价
				sku.setRetailPrice(MoneyTransUtil
						.formatBigDecimalByDivisTwo(sku.getRetailPrice() == null ? new BigDecimal("0") : sku.getRetailPrice()));// 零售价
				sku.setPurchasePrice(MoneyTransUtil
						.formatBigDecimalByDivisTwo(sku.getPurchasePrice() == null ? new BigDecimal("0") : sku.getPurchasePrice()));// 进货价
				sku.setSettlementPrice(MoneyTransUtil.formatBigDecimalByDivisTwo(
						sku.getSettlementPrice() == null ? new BigDecimal("0") : sku.getSettlementPrice()));// 结算价
				sku.setFreight(MoneyTransUtil
						.formatBigDecimalByDivisTwo(sku.getFreight() == null ? new BigDecimal("0") : sku.getFreight()));// 物流费
	    	return  sku;
	    }
	
	@Override
	public  	ProductSkuVO  selectSkuMinRetailPrice(ProductSkuDTO skuDTO){
		ProductSkuVO skuVO=this.productSkumapper.selectSkuMinRetailPrice(skuDTO);
		//将所有价格除以一万
		skuVO.setReferencePrice(MoneyTransUtil.formatBigDecimalByDivisTwo(
				skuVO.getReferencePrice() == null ? new BigDecimal("0") : skuVO.getReferencePrice()));// 市场参考价
		skuVO.setRetailPrice(MoneyTransUtil
				.formatBigDecimalByDivisTwo(skuVO.getRetailPrice() == null ? new BigDecimal("0") : skuVO.getRetailPrice()));// 零售价
		skuVO.setPurchasePrice(MoneyTransUtil
				.formatBigDecimalByDivisTwo(skuVO.getPurchasePrice() == null ? new BigDecimal("0") : skuVO.getPurchasePrice()));// 进货价
		skuVO.setSettlementPrice(MoneyTransUtil.formatBigDecimalByDivisTwo(
				skuVO.getSettlementPrice() == null ? new BigDecimal("0") : skuVO.getSettlementPrice()));// 结算价
		skuVO.setFreight(MoneyTransUtil
				.formatBigDecimalByDivisTwo(skuVO.getFreight() == null ? new BigDecimal("0") : skuVO.getFreight()));// 物流费
		return  skuVO;
	}
}