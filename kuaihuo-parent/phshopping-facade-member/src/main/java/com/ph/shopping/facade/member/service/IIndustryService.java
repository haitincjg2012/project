package com.ph.shopping.facade.member.service;

import com.ph.shopping.common.util.page.PageBean;
import com.ph.shopping.common.util.result.Result;
import com.ph.shopping.facade.member.dto.IndustrysDTO;

import java.util.List;
import java.util.Map;

/**批发的行业分类
 * Created by wudi on 2017/9/1.
 */
public interface IIndustryService {

    public Result getIndustryDataList(PageBean page,IndustrysDTO dto);

    public Result addIndustry(String address,String name,Integer isTop);

    public Result deleteIndustry(Long id);

    public Result editIndustry(Long id,String name,Integer isTop);

    public List<Map> findFirstIndustry();

    public Result addSecondIndstry(String name,Long id);

}
