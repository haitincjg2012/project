package com.alqsoft.controller.ueditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alqsoft.baidu.ueditor.ActionEnter;

/**
 * 
 * @Title: UeditorTest.java
 * @Description: TODO
 * @author 俞旺
 * @e-mail 13606024548@139.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年5月12日 下午2:20:56 Copyright © 2013 厦门卓讯信息技术有限公司 All rights
 *              reserved.
 * 
 */
@RequestMapping("editor")
@Controller
public class UeditorTest {

	private static String path = "upload/temp";
	private static String rootPath = "http://localhost:8080/alqsoft-easyui/ueditor";

	@RequestMapping("ueditor")
	public String ueditor(Model model) {
		return "editor/editor";
	}
	
	@RequestMapping("config")
	@ResponseBody
	public void config(HttpServletRequest request,HttpServletResponse response)
	{
		PrintWriter write=null;
		String str = null;
		try {
			request.setCharacterEncoding("utf-8");
			response.setHeader("Content-Type" , "text/html");
			str= new ActionEnter( request, rootPath+"/jsp" ).exec();
			write = response.getWriter();
			write.write(str);
			write.flush();
			write.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			write.write(str);
			write.flush();
			write.close();
		}
		
	}
	
	@RequestMapping("upload")
	@ResponseBody
	public String upload(@RequestParam("upfile") MultipartFile upfile, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String result = "";
		if (path == null) {
			System.out.println(">> the get file path error!");
			return result;
		}
		FileOutputStream out = null;
		try {
			// 存放路径，如果不存在则创建路径
			File file = new File(rootPath);
			if (!file.exists())
				file.mkdirs();
			// 随机生成文件名，oname是原始文件名称
			String fname = "123.jpg", oname = upfile.getOriginalFilename();
			out = new FileOutputStream(rootPath + "/" + fname);
			out.write(upfile.getBytes());
			out.close();
			// {"name":"10571402926855858.jpg", "originalName": "china.jpg",
			// "size": 43086, "state": "SUCCESS", "type": ".jpg", "url":
			// "upload/20140616/10571402926855858.jpg"}
			// result返回的url参照IndexController._file方法
			result = "{\"name\":\"" + fname + "\", \"originalName\": \"" + oname + "\", \"size\": "
					+ upfile.getSize() + ", \"state\": \"SUCCESS\", \"type\": \"" + ".jpg"
					+ "\", \"url\": \"" + path + "_" + fname + "\"}";
			result = result.replaceAll("\\\\", "\\\\");
		} catch (Exception e) {
		} finally {
			out.close();
		}

		return result;
	}

	@RequestMapping("show")
	@ResponseBody
	public void show(HttpServletRequest request,HttpServletResponse response) {
		// 这个path就是上面result的url
		// 因为我是多层目录所以就使用了_下划线代替/
		// 主要就是注意路径要对应一致就行了
		path = path.replaceAll("_", "/");
		FileInputStream in = null;
		OutputStream out = null;
		try {
			File file = new File(rootPath + path);
			in = new FileInputStream(file);
			byte[] b = new byte[(int) file.length()];
			in.read(b);
			in.close();
			response.setContentType("image/*");
			out = response.getOutputStream();
			out.write(b);
			out.flush();
			out.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
