package com.alqsoft.service.impl.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.member.MemberDao;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.member.MemberService;

@Service
@Transactional(readOnly=true)
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDao memeberDao;
	
	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Member get(Long arg0) {
		return this.memeberDao.findOne(arg0);
	}

	@Transactional
	@Override
	public Member saveAndModify(Member arg0) {
		return this.memeberDao.save(arg0);
	}
	
	@Transactional
	@Override
	public Member getRowLock(Long id) {
		// TODO Auto-generated method stub
		return memeberDao.getRowLock(id);
	}

}
