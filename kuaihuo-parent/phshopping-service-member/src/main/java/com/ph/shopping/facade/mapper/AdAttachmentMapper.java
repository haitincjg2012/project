package com.ph.shopping.facade.mapper;

import com.ph.shopping.common.core.base.BaseMapper;
import com.ph.shopping.facade.member.entity.Member;
import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * @Description: TODO
 * @author wudi
 * @version v1.0
 * @create-time 2017年8月28日 下午5:33:19
 *
 */
@MyBatisRepository
public interface AdAttachmentMapper extends BaseMapper<Member>{

	public List<Map> getDataCarousel();

	public List<Map> getDataFirstIndustry();

	public List<Map> getDataSecondIndustry();

	public List<Map> getDataAttachmentPicture(@Param("longitude")String longitude,@Param("latitude")String latitude,@Param("page")Integer page,@Param("pagesize")Integer pagesize);

	public List<Map> getSecondById(@Param("id")Long id);

	public List<Map> getDataAttachmentPictureById(@Param("industry1")Long industry1,@Param("industry2")Long industry2,@Param("longitude")String longitude,@Param("latitude")String latitude,@Param("page")Integer page,@Param("pagesize")Integer pagesize);

	public List<Map> getAllFirst();

	public List<Map> getAllSecond(@Param("parentId")Long parentId);

	public List<Map> findData(@Param("datas")String datas,@Param("page")Integer page,@Param("pagesize")Integer pagesize,@Param("longitude")String longitude,@Param("latitude")String latitude);

	public List<Map> getIndustryNameById(@Param("id") Long id);

	public Map getCItyProvince(@Param("province") String province);

	public Map  getCItyProvinceAndCity(@Param("province") String province,@Param("city") String city);

	public List<Map> getCItyProvinceList(@Param("provinceId") String provinceId,@Param("isRecommendType") Integer isRecommendType,@Param("page")Integer page,@Param("pagesize")Integer pagesize,@Param("longitude")String longitude,@Param("latitude")String latitude);

	public List<Map> getCItyProvinceListIsNull(@Param("provinceId") String provinceId,@Param("isRecommendType") Integer isRecommendType,@Param("page")Integer page,@Param("pagesize")Integer pagesize,@Param("longitude")String longitude,@Param("latitude")String latitude);

	public List<Map> getCItyANdProvinceListById(@Param("provinceId") String provinceId,@Param("cityId") String cityId,@Param("isRecommendType") Integer isRecommendType,@Param("page")Integer page,@Param("pagesize")Integer pagesize,@Param("longitude")String longitude,@Param("latitude")String latitude);

	public List<Map> getCItyANdProvinceListByIdIsNull(@Param("provinceId") String provinceId,@Param("cityId") String cityId,@Param("isRecommendType") Integer isRecommendType,@Param("page")Integer page,@Param("pagesize")Integer pagesize,@Param("longitude")String longitude,@Param("latitude")String latitude);

    public List<Map>  getIndustryNameByIndustryId(@Param("id")Long id,@Param("industry1")Long industry1,@Param("industry2")Long industry2);

	/**
	 * 手机版本号
	 * @return
	 */
	public List<Map> getAppVersion(@Param("type") Integer type, @Param("client_type") String client_type);

}
