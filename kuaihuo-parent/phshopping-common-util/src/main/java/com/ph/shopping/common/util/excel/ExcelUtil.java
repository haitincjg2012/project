package com.ph.shopping.common.util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName:  ExcelUtil   
 * @Description:导出excle工具类
 * @author: 李杰
 * @date:   2017年4月27日 下午1:58:27     
 * @Copyright: 2017
 */
public class ExcelUtil {
	
	private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
	/**
	 * 构造私有
	 */
	private ExcelUtil() {}

	/**
	 * 不在服务器上保存文件， 写回xls文件给用户 导出操作
	 * 
	 * @param request
	 * @param response
	 * @param excelData
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request, HttpServletResponse response, ExcelBean excelData)
			throws Exception {
		Object[] datas = null;
		String fileName = null;
		if (null != excelData.getSheets() && !excelData.getSheets().isEmpty()) {
			datas = excelData.getSheets().values().toArray();
			Arrays.sort(datas);
		} else {
			datas = new Object[] { excelData };
		}
		HSSFWorkbook excel = ExcelUtil.createExcel(datas);
		// 生成文件
		HSSFSheet sheet = excel.getSheetAt(0);
		HSSFFooter footer = sheet.getFooter();
		footer.setRight("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
		response.setContentType("application/x-msdownload;charset=iso-8859-1");
		fileName = new Date().getTime() + "_" + new String(excelData.getName().trim().getBytes("GBK"), "iso-8859-1")
				+ ".xls";
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

		OutputStream sops = response.getOutputStream();// 不同类型的文件对应不同的MIME类型

		excel.write(sops);
		sops.flush();
		sops.close();
	}

	/**
	 * 创建excel
	 *
	 * @param datas
	 * @return
	 */
	public static HSSFWorkbook createExcel(Object[] datas) throws Exception {
		HSSFWorkbook excel = new HSSFWorkbook();
		/**
		 * 单元格样式
		 */
		HSSFCellStyle cellStyle = excel.createCellStyle();
		;
		HSSFCellStyle cellStyleColor = excel.createCellStyle();
		/**
		 * 字体
		 */
		Font font = excel.createFont();
		/**
		 * 文本格式
		 */
		HSSFDataFormat format = excel.createDataFormat();
		for (Object o : datas) {
			ExcelBean data = (ExcelBean) o;
			// 创建sheet
			HSSFSheet sheet = excel.createSheet(data.getSheetName());
			// 创建表头
			HSSFHeader header = sheet.getHeader();
			header.setCenter(data.getHeaderCenter());
			HSSFRow headerRow = sheet.createRow(0);

			// 设置单元格类型
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直布局：居中
			cellStyle.setWrapText(true);

			for (int i = 0; i < data.getTableHeader().length; i++) {
				HSSFCell headerCell = headerRow.createCell(i);
				headerCell.setCellStyle(cellStyle); // 设置单元格样式
				headerCell.setCellValue(data.getTableHeader()[i].trim());
			}

			// 创建数据
			int rowIndex = 1;
			for (Object[] sheetData : data.getSheetData()) {
				HSSFRow row = sheet.createRow(rowIndex);
				for (int i = 0; i < sheetData.length; i++) {
					// 创建第i个单元格
					HSSFCell cell = row.createCell(i);

					Short color = data.getFontColorMap().get(rowIndex + "_" + i);
					if (color != null) {// 有颜色的单元格
						cellStyleColor.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平布局：居中
						cellStyleColor.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直布局：居中
						cellStyleColor.setWrapText(true);
						font.setColor(color);
						cellStyleColor.setFont(font);
						/**
						 * 设置文本格式
						 */
						cellStyleColor.setDataFormat(format.getFormat("@"));

						cell.setCellStyle(cellStyleColor); // 设置单元格样式
					} else {
						/**
						 * 设置文本格式
						 */
						cellStyleColor.setDataFormat(format.getFormat("@"));

						cell.setCellStyle(cellStyle); // 设置单元格样式
					}
					if (sheetData[i] == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(sheetData[i] + "");
					}
				}
				rowIndex++;
			}

			// 合并单元格
			if (data.getColRow() != null) {
				for (int i = 0; i < data.getColRow().size(); i++) {
					Integer[] colr = data.getColRow().get(i);
					CellRangeAddress cra = new CellRangeAddress(colr[0], colr[1], colr[2], colr[3]);
					// 在sheet里增加合并单元格
					sheet.addMergedRegion(cra);
				}
			}
			autoSizeColumn(sheet, data);
		}

		return excel;
	}

	/**
	 * 列度自适应
	 * 
	 * @param sheet
	 */
	private static void autoSizeColumn(HSSFSheet sheet, ExcelBean data) throws Exception {
		if (data.getTableHeader() != null && data.getTableHeader().length > 0) {
			int length = data.getTableHeader().length - 1;
			Integer[] widths = data.getColWidth();
			if (widths == null) {
				while (length >= 0) {
					sheet.autoSizeColumn((short) length); // 调整列宽度
					--length;
				}
			} else {
				while (length >= 0) {
					sheet.setColumnWidth(length, widths[length]);
					--length;
				}
			}
		}

	}

	/**
	 * 控制层跳转错误页面(/500)
	 * 
	 * @param response
	 * @param request
	 * @param toPage
	 */
	public static void toErrorPage(HttpServletResponse response, HttpServletRequest request, String toPage) {
		try {
			response.sendRedirect(request.getContextPath() + toPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 批量设置单元格格式
	 * 
	 * @param sheet
	 *            表sheet对象
	 * @param cellstyle
	 *            设置的style
	 * @param startRow
	 *            开始的行号
	 * @param startCell
	 *            开始的列号
	 * @param endRow
	 *            结束的行号
	 * @param endCell
	 *            结束的列号 void
	 */
	public static void setCellStyle(Sheet sheet, CellStyle cellstyle, int startRow, int startCell, int endRow,
			int endCell) {
		for (int i = startRow; i <= endRow; i++) {
			Row row = sheet.getRow(i);
			for (int j = startCell; j <= endCell; j++) {
				Cell cell = row.getCell(j);
				cell.setCellStyle(cellstyle);
			}
		}
	}

	/**
	 * 创建一个Font
	 * 
	 * @param workbook
	 *            表空间
	 * @param fontName
	 *            字体
	 * @return Font
	 */
	public static Font createFont(Workbook workbook, String fontName) {
		Font font = workbook.createFont();
		if (fontName != null)
			font.setFontName(fontName);
		return font;
	}

	/**
	 * 创建一个Font
	 * 
	 * @param workbook
	 *            表空间
	 * @param fontSize
	 *            字体大小
	 * @return Font
	 */
	public static Font createFont(Workbook workbook, Short fontSize) {
		Font font = workbook.createFont();
		if (fontSize != null)
			font.setFontHeightInPoints(fontSize);
		return font;
	}

	/**
	 * 创建一个Font
	 * 
	 * @param workbook
	 *            表空间
	 * @param fontName
	 *            字体
	 * @param fontSize
	 *            字体大小
	 * @param color
	 *            字体颜色
	 * @return Font
	 */
	public static Font createFont(Workbook workbook, String fontName, Short fontSize, Short color) {
		Font font = workbook.createFont();
		if (fontName != null)
			font.setFontName(fontName);
		if (fontSize != null)
			font.setFontHeightInPoints(fontSize);
		if (color != null)
			font.setColor(color);
		return font;
	}

	/**
	 * 添加批注
	 * 
	 * @param workbook
	 * @param sheet
	 * @param cell
	 *            单元格
	 * @param text
	 *            批注 void
	 */
	public static void addComment(Workbook workbook, Sheet sheet, Cell cell, String text) {
		CreationHelper newFactory = workbook.getCreationHelper();
		RichTextString str = newFactory.createRichTextString(text);
		Drawing drawing = sheet.createDrawingPatriarch();
		ClientAnchor anchor = newFactory.createClientAnchor();
		anchor.setCol1(0);
		anchor.setCol2(3);
		anchor.setRow1(0);
		anchor.setRow2(6);
		Comment comment = drawing.createCellComment(anchor);
		comment.setString(str);
		cell.setCellComment(comment);
	}

	/**
	 * 返回col 及 width
	 * 
	 * @param num
	 * @return
	 */
	public static Integer[] getColWidth(int num) {
		Integer[] ints = new Integer[num];
		for (int i = 0; i < num; i++) {
			ints[i] = 50 * 110;
		}
		return ints;
	}
	
	/**
	 * 页面输出信息
	 * @param response
	 */
	public static void responseMsg(HttpServletResponse response, String msg) {
		try {
			PrintWriter pw = response.getWriter();
			pw.write(msg);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * 
	 * @Title: getExcelBean   
	 * @Description: 得到头部数据   
	 * @param: @param tableHeads
	 * @param: @param name
	 * @param: @param sheetData
	 * @param: @return      
	 * @return: ExcelBean
	 * @author：李杰      
	 * @throws
	 */
	public static ExcelBean getExcelHeadBean(String[] tableHeads, String name, List<Object[]> sheetData) {
		ExcelBean excelBean = new ExcelBean();
		// 文件名
		excelBean.setName(name);
		excelBean.setSheetName(name);
		excelBean.setHeaderCenter(name + "列表");
		excelBean.setTableHeader(tableHeads);
		excelBean.setColWidth(ExcelUtil.getColWidth(tableHeads.length));
		excelBean.setSheetData(sheetData);
		return excelBean;
	}
}
