package com.alqsoft.mybatis.dao.subject;

import org.alqframework.orm.mybatis.MyBatisRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@MyBatisRepository
@Component("mybatisSubjectDao")
public interface SubjectDao {

	List<Map<String,Object>> getSubjectList(@Param("page") int page, @Param("rows")int rows);

	int delete(Long id);

    Map<String,Object> detail(Long id);
}
