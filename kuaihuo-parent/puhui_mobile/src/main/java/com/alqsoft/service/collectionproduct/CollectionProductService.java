package com.alqsoft.service.collectionproduct;

import org.alqframework.result.Result;

/**
 * @author Xuejizheng
 * @date 2017-03-11 10:23
 */
public interface CollectionProductService {

    Result collect(Long id, Long pid, Integer type);

    Result list(Long id, int page, int rows);
}
