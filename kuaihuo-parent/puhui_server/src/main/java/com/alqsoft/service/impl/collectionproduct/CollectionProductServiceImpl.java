package com.alqsoft.service.impl.collectionproduct;

import com.alqsoft.dao.collectionproduct.CollectionProductDao;
import com.alqsoft.entity.collectionproduct.CollectionProduct;
import com.alqsoft.service.collectionproduct.CollectionProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Xuejizheng
 * @date 2017-03-10 18:16
 */
@Service
@Transactional
public class CollectionProductServiceImpl implements CollectionProductService {

    @Autowired
    private CollectionProductDao collectionProductDao;

    @Override
    public CollectionProduct saveAndModify(CollectionProduct collectionProduct) {
        return collectionProductDao.save(collectionProduct);
    }

    @Override
    public boolean delete(Long aLong) {
        collectionProductDao.delete(aLong);
        return false;
    }

    @Override
    public CollectionProduct get(Long aLong) {
        return collectionProductDao.findOne(aLong);
    }
}
