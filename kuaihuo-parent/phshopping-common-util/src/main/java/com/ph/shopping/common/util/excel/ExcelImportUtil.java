package com.ph.shopping.common.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ph.shopping.common.util.container.ClassUtil;
import com.ph.shopping.common.util.date.SyncDateUtil;
/**
 * 
* @ClassName: ExcelImportUtil  
* @Description: excel 导入工具类  
* @author lijie  
* @date 2017年3月22日  
*
 */
public class ExcelImportUtil {

	private static final DecimalFormat nf = new DecimalFormat("0");// 格式化数字

	private ExcelImportUtil(){}
	/**
	 * @describe: cls 为需要转换的对象、names 为该对象与excel表格对应的字段名
	 * @param cls
	 * @param names 备注：此处的filePath 用于校验文件格式用途
	 * @return 返回对应的实体类
	 * @throws Exception
	 */
	public static <T> List<T> readExcel(Class<T> cls, String[] names, InputStream in, String filePath)
			throws Exception {
		if (names == null || names.length == 0) {
			throw new RuntimeException("参数 names 不能为空 必须为excel 表格对应字段必须为映射的实体类的字段名");
		}
		if (isExcelXls(filePath)) {
			return result(cls, readXls(in), names);
		} else if (isExcelxlsx(filePath)) {
			return result(cls, readXlsx(in), names);
		} else {
			throw new IOException("暂不支持该文件类型");
		}
	}

	/**
	 * 
	 * @Title: isExcelXls @Description: @param @param filePath @param @return
	 * 参数 @return boolean 返回类型 @throws
	 */
	private static boolean isExcelXls(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	/**
	 * 判断是否是xlsx版excel
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 结果
	 */
	private static boolean isExcelxlsx(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}

	/**
	 * 读取xls 格式的excel 文件
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private static List<List<Object>> readXls(InputStream in) throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		HSSFWorkbook hwb = new HSSFWorkbook(in);
		int sheetNum = hwb.getNumberOfSheets();
		for (int i = 0; i < sheetNum; i++) {
			List<List<Object>> retList = getValueBySheets(hwb.getSheetAt(i));
			if (retList == null || retList.isEmpty())
				continue;
			list.addAll(retList);
		}
		return list;
	}

	/**
	 * 
	* @Title: getValueBySheets  
	* @Description:读取xls 每个sheet 内容
	* @param @param sheet
	* @param @return    参数  
	* @return List<List<Object>>    返回类型  
	* @throws
	 */
	private static List<List<Object>> getValueBySheets(HSSFSheet sheet) {
		HSSFRow row;
		int counter = 0;
		List<List<Object>> list = new LinkedList<List<Object>>();
		for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				counter++;
			}
			if (counter == 1) {
				continue;
			}
			List<Object> linked = getCellValues(row);
			if (linked == null || linked.isEmpty())
				continue;
			list.add(linked);
		}
		return list;
	}
	/**
	 * 
	* @Title: getCellValues  
	* @Description: 读取xls 每个Cell 内容
	* @param @param row
	* @param @return    参数  
	* @return List<Object>    返回类型  
	* @throws
	 */
	private static List<Object> getCellValues(HSSFRow row) {
		List<Object> linked = new LinkedList<Object>();
		HSSFCell cell;
		for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
			cell = row.getCell(j);
			if (cell == null) {
				if (j != row.getLastCellNum())
					linked.add(null);
				continue;
			}
			Object obj = getValue(cell.getCellType(), cell);
			linked.add(obj);
		}
		return linked;
	}
	/**
	 * 
	* @Title: readXlsx  
	* @Description:读取Xlsx 所有sheet 内容
	* @param @param in
	* @param @return
	* @param @throws IOException    参数  
	* @return List<List<Object>>    返回类型  
	* @throws
	 */
	private static List<List<Object>> readXlsx(InputStream in) throws IOException {
		List<List<Object>> list = new LinkedList<List<Object>>();
		XSSFWorkbook xwb = new XSSFWorkbook(in);
		int sheetNum = xwb.getNumberOfSheets();
		for (int i = 0; i < sheetNum; i++) {
			List<List<Object>> retList = getValueByXSheets(xwb.getSheetAt(i));
			if (retList == null || retList.isEmpty())
				continue;
			list.addAll(retList);
		}
		return list;
	}
	/**
	 * 
	* @Title: getValueByXSheets  
	* @Description: 读取每个sheet 内容  
	* @param @param sheet
	* @param @return    参数  
	* @return List<List<Object>>    返回类型  
	* @throws
	 */
	private static List<List<Object>> getValueByXSheets(XSSFSheet sheet) {
		List<List<Object>> retlist = new LinkedList<List<Object>>();
		XSSFRow row = null;
		int counter = 0;
		for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				counter++;
			}
			if (counter == 1)
				continue;
			List<Object> list = getCellValuesByXHSSFRow(row);
			if (list == null || list.isEmpty())
				continue;
			retlist.add(list);
		}
		return retlist;
	}
	/**
	 * 
	* @Title: getCellValuesByXHSSFRow  
	* @Description: 读取 XLSX 没cell 的数据
	* @param @param row
	* @param @return    参数  
	* @return List<Object>    返回类型  
	* @throws
	 */
	private static List<Object> getCellValuesByXHSSFRow(XSSFRow row) {
		List<Object> linked = new LinkedList<Object>();
		XSSFCell cell = null;
		for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
			cell = row.getCell(j);
			if (cell == null) {
				if (j != row.getLastCellNum())
					linked.add(null);
				continue;
			}
			Object obj = getValue(cell.getCellType(), cell);
			linked.add(obj);
		}
		return linked;
	}
	/**
	 * 获取xlsx格式的文本内容
	 * 
	 * @param cellType
	 * @param cell
	 * @return
	 */
	private static Object getValue(int cellType, XSSFCell cell) {
		Object value = null;
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			value = nf.format(cell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		default:
			value = cell.toString();
		}
		return value;
	}

	/**
	 * 获取xls格式的文本内容
	 * 
	 * @param cellType
	 * @param cell
	 * @return
	 */
	private static Object getValue(int cellType, HSSFCell cell) {
		Object value = null;
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case XSSFCell.CELL_TYPE_NUMERIC:
			value = nf.format(cell.getNumericCellValue());
			break;
		case XSSFCell.CELL_TYPE_BOOLEAN:
			value = cell.getBooleanCellValue();
			break;
		case XSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		default:
			value = cell.toString();
		}
		return value;
	}

	/**
	 * @Describe 根据Class对象返回实体类 参数list 必须是 读取的 数据与实体字段 以map 的形式 映射
	 *           注意：excel表格的数据必须与实体类的字段一一对应
	 * @param cls
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private static <T> List<T> queryEntity(Class<T> cls, List<Map> list) throws Exception {
		if (list == null || list.isEmpty())
			return null;
		Map retMap = type(cls);
		if (retMap == null || retMap.isEmpty())
			return null;
		List<T> result = new LinkedList<T>();
		for (Map<?, ?> m : list) {
			T invokeTester = cls.newInstance();
			if (m == null || m.size() == 0)
				continue;
			for (Map.Entry<?, ?> mm : m.entrySet()) {
				if (mm == null)
					continue;
				Object[] objs = (Object[]) retMap.get(mm.getKey());
				if (objs == null) {
					continue;
				}
				String methodName = objs[0].toString();
				Class<?> clas = (Class) objs[1];
				Object value = convertType(clas, mm.getValue());
				Method addMethod = cls.getDeclaredMethod(methodName, new Class[] { clas });
				addMethod.invoke(invokeTester, new Object[] { value });
			}
			result.add(invokeTester);
		}
		return result;
	}
	/**
	 * 
	* @Title: convertType  
	* @Description:值类型转换 
	* @param @param type
	* @param @param value
	* @param @return    参数  
	* @return Object    返回类型  
	* @throws
	 */
	private static Object convertType(Type type, Object value) {
		if (value == null) {
			return value;
		}
		String retValue = value.toString();
		if (type == Integer.class) {
			value = Integer.valueOf(retValue);
		} else if (type == Byte.class) {
			value = Byte.valueOf(retValue);
		} else if (type == Short.class) {
			value = Short.valueOf(retValue);
		} else if (type == Date.class) {
			value = SyncDateUtil.strToDate(retValue);
		} else if (type == Long.class) {
			value = Long.valueOf(retValue);
		} else if (type == Double.class) {
			value = Double.valueOf(retValue);
		} else if (type == Float.class) {
			value = Float.valueOf(retValue);
		}
		return value;
	}
	/**
	 * 
	* @Title: type  
	* @Description: 得到字段类型 
	* @param @param cls
	* @param @return
	* @param @throws ClassNotFoundException    参数  
	* @return Map    返回类型  
	* @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map type(Class<?> cls) throws ClassNotFoundException {
		if (cls == null)
			throw new ClassNotFoundException();
		Map map = new HashMap<>();
		String[] fileds = ClassUtil.getFiledStrByClassBySuper(cls);
		Method[] mh = cls.getDeclaredMethods();
		if (mh != null && mh.length > 0) {
			for (Method m : mh) {
				String name = m.getName();
				if (name.indexOf("set") != -1) {
					Class<?>[] parType = m.getParameterTypes();
					String retName = name.substring("set".length());
					map.put(getFieldByArray(fileds, retName), new Object[] { name, parType[0] });
				}
			}
		}
		return map;
	}
	/**
	 * 
	* @Title: getFieldByArray  
	* @Description: TODO(这里用一句话描述这个方法的作用)  
	* @param @param fields
	* @param @param field
	* @param @return    参数  
	* @return String    返回类型  
	* @throws
	 */
	private static String getFieldByArray(String[] fields, String field) {
		if (fields != null) {
			for (String str : fields) {
				if (str.equalsIgnoreCase(field)) {
					field = str;
					break;
				}
			}
		}
		return field;
	}
    /**
     * 
     * @Title: result   
     * @Description: 返回导入的实体集合   
     * @param: @param cls
     * @param: @param list
     * @param: @param names
     * @param: @return
     * @param: @throws Exception      
     * @return: List<T>      
     * @throws
     */
	@SuppressWarnings("rawtypes")
	private static <T> List<T> result(Class<T> cls, List<List<Object>> list, String[] names) throws Exception {
		if (list == null || list.isEmpty())
			return null;
		ArrayList<Map> result = new ArrayList<>();
		int num = list.size();
		if (num > 10) {
			result.ensureCapacity(num);
		}
		Iterator<?> it = list.iterator();
		while (it.hasNext()) {
			List<?> obj = (List<?>) it.next();
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator<?> objit = obj.iterator();
			for (String name : names) {
				map.put(name, objit.next());
			}
			result.add(map);
		}
		return queryEntity(cls, result);
	}

}
