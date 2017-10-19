package com.ph.member.api.service;

import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.date.SyncDateUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelExportConfig;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.facade.member.vo.MemberPromotionVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MemberPromotionDataService {
	/**
	 *
	 * @Title: getMemberExcelBean
	 * @Description: 封装会员推广师列表导出数据
	* @param list
	 * @return ExcelBean    返回类型
	* @throws
	 */
	public ExcelBean getMemberAuthExcelBean(List<MemberPromotionVO> list) {
		List<Object[]> sheetData = ContainerUtil.aList();
		String name = "推广师列表数据";
		String[] tableHeads = { "会员ID","新业态账号","本地电商账号", "联系人", "手机号", "身份证号", "申请时间", "状态" };
		ExcelBean excelBean = ExcelUtil.getExcelHeadBean(tableHeads, name, sheetData);
		HashMap<Integer, ExcelBean> sheetsMap = new HashMap<Integer, ExcelBean>();
		Object[] content = null;
		int length = list.size();
		int olength = tableHeads.length;
		for (int j = 0; j < length; j++) {
			Object obj = list.get(j);
			MemberPromotionVO memberAuthVo = (MemberPromotionVO) obj;
			int k = 0;
			content = new Object[olength];
			content[k++] = memberAuthVo.getId();
			content[k++] = memberAuthVo.getAccountType().byteValue() == 1 ? memberAuthVo.getAccount() : "";
			content[k++] = memberAuthVo.getAccountType().byteValue() == 2 ? memberAuthVo.getAccount() : "";
			content[k++] = memberAuthVo.getMemberName();
			content[k++] = memberAuthVo.getTelPhone();
			content[k++] = memberAuthVo.getIdCardNo();
			content[k++] = SyncDateUtil.dateToString("yyyy-MM-dd HH:mm:ss", memberAuthVo.getCreateTime());
			Byte status = memberAuthVo.getStatus();
			String str = "";
			if (status == 1) {
				str = "待审核";
			} else if (status == 2) {
				str = "审核通过";
			} else if (status == 3) {
				str = "审核未通过";
			}
			content[k++] = str;
			sheetData.add(content);
			if ((j + 1) % ExcelExportConfig.MAX_SIZE == 0 || (j + 1) == length) {
				int sheetNum = j / ExcelExportConfig.MAX_SIZE;
				ExcelBean littleExcelBean = new ExcelBean();
				littleExcelBean.setNum(sheetNum);
				littleExcelBean.setName(name);
				littleExcelBean
						.setSheetName("推广师数据列表" + (sheetNum * ExcelExportConfig.MAX_SIZE + 1) + "-" + (j + 1) + "条");
				littleExcelBean.setHeaderCenter("推广师数据列表");
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
