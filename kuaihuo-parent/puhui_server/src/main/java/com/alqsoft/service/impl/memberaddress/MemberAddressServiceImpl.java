package com.alqsoft.service.impl.memberaddress;

import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alqsoft.dao.memberaddress.MemberAddressDao;
import com.alqsoft.entity.area.Area;
import com.alqsoft.entity.member.Member;
import com.alqsoft.entity.memberaddress.MemberAddress;
import com.alqsoft.service.area.AreaService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.service.memberaddress.MemberAddressService;

/**
 * 收货地址管理
 * @author Xuejizheng
 * @date 2017-03-01 17:09
 * @see
 * @since 1.8
 */
@Service
@Transactional
public class MemberAddressServiceImpl implements MemberAddressService {

    @Autowired
    private MemberAddressDao memberAddressDao;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AreaService areaService;

    @Override
   // @CacheEvict(key="'alq_server_memeber_address'+#memberAddress.id",value="alq_server_memeber_address")
    public MemberAddress saveAndModify(MemberAddress memberAddress) {
        return memberAddressDao.save(memberAddress);
    }

    @Override
   // @CacheEvict(key="'alq_server_memeber_address'+#aLong",value="alq_server_memeber_address")
    public boolean delete(Long aLong) {
        memberAddressDao.delete(aLong);
        return false;
    }

    @Override
   // @Cacheable(key="'alq_server_memeber_address'+#aLong",value="alq_server_memeber_address")
    public MemberAddress get(Long aLong) {
        return memberAddressDao.findOne(aLong);
    }

	@Override
	public Result saveAddressAndMid(MemberAddress memberAddress) {
		try {
			Long mId = memberAddress.getMember().getId();
			Long pId = memberAddress.getProArea().getId();
			Long cId = memberAddress.getCityArea().getId();
			Member member = this.memberService.get(mId);
			Area proArea = this.areaService.get(pId);
			Area cityArea = this.areaService.get(cId);
			memberAddress.setMember(member);
			memberAddress.setProArea(proArea);
			memberAddress.setCityArea(cityArea);
			this.saveAndModify(memberAddress);
			return ResultUtils.returnSuccess("添加地址成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("添加地址失败");
		}
	}

	@Override
    public boolean updateBatchNotDefault(Long mid) {
        return memberAddressDao.updateBatchNotDefault(mid) >= 0;
    }

	@Override
	public Result updateAddressAndMid(MemberAddress memberAddress) {
		try {
			MemberAddress address = this.get(memberAddress.getId());
			address.setProArea(memberAddress.getProArea());
			address.setCityArea(memberAddress.getCityArea());
			address.setDetailAddress(memberAddress.getDetailAddress());
			address.setMobile(memberAddress.getMobile());
			address.setUserName(memberAddress.getUserName());
			this.saveAndModify(address);
			return ResultUtils.returnSuccess("修改地址成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("修改地址失败");
		}
	}

	@Override
	public Result delAddressById(MemberAddress memberAddress) {
		try {
			Long mId = memberAddress.getMember().getId();
			Member member = this.memberService.get(mId);
			memberAddress.setMember(member);
			memberAddress.setIsDelete(1);
			this.saveAndModify(memberAddress);
			return ResultUtils.returnSuccess("删除地址成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return ResultUtils.returnError("删除地址失败");
		}
	}

	@Override
	public MemberAddress getmemAddById(Long id) {
		return this.memberAddressDao.getmemAddById(id);
	}

	@Override
	public MemberAddress getDefaultMemberAddressByMember(Member member) {
		return memberAddressDao.getMemberAddressByMemberAndIsDefault(member,1);
	}

	@Override
	public MemberAddress getMemberAddressByMemberAndIsDefaultAndIsDelete(Member member) {
		return memberAddressDao.getMemberAddressByMemberAndIsDefaultAndIsDelete(member,1,0);
	}
}
