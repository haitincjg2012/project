package com.ph.shopping.facade.score.request;

import java.io.Serializable;

/**
 * @项目：phshopping-facade-score
 * @描述：商城个人积分流水分页查询参数
 * @作者： Mr.zheng
 * @创建时间：2017-03-31
 * @Copyright @2017 by Mr.zheng
 */
public class ScorePageDTO implements Serializable{

    private static final long serialVersionUID = -2686618036721965930L;

    /**
     * 会员id
     */
    private Long memberId;
    /**
     * 页码
     */
    private Integer pageNum;
    /**
     * 每页条数
     */
    private Integer pageSize;
    
    
    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
    
}
