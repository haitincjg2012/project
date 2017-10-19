package com.ph.shopping.common.util.excel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
/**
 * 
 * @ClassName:  ExcelBean   
 * @Description:excel数据封装对象
 * @author: 李杰
 * @date:   2017年4月27日 下午1:56:44     
 * @Copyright: 2017
 */
public class ExcelBean implements Comparable<ExcelBean> ,Serializable{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = 4239102851746458603L;
	/**
	 *  当前sheet的编号,唯一
	 */
	private int num = 0;
	/**
	 * excel的名字
	 */
	private String name = "测试excel";
	/**
	 * 当前sheet的名称
	 */
	private String sheetName = "sheet名称";
	/**
	 * headerCenter
	 */
	private String headerCenter = "测试headerCenter";
	/**
	 * 当前sheet的表头
	 */
	private String[] tableHeader = new String[] { "测试数据" };
	/**
	 * 当前sheet的数据
	 */
	private List<Object[]> sheetData;
	/**
	 * 多个sheet情况下使用，如果只有一个sheet，那么请输入为null
	 */
	private HashMap<Integer, ExcelBean> sheets = null;
	/**
	 *  合并单元格的行和列{起始行，列，结束行，列}
	 */
	private List<Integer[]> colRow;
	/**
	 * 填充数据起始行
	 */
	private Integer startRow;
	/**
	 * {列宽(50*100),列宽，列宽..}
	 */
	private Integer[] colWidth;
	/**
	 * 列,宽度
	 */
	private List<short[]> widthList;
	/**
	 * 字体颜色地图，key： 行_列，value color
	 */
	private HashMap<String,Short> fontColorMap = new HashMap<String,Short>();

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String[] getTableHeader() {
		return tableHeader;
	}

	public void setTableHeader(String[] tableHeader) {
		this.tableHeader = tableHeader;
	}

	public HashMap<Integer, ExcelBean> getSheets() {
		return sheets;
	}

	public void setSheets(HashMap<Integer, ExcelBean> sheets) {
		this.sheets = sheets;
	}

	public List<Object[]> getSheetData() {
		return sheetData;
	}

	public void setSheetData(List<Object[]> sheetData) {
		this.sheetData = sheetData;
	}

	@Override
	public int compareTo(ExcelBean o) {
		return num - o.num;
	}

	public String getHeaderCenter() {
		return headerCenter;
	}

	public void setHeaderCenter(String headerCenter) {
		this.headerCenter = headerCenter;
	}

	public List<Integer[]> getColRow() {
		return colRow;
	}

	public void setColRow(List<Integer[]> colRow) {
		this.colRow = colRow;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public List<short[]> getWidthList() {
		return widthList;
	}

	public void setWidthList(List<short[]> widthList) {
		this.widthList = widthList;
	}

	/** {列宽(50*100),列宽，列宽..}
	 * @return
	 */
	public Integer[] getColWidth() {
		return colWidth;
	}

	/** {列宽(50*100),列宽，列宽..}
	 * @param colWidth
	 */
	public void setColWidth(Integer[] colWidth) {
		this.colWidth = colWidth;
	}

	public HashMap<String, Short> getFontColorMap() {
		return fontColorMap;
	}

	public void setFontColorMap(HashMap<String, Short> fontColorMap) {
		this.fontColorMap = fontColorMap;
	}

}

