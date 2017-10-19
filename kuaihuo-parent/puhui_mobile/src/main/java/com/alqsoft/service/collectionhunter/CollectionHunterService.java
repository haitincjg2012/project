package com.alqsoft.service.collectionhunter;

import org.alqframework.result.Result;

/**
 * @author Xuejizheng
 * @date 2017-03-11 10:23
 */
public interface CollectionHunterService {

    Result collect(Long id, Long hid, Integer type);

    Result list(Long id, int page, int rows);
}
