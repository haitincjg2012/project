package com.alqsoft.service.impl.shopcart;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alqsoft.dao.shopcart.ShopCartDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.product.Product;
import com.alqsoft.entity.productspecification.ProductSpecification;
import com.alqsoft.entity.shopcart.ShopCart;
import com.alqsoft.rpc.mobile.RpcShopCartService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.service.product.ProductService;
import com.alqsoft.service.productspecification.ProductSpecificationService;
import com.alqsoft.service.shopcart.ShopCartService;
import com.alqsoft.utils.StatusCodeEnums;
import com.alqsoft.vo.ProductSpecificationVO;
import com.alqsoft.vo.ShopCartVO;
import com.google.common.collect.Maps;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 购物车实现
 *
 * @author Xuejizheng
 * @date 2017-02-28 14:59
 * @see
 * @since 1.8
 */
@Service
@Transactional(readOnly = true)
public class ShopCartServiceImpl implements ShopCartService {

    private static Logger log = LoggerFactory.getLogger(ShopCartServiceImpl.class);

    @Autowired
    private ShopCartDao shopCartDao;

    @Autowired
    private RpcShopCartService rpcShopCartService;

    @Autowired
    private ProductSpecificationService productSpecificationService;
    @Autowired
    private ProductService productService;
    @Autowired
    private MemberService memberService;

    /**
     * 获取购物车列表
     *
     * @param map
     * @return
     */
    @Override
    public Result getShopCartList(Map<String, Object> map) {
        Result result = new Result();
        try {
            log.info(" shopcart list param:{}", map);
            Long uid = (Long) map.get("uid");
            if (uid == null) {
                result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
                result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
                return result;
            }

            List<ShopCartVO> hunterList = shopCartDao.getShopCartListByMemberId(uid);
            int size = hunterList.size();
            JSONArray cartAry = new JSONArray();
            for (ShopCartVO hunterVo : hunterList) {
                Long hunterId = hunterVo.getHunterId();
                String nickname = hunterVo.getNickname();
                JSONObject hunterObj = new JSONObject();


                //批发商ID 和昵称
                hunterObj.put("merchantId", hunterId);
                hunterObj.put("merchantName", nickname);
                map.put("hid", hunterId);
                //出售中
                map.put("status", 1);
                List<ShopCartVO> list = shopCartDao.getShopCartList(map);

                int cartSize = list.size();
                if (cartSize > 0) {
                    hunterObj.put("lists", list);
                }
                cartAry.add(hunterObj);
            }
            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("uid", uid);
            paramMap.put("status", 0);
            //下架商品列表
            List<ShopCartVO> invalidateShopCartList = shopCartDao.getShopCartList(paramMap);
            int invalidateSize = invalidateShopCartList.size();
            if (size == 0 && invalidateSize == 0) {
                result.setCode(StatusCodeEnums.SUCCESS_NO_DATA.getCode());
                result.setMsg(StatusCodeEnums.SUCCESS_NO_DATA.getMsg());
                return result;
            }
            if (!invalidateShopCartList.isEmpty()) {
                JSONObject invalidateObject = new JSONObject();
                //批发商ID 和昵称
                invalidateObject.put("merchantId", 0);
                invalidateObject.put("merchantName", "下架商品");
                invalidateObject.put("lists", invalidateShopCartList);
                cartAry.add(invalidateObject);
            }
            result.setCode(StatusCodeEnums.SUCCESS.getCode());
            result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
            result.setContent(cartAry);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
        } finally {
            return result;
        }

    }

    /**
     * 修改购物车
     *
     * @param uid
     * @param cid
     * @param num
     * @return
     */
    @Override
    @Transactional
    public Result update(Long uid, Long cid, Long num) {
        if (Objects.isNull(uid) || Objects.isNull(cid) || Objects.isNull(num)) {
            return ResultUtils.returnError("参数异常");
        }
        
        /*//商品数量不能小于1
        if (num<1){
            return ResultUtils.returnError("商品数量不能小于1");
        }*/

        Map<String, Object> map = Maps.newHashMap();
        map.put("uid", uid);
        map.put("cid", cid);
        try {
            ShopCartVO shopCartVO = shopCartDao.getShopCartById(map);
            if (shopCartVO == null) {
                return ResultUtils.returnError("对应购物车信息不存在.");
            }
            Long spid = shopCartVO.getProductSpecificationId();
            ProductSpecificationVO productSpecificationVO =
                    productSpecificationService.getProductSpecificationVO(spid);

            if (Objects.isNull(productSpecificationVO)) {
                return ResultUtils.returnError("规格信息不存在.");
            }

            Long productId = productSpecificationVO.getProductId();
            if (Objects.isNull(productId)) {
                return ResultUtils.returnError("商品不存在.");
            }

            //商品数量不能小于起批数量
            Product product = productService.getProductById(productId);

            Integer startNum = product.getStartNum();
            if (startNum.equals("") || startNum == null) {
                startNum = 1;
            }

            if (num < startNum) {
                return ResultUtils.returnError("购买商品数量不能小于起批数量");
            }

            //商品状态
            Integer productStatus = productSpecificationVO.getProductStatus();
            if (!Objects.equals(productStatus, 1)) {
                return ResultUtils.returnError("商品已下架.");
            }
            Long productTypeId = productSpecificationVO.getProductTypeId();
            if (Objects.isNull(productTypeId)) {
                return ResultUtils.returnError("商品分类不存在.");
            }

            Long totalNum = productSpecificationVO.getNum();
            if (Objects.isNull(totalNum) || num > totalNum) {
                return ResultUtils.returnError("商品库存不足");
            }

            ShopCart shopCart = new ShopCart();
            shopCart.setId(cid);
            Member member = new Member();
            member.setId(uid);
            shopCart.setMember(member);
            shopCart.setBuyNum(num);
            ProductSpecification productSpecification = new ProductSpecification();
            productSpecification.setId(spid);
            shopCart.setProductSpecification(productSpecification);

            rpcShopCartService.saveAndModify(shopCart);
            return ResultUtils.returnSuccess("修改成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultUtils.returnError("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param uid
     * @param cid
     * @return
     */
    @Override
    @Transactional
    public Result delete(Long uid, Long cid) {
        Result result = new Result();
        try {
            ShopCart shopCart = rpcShopCartService.get(cid);
            if (shopCart != null) {
                Member member = shopCart.getMember();
                if (member != null) {
                    Long mid = member.getId();
                    if (uid.equals(mid)) {
                        rpcShopCartService.delete(cid);
                        result.setCode(StatusCodeEnums.SUCCESS.getCode());
                        result.setMsg("删除成功.");
                        return result;
                    }
                    return ResultUtils.returnError("购物车和用户信息不匹配.");
                }
            }
            return ResultUtils.returnError("购物车信息不存在");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
            return result;
        }

    }

    /**
     * 添加入购物车
     *
     * @param rmember 用户id
     * @param spid    商品规格ID
     * @param num     购买数量
     * @param stid    商品分类ID
     * @return
     */
    @Override
    @Transactional
    public Result add(Member rmember, Long spid, Long num, Long stid) {

        Long hunterid = memberService.getHunteridByMemberid(rmember.getId());
        if (hunterid != null) {
            return ResultUtils.returnError("只有商户能进行此操作，请登录商户");
        }

        Long uid = rmember.getId();
        if (Objects.isNull(uid) || Objects.isNull(spid) || Objects.isNull(num) || Objects.isNull(stid)) {
            return ResultUtils.returnError("参数错误");
        }

        if (num.longValue() < 0) {
            return ResultUtils.returnError("商品数量不能小于0");
        }
        try {
            ProductSpecificationVO productSpecificationVO =
                    productSpecificationService.getProductSpecificationVO(spid);
            if (Objects.isNull(productSpecificationVO)) {
                return ResultUtils.returnError("商户商品、规格信息不存在");
            }
            Long hid = productSpecificationVO.getHunterId();
            Hunter hunter = rmember.getHunter();
            if (hunter != null) {
                Long shid = hunter.getId();
                if (Objects.equals(shid, hid)) {
                    return ResultUtils.returnError("批发商不能购买自己的商品");
                }
            }
            Long productId = productSpecificationVO.getProductId();
            if (Objects.isNull(productId)) {
                return ResultUtils.returnError("商品不存在.");
            }

            //购买商品数量要大于起批数量 
            //修改人：高歌
            Product product = productService.getProductById(productId);
            // Integer startNum=product.getStartNum();
          /*
            
            if("".equals(startNum)||startNum==null){
            	startNum=1;
            }
            	
            if (num<startNum){
                return ResultUtils.returnError("商品数量不能小于起批数量");
              }*/
            //商品状态
            Integer productStatus = productSpecificationVO.getProductStatus();
            if (!Objects.equals(productStatus, 1)) {
                return ResultUtils.returnError("商品已下架.");
            }
            Long productTypeId = productSpecificationVO.getProductTypeId();
            if (Objects.isNull(productTypeId)) {
                return ResultUtils.returnError("商品分类不存在.");
            }
            //商品类型和规格是否匹配
            if (!Objects.equals(productTypeId, stid)) {
                return ResultUtils.returnError("商品规格和分类信息不匹配.");
            }
            Long totalNum = productSpecificationVO.getNum();
            /*if (Objects.isNull(totalNum) || num > totalNum) {
                return ResultUtils.returnError("商品库存不足");
            }*/

            Map<String, Object> paramMap = Maps.newHashMap();
            paramMap.put("uid", uid);
            paramMap.put("spid", spid);
            ShopCartVO oldCart = shopCartDao.getShopCart(paramMap);
            //新增
            if (oldCart == null) {
                ShopCart cart = new ShopCart();
                ProductSpecification specification = new ProductSpecification();
                specification.setId(spid);
                Member member = new Member();
                member.setId(uid);
                cart.setMember(member);
                cart.setProductSpecification(specification);
                cart.setBuyNum(num);
                rpcShopCartService.saveAndModify(cart);
                Long price = shopCartDao.getTotalPriceByMember(uid);
                String priceStr = "0.00";
                if (Objects.nonNull(price)) {
                    priceStr = BigDecimal.valueOf(price).divide(BigDecimal.valueOf(100L)).toString();
                }
               return  ResultUtils.returnSuccess("成功",priceStr);
                //更新
            } else {
                //删除
                if (Objects.equals(num, 0L)) {
                    rpcShopCartService.delete(oldCart.getId());
                    Long price = shopCartDao.getTotalPriceByMember(uid);
                    String priceStr = "0.00";
                    if (Objects.nonNull(price)) {
                        priceStr = BigDecimal.valueOf(price).divide(BigDecimal.valueOf(100L)).toString();
                    }
                    return ResultUtils.returnSuccess("删除成功",priceStr);
                }
                ShopCart shopCart = new ShopCart();
                shopCart.setId(oldCart.getId());
                Long byNum = num;
                /*if (byNum > totalNum) {
                    return ResultUtils.returnError("商品库存不足");
                }*/
                shopCart.setBuyNum(byNum);
                Member member = new Member();
                member.setId(uid);
                shopCart.setMember(member);
                ProductSpecification specification = new ProductSpecification();
                specification.setId(oldCart.getProductSpecificationId());
                shopCart.setProductSpecification(specification);
                rpcShopCartService.saveAndModify(shopCart);
                Long price = shopCartDao.getTotalPriceByMember(uid);
                String priceStr = "0.00";
                if (Objects.isNull(price) || !Objects.equals(price, 0L)) {
                    priceStr = BigDecimal.valueOf(price).divide(BigDecimal.valueOf(100L)).toString();
                }
                return ResultUtils.returnSuccess("成功",priceStr);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultUtils.returnError("系统异常");
        }
    }

    /**
     * 购物车批量更新
     *
     * @param uid
     * @param cids
     * @param nums
     * @return
     */
    @Override
    @Transactional
    public Result batchUpdate(Long uid, String cids, String nums) {
        if (Objects.isNull(uid) || StringUtils.isBlank(cids) || StringUtils.isBlank(nums)) {
            return ResultUtils.returnError("参数异常");
        }
        try {
            String[] cidAry = cids.split(",");
            String[] numAry = nums.split(",");
            int cidLength = cidAry.length;
            int numLength = numAry.length;
            if (cidLength != numLength) {
                return ResultUtils.returnError("购物车和商品数量不对应");
            }
            for (int i = 0; i < cidLength; i++) {
                long num = Long.parseLong(numAry[i]);
                long cid = Long.parseLong(cidAry[i]);
                //商品数量不能小于1
                if (num < 1) {
                    return ResultUtils.returnError("商品数量不能小于1");
                }

                Map<String, Object> map = Maps.newHashMap();
                map.put("uid", uid);
                map.put("cid", cid);

                ShopCartVO shopCartVO = shopCartDao.getShopCartById(map);
                if (shopCartVO == null) {
                    return ResultUtils.returnError("对应购物车信息不存在.");
                }
                Long spid = shopCartVO.getProductSpecificationId();
                ProductSpecificationVO productSpecificationVO =
                        productSpecificationService.getProductSpecificationVO(spid);

                if (Objects.isNull(productSpecificationVO)) {
                    return ResultUtils.returnError("规格信息不存在.");
                }

                Long productId = productSpecificationVO.getProductId();
                if (Objects.isNull(productId)) {
                    return ResultUtils.returnError("商品不存在.");
                }

                //商品状态
                Integer productStatus = productSpecificationVO.getProductStatus();
                if (!Objects.equals(productStatus, 1)) {
                    return ResultUtils.returnError("商品已下架.");
                }
                Long productTypeId = productSpecificationVO.getProductTypeId();
                if (Objects.isNull(productTypeId)) {
                    return ResultUtils.returnError("商品分类不存在.");
                }

                Long totalNum = productSpecificationVO.getNum();
                if (Objects.isNull(totalNum) || num > totalNum) {
                    return ResultUtils.returnError("商品库存不足");
                }

                ShopCart shopCart = new ShopCart();
                shopCart.setId(cid);
                Member member = new Member();
                member.setId(uid);
                shopCart.setMember(member);
                shopCart.setBuyNum(num);
                ProductSpecification productSpecification = new ProductSpecification();
                productSpecification.setId(spid);
                shopCart.setProductSpecification(productSpecification);
                rpcShopCartService.saveAndModify(shopCart);
            }
            return ResultUtils.returnSuccess("修改成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultUtils.returnError("修改失败");
        }
    }

    /**
     * 获取用户购物车数量
     *
     * @param id
     * @return
     */
    @Override
    public Result getShopCartCount(Long id) {
        long count = 0L;
        try {
            count = shopCartDao.getShopCartCountByMemberId(id);
            Map<String, Object> map = Maps.newHashMap();
            map.put("count", count);
            return ResultUtils.returnSuccess("成功", map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
        }
    }

    @Override
    public Result batchAdd(Member rmember, String spids, String nums, String stids) {
        Long hunterid = memberService.getHunteridByMemberid(rmember.getId());
        if (hunterid != null) {
            return ResultUtils.returnError("只有商户能进行此操作，请登录商户");
        }

        Long uid = rmember.getId();
        Result result = new Result();
        if (Objects.isNull(uid) || Objects.isNull(spids) || Objects.isNull(nums) || Objects.isNull(stids)) {
            result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
            result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
            return result;
        }
        if (spids.length() != nums.length() && nums.length() != stids.length()) {
            return ResultUtils.returnError("参数信息不匹配");
        }

        /*if (num<1){
            return ResultUtils.returnError("商品数量不能小于1");
          }*/
        try {
            String[] spidAry = spids.split(",");
            String[] numAry = nums.split(",");
            String[] stidAry = stids.split(",");

            for (int i = 0; i < spids.length(); i++) {
                ProductSpecificationVO productSpecificationVO =
                        productSpecificationService.getProductSpecificationVO(Long.valueOf(spidAry[i]));
                Long hid = productSpecificationVO.getHunterId();
                Hunter hunter = rmember.getHunter();
                if (hunter != null) {
                    Long shid = hunter.getId();
                    if (Objects.equals(shid, hid)) {
                        return ResultUtils.returnError("批发商不能购买自己的商品");
                    }
                }
                if (Objects.isNull(productSpecificationVO)) {
                    return ResultUtils.returnError("规格信息不存在.");
                }

                Long productId = productSpecificationVO.getProductId();
                if (Objects.isNull(productId)) {
                    return ResultUtils.returnError("商品不存在.");
                }

                //购买商品数量要大于起批数量
                //修改人：高歌
                Product product = productService.getProductById(productId);
                Integer startNum = product.getStartNum();


                if ("".equals(startNum) || startNum == null) {
                    startNum = 1;
                }
              /*  if (num<startNum){
                    return ResultUtils.returnError("商品数量不能小于起批数量");
                }*/
                //商品状态
                Integer productStatus = productSpecificationVO.getProductStatus();
                if (!Objects.equals(productStatus, 1)) {
                    return ResultUtils.returnError("商品已下架.");
                }
                Long productTypeId = productSpecificationVO.getProductTypeId();
                if (Objects.isNull(productTypeId)) {
                    return ResultUtils.returnError("商品分类不存在.");
                }
                //商品类型和规格是否匹配
                if (!Objects.equals(productTypeId, Long.valueOf(stidAry[i]))) {
                    return ResultUtils.returnError("商品规格和分类信息不匹配.");
                }
                Long totalNum = productSpecificationVO.getNum();
                if (Objects.isNull(totalNum) || Long.valueOf(numAry[i]).longValue() > totalNum.longValue()) {
                    return ResultUtils.returnError("商品库存不足");
                }

                Map<String, Object> paramMap = Maps.newHashMap();
                paramMap.put("uid", uid);
                paramMap.put("spid", Long.valueOf(spidAry[i]));
                ShopCartVO oldCart = shopCartDao.getShopCart(paramMap);
                //新增
                if (oldCart == null) {
                    ShopCart cart = new ShopCart();
                    ProductSpecification specification = new ProductSpecification();
                    specification.setId(Long.valueOf(spidAry[i]));
                    Member member = new Member();
                    member.setId(uid);
                    cart.setMember(member);
                    cart.setProductSpecification(specification);
                    cart.setBuyNum(Long.valueOf(numAry[i]));
                    rpcShopCartService.saveAndModify(cart);
                    result.setCode(StatusCodeEnums.SUCCESS.getCode());
                    result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
                    return result;
                    //更新
                } else {
                    ShopCart shopCart = new ShopCart();
                    shopCart.setId(oldCart.getId());
                    Long byNum = oldCart.getBuyNum() + Long.valueOf(numAry[i]);
                    if (byNum > totalNum) {
                        return ResultUtils.returnError("商品库存不足");
                    }
                    shopCart.setBuyNum(byNum);
                    Member member = new Member();
                    member.setId(uid);
                    shopCart.setMember(member);
                    ProductSpecification specification = new ProductSpecification();
                    specification.setId(oldCart.getProductSpecificationId());
                    shopCart.setProductSpecification(specification);
                    rpcShopCartService.saveAndModify(shopCart);
                    result.setCode(StatusCodeEnums.SUCCESS.getCode());
                    result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
                    return result;
                }
            }
            return ResultUtils.returnSuccess("添加成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
            return result;
        }
    }

    @Override
    public Result getHunterShopCartList(Map<String, Object> map) {
        Result result = new Result();
        try {
            log.info(" 购物车列表 param:{}", map);
            Long uid = (Long) map.get("uid");
            Long hid = (Long) map.get("hid");
            if (uid == null) {
                result.setCode(StatusCodeEnums.ERROR_PARAM.getCode());
                result.setMsg(StatusCodeEnums.ERROR_PARAM.getMsg());
                return result;
            }
            List<Map<String, Object>> hunterShopCartList = shopCartDao.getShopCartListByHunterAndMember(hid, uid);
            result.setCode(StatusCodeEnums.SUCCESS.getCode());
            result.setMsg(StatusCodeEnums.SUCCESS.getMsg());
            result.setContent(hunterShopCartList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.setCode(StatusCodeEnums.ERROR.getCode());
            result.setMsg(StatusCodeEnums.ERROR.getMsg());
        } finally {
            return result;
        }
    }

    /**
     * 清空购物车
     *
     * @param member
     * @return
     */
    @Override
    public Result batchDelete(Member member,Long hid) {
        return rpcShopCartService.batchDelete(member,hid);
    }

}
