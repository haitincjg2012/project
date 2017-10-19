package com.alqsoft.service.impl.alreadycashorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alqsoft.dao.alreadycashorder.AlreadyCashOrderDao;
import com.alqsoft.entity.alreadycashorder.AlreadyCashOrder;
import com.alqsoft.service.alreadycashorder.AlreadyCashOrderService;

/**
 * 
 * @Description: TODO
 * @author Shen.joe
 * @e-mail sudiluo_java@163.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年3月10日 上午10:24:57
 * 
 */
@Service
@Transactional(readOnly=true)
public class AlreadyCashOrderServiceImpl implements AlreadyCashOrderService {

	@Autowired
	private AlreadyCashOrderDao alreadyCashOrderDao;
	
	@Override
	public Integer findAlreadyCashOrderByMerSeqID(String merSeqId) {
		return alreadyCashOrderDao.findAlreadyCashOrderByMerSeqID(merSeqId);
	}

	@Override
	public boolean delete(Long arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AlreadyCashOrder get(Long arg0) {
		return alreadyCashOrderDao.findOne(arg0);
	}

	@Override
	@Transactional(readOnly=false)
	public AlreadyCashOrder saveAndModify(AlreadyCashOrder arg0) {
		AlreadyCashOrder save = alreadyCashOrderDao.save(arg0);
		if (null==save) {
			return null;
		}
		return save;
	}

}
