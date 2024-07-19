package org.tech.springcode.utils;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class AliCloudUploadUtil {
    private static final String  ENDPOINT = "oss-cn-beijing.aliyuncs.com";

    @Value("${uploadSetting.ACCESS_KEY}")
    private static String ACCESS_KEY;

    @Value("${uploadSetting.ACCESS_KEY_SECRET}")
    private static String ACCESS_KEY_SECRET;

    private static final String BUCKET_NAME = "michaelchan";

    public static String uploadFile(String objectName, InputStream in) throws Exception {

        String url = "";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY, ACCESS_KEY_SECRET);
        url = "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + objectName;
        try {
            String content = "Hello OSS";
            ossClient.putObject(BUCKET_NAME, objectName, in);
            url = "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + objectName;

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