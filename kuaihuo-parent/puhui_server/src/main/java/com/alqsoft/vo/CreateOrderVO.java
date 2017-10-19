package com.alqsoft.vo;

import java.io.Serializable;
import java.util.List;

import com.alqsoft.entity.hunter.Hunter;

/**
 * 创建订单VO
 * @author cjd
 * @tiem 2017年3月9日下午3:13:33
 */
public class CreateOrderVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Hunter hunter;
	
	private List<ProVO> psList;
	
	private String message;
	
	private String hopeServiceDate;//期望送达时间
	
	private String predictServiceDate;//预计送达时间
	

	public String getHopeServiceDate() {
		return hopeServiceDate;
	}

	public void setHopeServiceDate(String hopeServiceDate) {
		this.hopeServiceDate = hopeServiceDate;
	}

	public String getPredictServiceDate() {
		return predictServiceDate;
	}

	public void setPredictServiceDate(String predictServiceDate) {
		this.predictServiceDate = predictServiceDate;
	}

	public Hunter getHunter() {
		return hunter;
	}

	public void setHunter(Hunter hunter) {
		this.hunter = hunter;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<ProVO> getPsList() {
		return psList;
	}

	public void setPsList(List<ProVO> psList) {
		this.psList = psList;
	}
	
	
}
