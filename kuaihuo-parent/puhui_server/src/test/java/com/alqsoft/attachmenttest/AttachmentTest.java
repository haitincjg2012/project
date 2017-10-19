package com.alqsoft.attachmenttest;


import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.junit.Test;

public class AttachmentTest {
	
	public void upload(){
		System.out.println("上传图片");
		File file = new File("C:\\Users\\Public\\Pictures\\Sample Pictures\\1.jpg");
		PostMethod filePost = new PostMethod("http://127.0.0.1:8080//attachment//import-attachment");
		HttpClient client = new HttpClient();

		try {
			Part[] parts = new Part[1];
			parts[0] =  new FilePart("urlfile", file);
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			client.executeMethod(filePost);
			System.out.println("responseString:"+filePost.getResponseBodyAsString());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
	}
	
	@Test
	public void test(){
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(){
				@Override
				public void run(){
					upload();
				}
			};
			t.start();
			System.out.println("jajfd"+i+":"+t.getName());
		}
	}
}
