package org.tech.springcode.utils;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class AliCloudUploadUtil {
    private static final String  ENDPOINT = "oss-cn-beijing.aliyuncs.com";
    private static final String ACCESS_KEY = "2";
    private static final String ACCESS_KEY_SECRET = "3";
    private static final String BUCKET_NAME = "michaelchan";

    public static String uploadFile(String objectName, InputStream in) throws Exception {

        String url = "";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY, ACCESS_KEY_SECRET);
        url = "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + objectName;
        try {
            String content = "Hello OSS";
            ossClient.putObject(BUCKET_NAME, objectName, new ByteArrayInputStream(content.getBytes()));

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return url;
    }
}