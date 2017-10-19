package com.alqsoft.utils.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 该示例代码展示了如果在OSS中创建和删除一个Bucket，以及如何上传和下载一个文件。
 * 
 * 该示例代码的执行过程是：
 * 1. 创建一个Bucket（如果已经存在，则忽略错误码）；
 * 2. 上传一个文件到OSS；
 * 3. 下载这个文件到本地；
 * 4. 清理测试资源：删除Bucket及其中的所有Objects。
 * 
 * 尝试运行这段示例代码时需要注意：
 * 1. 为了展示在删除Bucket时除了需要删除其中的Objects,
 *    示例代码最后为删除掉指定的Bucket，因为不要使用您的已经有资源的Bucket进行测试！
 * 2. 请使用您的API授权密钥填充ACCESS_ID和ACCESS_KEY常量；
 * 3. 需要准确上传用的测试文件，并修改常量uploadFilePath为测试文件的路径；
 *    修改常量downloadFilePath为下载文件的路径。
 * 4. 该程序仅为示例代码，仅供参考，并不能保证足够健壮。
 * 
 * @Discription 阿里云开发存储 oss工具类
 * @Email 540970036@qq.com
 * @Project zte-main
 * @Author 陈振兵
 * @Date 2014-11-1 下午3:03:33
 */
public class OSSObjectSample {
	
	private static Logger log = LoggerFactory.getLogger(OSSObjectSample.class);
	
    //太古粮家
   /* private static final String ACCESS_ID = "G9Ghxml888FNt3Bk";
    private static final String ACCESS_KEY = "CAFh14WmeLAgBGptwPc0zzahZK4bZY";*/
	
	//*********test
    private static final String ACCESS_ID = "1kDHbQZuzf8J7gLv";
    private static final String ACCESS_KEY = "CJD3tZ4CXX9vFi3iaX0BQNJksxLUZd";
    private static final String bucketName = "tigo-main";
    
    /**
     * 
     * @Title: getOSSClient
     * @Description:  获取OSSClient对象
     * @param: @param ACCESS_ID
     * @param: @param ACCESS_KEY
     * @param: @return   
     * @return: OSSClient   
     * @throws
     */
    public static OSSClient getOSSClient(String ACCESS_ID,String ACCESS_KEY){
        try {
            OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
            return client;
        } catch (Exception e) {
            System.out.println("创建OSS对象异常");
           // e.printStackTrace();
        }
       return null;
    }
    /**
     * 
     * @Title: createOss  
     * @Description: 图片上传到阿里云存储服务器方法
     * @param: @param bucketName 创建OSS 上的命名空间
     * @param: @param key  上传的图片名称
     * @param: @param uploadFilePath  上传的图片路径 eg:d:/temp/photo.jpg
     * @param: @param ACCESS_ID oss id
     * @param: @param ACCESS_KEY    oss secret
     * @return: void   
     * @throws
     */
    public static void  createOss(String bucketName,String key,String uploadFilePath,String ACCESS_ID,String ACCESS_KEY){
        // 使用默认的OSS服务器地址创建OSSClient对象。
        OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);
        try {
            if (!client.doesBucketExist(bucketName)) {
                ensureBucket(client, bucketName);//创建bucket
                //如果已经不存在bucketName 才设置读写的权限
                setBucketPublicReadable(client, bucketName);
            }
            System.out.println("正在上传到阿里云存储服务器...");
            uploadFile(client, bucketName, key, uploadFilePath);
        }catch(Exception e){
            System.out.println("上传到阿里云存储服务器 异常");
            //e.printStackTrace();
            log.info(e.getMessage());
        }finally {
//            deleteBucket(client, bucketName);
            System.out.println("上传到阿里云存储服务器 操作完毕");
        }
    }
    
    
    /**
     * 
     * @Title: deleteBucketObjcet
     * @Description: 删除bucketName命名空间下的某张图片
     * @param: @param client
     * @param: @param bucketName命名空间 
     * @param: @param objectName图片名称
     * @return: void   
     * @throws
     */
    public static void deleteBucketObjcet(OSSClient client, String bucketName,String objectName){
        try {
            client.deleteObject(bucketName, objectName);
            System.out.println("删除云服务器图片成功");
        } catch (Exception e) {
            System.out.println("删除图片异常");
            //e.printStackTrace();
        }
    }

     /**
      * 
      * @Title: ensureBucket
      * @Description:  创建Bucket.
      * @param: @param client
      * @param: @param bucketName
      * @param: @throws OSSException
      * @param: @throws ClientException   
      * @return: void   
      * @throws
      */
    private static void ensureBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException{

        try {
            // 创建bucket 
            /* Bucket 命名规范:
             * 只能包括小写字母，数字，短横线（-）
             * 必须以小写字母或者数字开头
             * 长度必须在3-63字节之间*/
            client.createBucket(bucketName);
        } catch (ServiceException e) {
            if (!OSSErrorCode.BUCKET_ALREADY_EXISTS.equals(e.getErrorCode())) {
                // 如果Bucket已经存在，则忽略
                throw e;
            }
        }
    }

    /**
     * 
     * @Title: deleteBucket
     * @Description: 删除Bucket命名空间  
     * @param: @param client
     * @param: @param bucketName
     * @param: @throws OSSException
     * @param: @throws ClientException   
     * @return: void   
     * @throws
     */
    private static void deleteBucket(OSSClient client, String bucketName)
            throws OSSException, ClientException {
        ObjectListing ObjectListing = client.listObjects(bucketName);
        List<OSSObjectSummary> listDeletes = ObjectListing
                .getObjectSummaries();
        for (int i = 0; i < listDeletes.size(); i++) {
            String objectName = listDeletes.get(i).getKey();
            // 如果不为空，先删除bucket下的文件
            client.deleteObject(bucketName, objectName);
        }
        client.deleteBucket(bucketName);
    }

    // 把Bucket设置为所有人可读
    private static void setBucketPublicReadable(OSSClient client, String bucketName)
            throws OSSException, ClientException {
        //创建bucket
        client.createBucket(bucketName);

        //设置bucket的访问权限，public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    // 上传文件
    private static void uploadFile(OSSClient client, String bucketName, String key, String filename)
            throws OSSException, ClientException, FileNotFoundException {
        File file = new File(filename);
        // 创建上传 Object 的 Metadata
        ObjectMetadata objectMeta = new ObjectMetadata();
        // 必须设置 ContentLength
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
        objectMeta.setContentType("image/jpeg");

        InputStream input = new FileInputStream(file);
        
        // 使用putObject 上传 Object.
        PutObjectResult result =client.putObject(bucketName, key, input, objectMeta);
        // 打印 ETag  ////用户可以根据 ETag 检验上传的文件与本地的是否一致
        System.out.println("ETag="+result.getETag());
        if(input!=null){
        	 try {
     			input.close();
     		} catch (IOException e) {
     			//e.printStackTrace();
     		}
        }
       
    }

    /**
     * 
     * @Title: downloadFile
     * @Description: 下载文件
     * @param: @param client  oss对象
     * @param: @param bucketName oss命名空间
     * @param: @param key 图片名称
     * @param: @param downloadFilePath 下载的文件目录  
     * @param: @throws OSSException
     * @param: @throws ClientException   
     * @return: void   
     * @throws
     */
    public static void downloadFile(OSSClient client, String bucketName, String key, String downloadFilePath)
            throws OSSException, ClientException {
        client.getObject(new GetObjectRequest(bucketName, key),new File(downloadFilePath));
    }
    
    
    
    
    /* public static void main(String[] args) throws Exception {
    String uploadFilePath = "d:/temp/20141219232619490193653.png";
    String downloadFilePath = "d:/temp/photo1 .jpg";

    // 使用默认的OSS服务器地址创建OSSClient对象。
    OSSClient client = new OSSClient(ACCESS_ID, ACCESS_KEY);

    ensureBucket(client, bucketName);
    
    try {
        if (!client.doesBucketExist(bucketName)) {
            //如果已经不存在bucketName 才设置读写的权限
            setBucketPublicReadable(client, bucketName);
        }
       
        System.out.println("正在上传...");
        uploadFile(client, bucketName, "upload/20141219232619490193653.png", uploadFilePath);
        System.out.println("上传结束");

//        System.out.println("正在下载...");
//        downloadFile(client, bucketName, key, downloadFilePath);
    } finally {
//        deleteBucket(client, bucketName);
        System.out.println("操作完毕");
    }}
*/
}
