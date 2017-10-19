package com.alqsoft.dao.memberaddress;

import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.memberaddress.MemberAddress;
import org.alqframework.orm.hibernate.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Xuejizheng
 * @date 2017-03-01 14:33
 * @see
 * @since 1.8
 */
public interface MemberAddressDao extends BaseDao<MemberAddress> {
    @Modifying
    @Query(value = "update alq_member_address set is_default = 0 where member_id = ?1 ",nativeQuery = true)
    int updateBatchNotDefault(@Param("mid") Long mid);

	@Query("FROM MemberAddress AS ma WHERE ma.id=:id AND isDelete=0")
    public MemberAddress getmemAddById(@Param("id") Long id);

    MemberAddress getMemberAddressByMemberAndIsDefault(Member member,int isDefault);

    MemberAddress getMemberAddressByMemberAndIsDefaultAndIsDelete(Member member,int isDefault,int isDelete);
}
