package com.alqsoft.service.huntershow;

import org.alqframework.result.Result;

public interface HunterShowService {

	public Result getHunterShowList(Long id, Long member_id);

	public Result getSingleShowList(Long id);

	public Result getDetailShowList(Long id);

}
