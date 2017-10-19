package cm.ph.shopping.facade.order.service;

import cm.ph.shopping.facade.order.dto.PhOrderAddressDTO;
import com.ph.shopping.common.util.result.Result;

/**
 * @author 熊克文
 * @Description: 会员收货地址接口
 */
public interface IOrderAddressService {


    /**
     * @Description: 新增收货地址
     * @author 熊克文
     */
    Result addOrderAddress(PhOrderAddressDTO dto);

    /**
     * @Description: 修改收货地址
     * @author 熊克文
     */
    Result updateOrderAddress(PhOrderAddressDTO orderAddress);

    /**
     * @param memberId 用户id
     * @Description: 查询收货地址
     * @author 熊克文
     */
    Result listOrderAddressByMemberId(Long memberId);

    /**
     *
     * 查询收货地址详情
     * @param addressId 地址id
     * @Description:
     * @author 熊克文
     */
    Result getAddressDetailById(Long addressId);

    /**
     * 设置会员默认收货地址
     * @param addressId 收货地址id
     * @param memberId  会员id
     * @author 熊克文
     */
    Result setDefaultAddressByMemberId(Long addressId, Long memberId);
}



