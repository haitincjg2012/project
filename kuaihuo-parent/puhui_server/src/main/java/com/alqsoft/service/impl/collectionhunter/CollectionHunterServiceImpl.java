package com.alqsoft.service.impl.collectionhunter;

import com.alqsoft.dao.collectionhunter.CollectionHunterDao;
import com.alqsoft.entity.collectionhunter.CollectionHunter;
import com.alqsoft.service.collectionhunter.CollectionHunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Xuejizheng
 * @date 2017-03-10 18:20
 */
@Service
@Transactional
public class CollectionHunterServiceImpl implements CollectionHunterService {

    @Autowired
    private CollectionHunterDao collectionHunterDao;

    @Override
    public CollectionHunter saveAndModify(CollectionHunter collectionHunter) {
        return collectionHunterDao.save(collectionHunter);
    }

    @Override
    public boolean delete(Long aLong) {
        collectionHunterDao.delete(aLong);
        return false;
    }

    @Override
    public CollectionHunter get(Long aLong) {

        return collectionHunterDao.findOne(aLong);
    }
}
