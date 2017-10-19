package com.ph.shopping.facade.mapper;

import com.ph.shopping.facade.member.dto.AdAtachmentDTO;
import com.ph.shopping.facade.permission.vo.AdAttachmentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @项目：phshopping-service-permission
 * @描述： 按钮mapper
 * @作者： Mr.Shu
 * @创建时间：2017-05-12
 * @Copyright @2017 by Mr.Shu
 */
@Repository
public interface AdAttachmentsMapper {

  /**
   * 查询数据
   * @param dto
   * @return
   */
  public List<AdAttachmentVo> getDataAtachmentList(AdAtachmentDTO dto);

  /**
   * 保存数据
   * @param address
   * @param name
   * @param type
   * @return
   */
  public void addAdAttachment(@Param("address") String address, @Param("name") String name, @Param("type") String type,@Param("detail_content") String detail_type);

  /**
   * 编辑
   * @param id
   * @param name
   */
  public void editAttachment(@Param("id")Long id,@Param("name")String name,@Param("detail_content")String detail_content);

  /**
   * 删除
   * @param id
   */
  public void deleteAttachment(@Param("id")Long id);
}
