package com.ph.member.api.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.container.ParamVerifyUtil;
import com.ph.shopping.common.util.date.SyncDateUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelExportConfig;
import com.ph.shopping.common.util.excel.ExcelImportUtil;
import com.ph.shopping.common.util.excel.ExcelUtil;
import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.facade.member.entity.Member;

@Service
public class MemberDataService {

	private static final Logger log = LoggerFactory.getLogger(MemberDataService.class);
	
	/**
	 * 
	* @Title: getMemberExcelBean  
	* @Description: 封装会员列表导出数据  
	* @param @param list
	* @param @return    参数  
	* @return ExcelBean    返回类型  
	* @throws
	 */
	public ExcelBean getMemberExcelBean(List<Member> list) {
		if (!ParamVerifyUtil.objIsNotNull(list)) {
			return null;
		}
		List<Object[]> sheetData = ContainerUtil.aList();
		String name = "会员列表数据";
		String[] tableHeads = { "会员ID", "头像地址", "联系人", "联系电话", "身份证号", "注册时间", "实名认证", "是否是推广师", "状态", "会员等级" };
		ExcelBean excelBean = ExcelUtil.getExcelHeadBean(tableHeads, name, sheetData);
		HashMap<Integer, ExcelBean> sheetsMap = new HashMap<Integer, ExcelBean>();
		Object[] content = null;
		int length = list.size();
		int olength = tableHeads.length;
		for (int j = 0; j < length; j++) {
			Member member = list.get(j);
			int k = 0;
			content = new Object[olength];
			content[k++] = member.getId();
			content[k++] = member.getHeadImage();
			content[k++] = member.getMemberName();
			content[k++] = member.getTelPhone();
			content[k++] = member.getIdCardNo();
			content[k++] = SyncDateUtil.dateToString("yyyy-MM-dd HH:mm:ss", member.getCreateTime());

			Byte certification = member.getCertification();
			String isCertification = "";
			if (certification != null) {
				if (certification == 1) {
					isCertification = "未认证";
				} else if (certification == 2) {
					isCertification = "已认证";
				} else if (certification == 3) {
					isCertification = "待审核";
				}
			}
			content[k++] = isCertification;

			Byte isMarketing = member.getIsMarketing();
			String marketing = "";
			if (isMarketing != null) {
				if (isMarketing == 1) {
					marketing = "是";
				} else if (isMarketing == 2) {
					marketing = "否";
				}
			}
			content[k++] = marketing;

			Byte status = member.getStatus();
			String state = "";
			if (status != null) {
				if (status == 1) {
					state = "删除";
				} else if (status == 2) {
					state = "正常";
				} else if (status == 3) {
					state = "冻结";
				}
			}
			content[k++] = state;
			String lv = "";
			Byte level = member.getLevel();
			if (level != null) {
				if (level == 1) {
					lv = "普通会员";
				} else if (level == 2) {
					lv = "VIP";
				}
			}
			content[k++] = lv;
			sheetData.add(content);
			if ((j + 1) % ExcelExportConfig.MAX_SIZE == 0 || (j + 1) == length) {
				int sheetNum = j / ExcelExportConfig.MAX_SIZE;
				ExcelBean littleExcelBean = new ExcelBean();
				littleExcelBean.setNum(sheetNum);
				littleExcelBean.setName(name);
				littleExcelBean
						.setSheetName("会员数据列表" + (sheetNum * ExcelExportConfig.MAX_SIZE + 1) + "-" + (j + 1) + "条");
				littleExcelBean.setHeaderCenter("会员数据列表");
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

	/**
	 * @throws Exception 
	 * @throws IOException 
	 * 
	* @Title: getExcelImportInfos  
	* @Description: 得到导入的exel 数据  
	* @param @param clas
	* @param @param request
	* @param @return    参数  
	* @return T    返回类型  
	* @throws
	 */
	public <T> List<T> getExcelImportInfos(Class<T> clas, String[] names, HttpServletRequest request) throws Exception {
		List<T> t = null;
		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> multipartFiles = null;
		try {
			multipartFiles = multiRequest.getFileMap();
		} catch (Exception e) {
			log.error("加载上传对象失败", e);
		}
		if (multipartFiles != null) {
			for (Map.Entry<String, MultipartFile> entry : multipartFiles.entrySet()) {
				MultipartFile mfile = entry.getValue();
				InputStream in = mfile.getInputStream();
				String filePath = mfile.getOriginalFilename();
				t = ExcelImportUtil.readExcel(clas, names, in, filePath);
			}
		}
		return t;
	}
	/**
	 * 
	* @Title: verifyPwd  
	* @Description: 验证密码是否相等  
	* @param @param target
	* @param @param localwd
	* @param @return    参数  
	* @return boolean    返回类型  
	* @throws
	 */
	public boolean verifyPwd(String target, String localwd) {
		boolean flag = false;
		if (StringUtils.isNotBlank(target)) {
			String md5pwd = pwdMd5Str(target);
			if (md5pwd != null) {
				flag = md5pwd.equals(localwd);
			}
		}
		return flag;
	}
	/**
	 * 
	* @Title: pwdMd5Str  
	* @Description:密码加密
	* @param @param pwd
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	private String pwdMd5Str(String pwd) {

		return MD5.getMD5Str(pwd);
	}
}
