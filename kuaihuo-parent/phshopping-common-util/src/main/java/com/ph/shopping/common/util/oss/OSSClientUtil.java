package com.ph.shopping.common.util.oss;

import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * @oss上传
 * @author zhang
 *
 */
@Component
public class OSSClientUtil {
	public static Log log = LogFactory.getLog(OSSClientUtil.class);
	
	private static OSSClient client = null;
	private static final String ACCESS_ID = "e6wt7YMUZEru6TOD";
	private static final String ACCESS_KEY = "QLhDKpV0Rez9fsH6oIOXbmks4sd7WE";
	private static final String OSS_ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";//oss外网正式上传地址
//	private static final String OSS_ENDPOINT = "http://oss-cn-shenzhen-internal.aliyuncs.com";//oss内网上传地址
	public static  String BASE_URL = "https://ph-images.oss-cn-shenzhen.aliyuncs.com/";// 正式基础访问路径
	public static  String BUCKET_NAME = "ph-images";// 正式bucket名
	/**
	 * 测试环境地址
	 */
/*	private static final String OSS_ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";//oss外网测试上传地址
	public static  String BASE_URL = "https://ph-images-test.oss-cn-shenzhen.aliyuncs.com/";// 测试基础访问路径
	public static  String BUCKET_NAME = "ph-images-test";// 测试bucket名*/

/*	static{
		BUCKET_NAME = PropertiesUtil.getValue("bucketName");
		BASE_URL = PropertiesUtil.getValue("bucketUrl");
	}
	*/
	
	/**
	 * @上传文件
	 * @param key(模块名+YYYYMMDD+图片名)
	 * @param filePath
	 * @throws FileNotFoundException
	 * @author Mr.Chang
	 * @since 2017年3月26日
	 */
	public String putObject(String url, MultipartFile file) {
		if(client==null){
			client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);
			// 设置bucket的访问权限，public-read-write权限
			client.setBucketAcl(BUCKET_NAME, CannedAccessControlList.PublicRead);
		}
		return up(client,url, file);

	}

	/**
	 * multipartFile上传文件
	 * @param client
	 * @param url
	 * @param file
	 * @return
	 * @author Mr.Chang
	 */
	public String up(OSSClient client, String url, MultipartFile file) {
		try {
			// 初始化OSSClient
			if(client==null){
				client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);
				// 设置bucket的访问权限，public-read-write权限
				client.setBucketAcl(BUCKET_NAME, CannedAccessControlList.PublicRead);
			}
			// 获取指定文件的输入流
			InputStream content = file.getInputStream();

			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();

			// 必须设置ContentLength
			meta.setContentLength(file.getSize());

			// 上传Object.
			client.putObject(BUCKET_NAME,url, content, meta);
			// 打印ETag
			return BASE_URL  + url;
		} catch (Exception e) {
			log.error("系统错误:上传OSS图片错误" + e);
			return null;
		}
	}
	
	/**
	 * 输入流上传文件
	 * @param client
	 * @param url
	 * @param in
	 * @return
	 * @author Mr.Chang
	 */
	public String up(OSSClient client,String url, InputStream in) {
		try {
			if(client==null){
				client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);
				// 设置bucket的访问权限，public-read-write权限
				client.setBucketAcl(BUCKET_NAME, CannedAccessControlList.PublicRead);
			}

			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();

			// 必须设置ContentLength
			meta.setContentLength(in.available());

			// 上传Object.
			client.putObject(BUCKET_NAME,url, in, meta);
			// 打印ETag
			return BASE_URL  + url;
		} catch (Exception e) {
			log.error("系统错误:上传OSS图片错误" + e);
			return null;
		}
	}
	
	/**
	 * @删除oss的图片
	 * @param key
	 * @author Mr.Chang
	 * @since 2017年3月26日
	 */
	public static void deleteObject(String key){
		if(client==null){
			client = new OSSClient(OSS_ENDPOINT, ACCESS_ID, ACCESS_KEY);
			// 设置bucket的访问权限，public-read-write权限
			client.setBucketAcl(BUCKET_NAME, CannedAccessControlList.PublicReadWrite);
		}
		client.deleteObject(BUCKET_NAME, key);
	}
	
	public static void main(String[] args) {
	/*	String str="http://image.jsbn.com/WebImage/cq/jpg/20150821/44980352111163502374/20150821191413486574_800x1200.jpg";
		str.replace(str.substring(0, str.lastIndexOf("/")), "http://img2.jsbn.com/venus/pringles/20160307");
		System.out.println(str);*/
		/*OSSClientUtil ot=new OSSClientUtil();
		File f=new File("D:\\test.jpg");
		InputStream is = null;
		try {
			is = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		ot.up(client, "agent/test.jpg",is);*/
	}
}
