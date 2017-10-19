package com.ph.shopping.facade.mapper;

import com.ph.shopping.facade.member.entity.Attachment;
import com.ph.shopping.facade.member.vo.IndustryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 批发的行业的分类
 * Created by wudi on 2017/9/2.
 */
@Repository
public interface IndustrysMapper {

    public List<IndustryVO> getIndustryDataList();

    public void addPicture(@Param("address") String address,@Param("name") String name);

    public Attachment selectPicture(@Param("address") String address,@Param("name") String name);

    public void addIndustry(@Param("id")Long id,@Param("name") String name,@Param("isTop") Integer isTop);

    public void  deleteIndustry(@Param("id") Long id);

    public void editIndustry(@Param("id")Long id,@Param("name") String name,@Param("isTop") Integer isTop);

    public List<Map> findFirstIndustry();

    public void addSecondIndstry(@Param("name")String name ,@Param("id")Long id);
}
