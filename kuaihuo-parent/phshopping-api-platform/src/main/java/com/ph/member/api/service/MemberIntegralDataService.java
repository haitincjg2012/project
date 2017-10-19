package com.ph.member.api.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.date.SyncDateUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelExportConfig;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.facade.member.dto.MemberIntegralDTO;

@Service
public class MemberIntegralDataService {

	/**
	 * 
	* @Title: getMemberIntegralExcelBean  
	* @Description: 导出会员积分数据列表  
	* @param @param list
	* @param @return    参数  
	* @return ExcelBean    返回类型  
	* @throws
	 */
	public ExcelBean getMemberIntegralExcelBean(List<MemberIntegralDTO> list) {
		if (!ParamVerifyUtil.objIsNotNull(list)) {
			return null;
		}
		List<Object[]> sheetData = ContainerUtil.aList();
		String name = "会员积分列表数据";
		String[] tableHeads = { "会员ID", "会员名称", "来源", "订单号", "时间", "积分" ,"备注"};
		ExcelBean excelBean = ExcelUtil.getExcelHeadBean(tableHeads, name, sheetData);
		HashMap<Integer, ExcelBean> sheetsMap = new HashMap<Integer, ExcelBean>();
		Object[] content = null;
		int length = list.size();
		int olength = tableHeads.length;
		for (int j = 0; j < length; j++) {
			MemberIntegralDTO dto = list.get(j);
			int k = 0;
			content = new Object[olength];
			content[k++] = dto.getMemberId();
			content[k++] = dto.getMemberName();
			content[k++] = dto.getRemark();
			content[k++] = dto.getOrderNo();
			content[k++] = SyncDateUtil.dateToString("yyyy-MM-dd HH:mm:ss", dto.getCreateTime());
			content[k++] = dto.getScore();
			content[k++] = dto.getRemark();
			
			sheetData.add(content);
			if ((j + 1) % ExcelExportConfig.MAX_SIZE == 0 || (j + 1) == length) {
				int sheetNum = j / ExcelExportConfig.MAX_SIZE;
				ExcelBean littleExcelBean = new ExcelBean();
				littleExcelBean.setNum(sheetNum);
				littleExcelBean.setName(name);
				littleExcelBean
						.setSheetName("会员积分数据列表" + (sheetNum * ExcelExportConfig.MAX_SIZE + 1) + "-" + (j + 1) + "条");
				littleExcelBean.setHeaderCenter("会员积分数据列表");
				littleExcelBean.setTableHeader(tableHeads);
				littleExcelBean.setColWidth(ExcelUtil.getColWidth(tableHeads.length));
				littleExcelBean.setSheetData(sheetData);
				sheetsMap.put(sheetNum, littleExcelBean);
				sheetData = ContainerUtil.aList();
			}
		}
		excelBean.setSheets(sheetsMap);
		return excelBean;
	}
}
