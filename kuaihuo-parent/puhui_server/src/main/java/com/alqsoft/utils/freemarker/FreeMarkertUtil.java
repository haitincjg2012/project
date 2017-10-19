package com.alqsoft.utils.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.alqsoft.utils.Constant;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @Title: FreeMarkertUtil.java
 * @Description: freemarke模板引擎 自动生成 代码
 * @author 陈振兵
 * @e-mail chenzhenbing@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月29日 下午5:43:31 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
public class FreeMarkertUtil {

	private Configuration cfg = null;
	
	public static void main(String[] args) throws Exception {
		FreeMarkertUtil maker = new FreeMarkertUtil();
		maker.init(); //初始化
		maker.processDao(); //生成dao类
		maker.processService(); //生成service类
		maker.processServiceImpl(); //生成实现类
	}
	
	/**
	 * 
	* @Title: init 
	* @Description: 创建Configuration实例,该实例负责管理FreeMarker的模板加载路径,负责生成模板实例
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@SuppressWarnings("deprecation")
	public void init() throws Exception {
		//初始化FreeMarker配置 
		cfg = new Configuration();
		// 设置FreeMarker的模版文件位置
		cfg.setDirectoryForTemplateLoading(new File(Constant.TEMPLATEBASEPATH));//指定模板文件从何处加载的数据源，这里设置成一个文件目录
	}

	/**
	 * 模板 + 数据模型 = 输出(合拼的结果)
	* @Title: processDao 
	* @Description: 模版引擎 生成dao 层
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void processDao() throws Exception {
		//填充数据模型,数据模型就是一个Map对象
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lower", "sms"); // 包名  小写
		map.put("bean", "ChatListLog"); // 实体类名大写 测试使用 如果正式使用可以遍历实体包，动态加入实体类名
		//使用Configuration实例加载指定模板
		Template template = cfg.getTemplate("dao.ftl",Constant.CHARSET); 
		File file = new File(Constant.DAOBASEURL + map.get("lower") + "/");//创建包名
		if (!file.exists()) // 如果目录不存在 则创建
		{
			file.mkdirs();
		}
		FileOutputStream outStream = new FileOutputStream(new File(Constant.DAOBASEURL + map.get("lower")
				+ "/" + map.get("bean") + "Dao.java"));
		OutputStreamWriter writer = new OutputStreamWriter(outStream,Constant.CHARSET);
		BufferedWriter sw = new BufferedWriter(writer);
		// 调用Template实例的process方法将模版和数据模型进行合并
		template.process(map, sw);
		sw.flush();
		sw.close();
		outStream.close();
	}

	/**
	 * 模板 + 数据模型 = 输出
	* @Title: processDao 
	* @Description: 模版引擎 生成service 层
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void processService() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lower", "sms"); // 包名  小写
		map.put("bean", "ChatListLog"); // 实体类名大写 测试使用 如果正式使用可以遍历实体包，动态加入实体类名
		// 使用Configuration实例加载指定模板
		Template template = cfg.getTemplate("Service.ftl",Constant.CHARSET);
		File file = new File(Constant.SERVICEBASEURL + map.get("lower") + "/");
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream outStream = new FileOutputStream(new File(Constant.SERVICEBASEURL
				+ map.get("lower") + "/" + map.get("bean") + "Service.java"));
		OutputStreamWriter writer = new OutputStreamWriter(outStream, Constant.CHARSET);
		BufferedWriter sw = new BufferedWriter(writer);
		// 调用Template实例的process方法将模版和数据模型进行合并
		template.process(map, sw);
		sw.flush();
		sw.close();
		outStream.close();
	}

	/**
	 * 模板 + 数据模型 = 输出
	* @Title: processDao 
	* @Description: 模版引擎 生成serviceImpl 层
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void processServiceImpl() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lower", "sms"); // 包名  小写
		map.put("bean", "ChatListLog"); // 实体类名大写 测试使用 如果正式使用可以遍历实体包，动态加入实体类名
		// 使用Configuration实例加载指定模板
		Template template = cfg.getTemplate("ServiceImpl.ftl",Constant.CHARSET);
		File file = new File(Constant.SERVICEIMPLBASEURL + map.get("lower") + "/");
		if (!file.exists()) {
			file.mkdirs();
		}
		FileOutputStream outStream = new FileOutputStream(new File(Constant.SERVICEIMPLBASEURL
				+ map.get("lower") + "/" + map.get("bean") + "ServiceImpl.java"));
		OutputStreamWriter writer = new OutputStreamWriter(outStream,Constant.CHARSET);
		BufferedWriter sw = new BufferedWriter(writer);
		// 调用Template实例的process方法将模版和数据模型进行合并
		template.process(map, sw);
		sw.flush();
		sw.close();
		outStream.close();
	}

	/*public static void analysisTemplate(String templatePath, String templateName, String fileName,
			Map<?, ?> root) {
		try {
			// 初使化FreeMarker配置
			Configuration config = new Configuration();
			// 设置要解析的模板所在的目录，并加载模板文件
			config.setDirectoryForTemplateLoading(new File(templatePath));
			// 设置包装器，并将对象包装为数据模型
//			ObjectWrapper objectWrapper=config.getObjectWrapper();
//			objectWrapper.wrap(map);
			config.setObjectWrapper(new DefaultObjectWrapper());

			// 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
			// 否则会出现乱码
			Template template = config.getTemplate(templateName, Constant.CHARSET);
			// 合并数据模型与模板
			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos, Constant.CHARSET);
			template.process(root, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}*/
}
