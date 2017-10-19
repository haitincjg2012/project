package com.alqsoft.rpc.mobile.impl;

import com.alqsoft.entity.collectionhunter.CollectionHunter;
import com.alqsoft.rpc.mobile.RpcCollectionHunterService;
import com.alqsoft.service.collectionhunter.CollectionHunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Xuejizheng
 * @date 2017-03-10 18:25
 */
@Service
@Transactional
public class RpcCollectionHunterServiceImpl implements RpcCollectionHunterService {

    @Autowired
    private CollectionHunterService collectionHunterService;
    @Override
    public CollectionHunter saveAndModify(CollectionHunter collectionHunter) {
        return collectionHunterService.saveAndModify(collectionHunter);
    }

    @Override
    public boolean delete(Long aLong) {
        collectionHunterService.delete(aLong);
        return false;
    }

    @Override
    public CollectionHunter get(Long aLong) {
        return collectionHunterService.get(aLong);
    }
}
