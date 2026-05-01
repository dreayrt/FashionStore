package com.dreayrt.fashion_store.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.net.URI;

@Service
public class R2Service {
    private final S3Client s3;

    @Value("${r2.bucket}")
    private String bucketName;

    public R2Service(
            @Value("${r2.endpoint}") String endpoint,
            @Value("${r2.access-key}") String accessKey,
            @Value("${r2.secret-key}") String secretKey
    ) {
        this.s3 = S3Client.builder() //tao client de giao tiep voi storage(cloudflare r2)
                .endpointOverride(URI.create(endpoint))//S3 client goi den r2 endpoint
                .region(Region.of("auto"))//AWS bat buoc phai co region nhung R2 khong dung region that nen set auto
                //gắn Access Key + Secret Key để xác thực
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .build();//build ra object `S3Client`

    }

    //key: duong dan file trong bucket
    //file: file upload tu client
    public void uploadFile(String key , MultipartFile file) throws Exception {
        //Gửi request upload file
        //Thực chất là:
        //```http
        //PUT /bucket/key
        s3.putObject(
                PutObjectRequest.builder()//tạo “nội dung request” (request config) //header
                        .bucket(bucketName)
                        .key(key)
                        .contentType(file.getContentType())// metadata(thong tin di kem)
                        .build(),//hoàn thành request config
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())//Biến file của bạn thành dữ liệu (body) để gửi đi trong HTTP request  //body
        );
    }
}
