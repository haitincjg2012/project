package com.alqsoft.rpc.mobile.impl;

import com.alqsoft.entity.collectionproduct.CollectionProduct;
import com.alqsoft.rpc.mobile.RpcCollectionProductService;
import com.alqsoft.service.collectionproduct.CollectionProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Xuejizheng
 * @date 2017-03-11 10:11
 */
@Service
@Transactional
public class RpcCollectionProductServiceImpl implements RpcCollectionProductService {

    @Autowired
    private CollectionProductService collectionProductService;
    @Override
    public CollectionProduct saveAndModify(CollectionProduct collectionProduct) {
        return collectionProductService.saveAndModify(collectionProduct);
    }

    @Override
    public boolean delete(Long aLong) {
        collectionProductService.delete(aLong);
        return false;
    }

    @Override
    public CollectionProduct get(Long aLong) {

        return collectionProductService.get(aLong);
    }
}
