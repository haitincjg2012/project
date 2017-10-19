package com.alqsoft.service.impl.shopcart;

import com.alqsoft.dao.shopcart.ShopCartDao;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.shopcart.ShopCart;
import com.alqsoft.service.shopcart.ShopCartService;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 购物车操作实现
 * @author Xuejizheng
 * @date 2017-03-01 11:33
 * @see
 * @since 1.8
 */
@Service
@Transactional(readOnly = true)
public class ShopCartServiceImpl implements ShopCartService {

    private static Logger log = LoggerFactory.getLogger(ShopCartServiceImpl.class);

    @Autowired
    private ShopCartDao shopCartDao;

    @Override
    @CacheEvict(key="'alq_server_shopcart'+#shopCart.id",value = "alq_server_shopcart")
    public ShopCart saveAndModify(ShopCart shopCart) {
        return shopCartDao.save(shopCart);
    }

    @Override
    @CacheEvict(key="'alq_server_shopcart'+#aLong",value = "alq_server_shopcart")
    public boolean delete(Long aLong) {
        shopCartDao.delete(aLong);
        return false;
    }

    @Override
    @Cacheable(key="'alq_server_shopcart'+#aLong",value = "alq_server_shopcart")
    public ShopCart get(Long aLong) {
        return shopCartDao.findOne(aLong);
    }

    /**
     *  根据会员和规格信息删除购物车
     * @param uid  会员ID
     * @param psid  规格id
     * @return
     */
    @Override
    @Transactional
    public Result deleteByMemberAndProductSpecification(Long uid, Long psid) {
        Result result = new Result();
        if (Objects.isNull(uid) || Objects.isNull(psid)){
            result.setCode(0);
            result.setMsg("参数不能为空");
            return result;
        }
        try {

            shopCartDao.deleteByMemeberAndProductSpecification(uid,psid);
            result.setCode(1);
            result.setMsg("购物车删除成功");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            result.setCode(0);
            result.setMsg("接口调用失败");
        }finally {
            return result;
        }
    }

	@Override
	public List<ShopCart> findByMidAndPsId(Long id, Long psId) {
		return this.shopCartDao.findByMidAndPsId(id, psId);
	}

    @Override
    public Result deleteBatch(Member member,Long hid) {
        try {
            int res = shopCartDao.deleteShopCartsByHunterId(member.getId(),hid);
            log.info("批量删除记录数:{}",res);
            return ResultUtils.returnSuccess("删除成功");
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResultUtils.returnError("内部服务异常");
        }

    }
}
