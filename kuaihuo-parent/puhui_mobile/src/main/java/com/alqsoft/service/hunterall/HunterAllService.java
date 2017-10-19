package com.alqsoft.service.hunterall;

import com.alqsoft.entity.member.Member;
import org.alqframework.result.Result;

public interface HunterAllService {
	
	public Result getHunterAllList();

	public Result getHunterAllList2();
	
    public Result getAllHunter(String longitude,String latitude,Integer currentPage,Integer numPage);


    public Result getAllHunterByArea(String phone,Integer currentPage, Integer numPage);
}
