package com.alqsoft.dao.memberaddress;

import java.util.List;
import java.util.Map;

import org.alqframework.orm.mybatis.MyBatisRepository;

import com.alqsoft.entity.memberaddress.MemberAddress;
import com.alqsoft.vo.MemberAddressVO;

/**
 * @author Xuejizheng
 * @date 2017-02-28 18:18
 * @see
 * @since 1.8
 */
@MyBatisRepository
public interface MemberAddressDao {

    List<MemberAddressVO> getAddressList(Map<String,Object> map);

    MemberAddressVO getDefaultAddress(Map<String, Object> map);

	public MemberAddress findAddressById(Long id);

	public Map<String, Object> getDefAddByMid(Long id);
}
