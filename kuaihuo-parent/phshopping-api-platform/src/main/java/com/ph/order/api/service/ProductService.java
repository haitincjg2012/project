package com.ph.order.api.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ph.order.api.controller.vo.PurchaseOrderProductVO;
import com.ph.order.api.controller.vo.PurchaseProductVO;
import com.ph.shopping.facade.product.dto.ProductSnapshotDTO;
import com.ph.shopping.facade.product.entity.ProductSku;
import com.ph.shopping.facade.product.service.IProductService;
import com.ph.shopping.facade.product.service.IProductSkuService;
import com.ph.shopping.facade.product.service.IProductSnapshotService;
import com.ph.shopping.facade.product.vo.ProductSnapshotVO;
import com.ph.shopping.facade.product.vo.ProductVO;
import com.ph.shopping.facade.spm.vo.SupplierVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用商品模块的service
 *
 * @author 郑朋
 * @create 2017/5/27
 **/
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Reference(version = "1.0.0")
    IProductService productService;

    @Reference(version = "1.0.0")
    IProductSkuService productSkuService;

    @Reference(version = "1.0.0")
    IProductSnapshotService productSnapshotService;

    @Autowired
    SpmService spmService;

    /**
     * @methodname getProduct 的描述：通过商品id查询商品信息
     * @param list
     * @return java.util.List<com.ph.order.api.controller.vo.PurchaseProductVO>
     * @author 郑朋
     * @create 2017/5/27
     */
    public List<PurchaseProductVO> getProduct(List<Long> list) {
        logger.info("通过商品ids查询商品信息接口入参：ids={}", JSON.toJSONString(list));
        List<ProductVO> productVOList = productService.getProductListForOder(list);
        logger.info("通过商品ids查询商品信息接口返回值：productVOList={}", JSON.toJSONString(productVOList));
        return mergeProduct(productVOList);
    }

    private List<PurchaseProductVO> mergeProduct(List<ProductVO> list) {
        PurchaseProductVO purchaseProductVO;
        Map<Long,List<PurchaseOrderProductVO>> map = new HashMap<>();
        List<PurchaseOrderProductVO> group;
        ProductVO item;
        PurchaseOrderProductVO vo;
        List<PurchaseProductVO> productVOS = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            item = list.get(i);
            //查询商品快照
            ProductSnapshotVO productSnapshotVO = getProductSnap(item.getId());
            vo = new PurchaseOrderProductVO();
            BeanUtils.copyProperties(item,vo);
            if (null != productSnapshotVO) {
                vo.setProductSnapId(productSnapshotVO.getId());
            }
            if (map.containsKey(item.getSupplierId())) {
                map.get(item.getSupplierId()).add(vo);
            } else {
                group = new ArrayList<>();
                group.add(vo);
                map.put(item.getSupplierId(),group);
            }
        }
        //循环map，通过供应商id查询供应商名称
        Long supplierId;
        SupplierVO supplierVO;
        for (Map.Entry<Long, List<PurchaseOrderProductVO>> entry : map.entrySet()) {
            purchaseProductVO = new PurchaseProductVO();
            supplierId = entry.getKey();
            BeanUtils.copyProperties(entry.getValue(),purchaseProductVO);
            supplierVO = spmService.getSupplierById(supplierId);
            purchaseProductVO.setSupplierId(supplierVO.getUserId());
            purchaseProductVO.setSupplierName(supplierVO.getSupplierName());
            //供应商名称
            purchaseProductVO.setProductVOList(entry.getValue());
            productVOS.add(purchaseProductVO);
        }
        return productVOS;
    }

    /**
     * @methodname getProductSku 的描述：通过商品id查询sku信息
     * @param productId
     * @return java.util.List<com.ph.shopping.facade.product.entity.ProductSku>
     * @author 郑朋
     * @create 2017/5/27
     */
    public List<ProductSku> getProductSku(Long productId) {
        ProductSku productSku = new ProductSku();
        productSku.setProductId(productId);
        logger.info("通过商品id查询sku信息接口入参：productSku={}", JSON.toJSONString(productSku));
        List<ProductSku> productSkuList  = productSkuService.getProductSkuList(productSku);
        logger.info("通过商品id查询sku信息接口返回值：productSkuList={}", JSON.toJSONString(productSkuList));
        return productSkuList;
    }

    private ProductSnapshotVO getProductSnap(Long productId) {
        ProductSnapshotDTO productSnapshotDTO = new ProductSnapshotDTO();
        productSnapshotDTO.setProductId(productId);
        logger.info("通过商品id查询商品快照信息接口入参：productSku={}", JSON.toJSONString(productSnapshotDTO));
        ProductSnapshotVO productSnapshotVo =  productSnapshotService.selectProductSnapshotByproductId(productSnapshotDTO);
        logger.info("通过商品id查询商品快照信息接口返回值：productSkuList={}", JSON.toJSONString(productSnapshotVo));
        return productSnapshotVo;
    }

}
