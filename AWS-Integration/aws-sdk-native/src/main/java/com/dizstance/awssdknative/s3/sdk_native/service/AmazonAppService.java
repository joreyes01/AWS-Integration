package com.dizstance.awssdknative.s3.sdk_native.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;

@Service
@Slf4j
public class AmazonAppService {

    @Value(value = "${app-config.amazon.s3.bucket-name}")
    private String bucketName;
    private final S3Client s3Client;

    public AmazonAppService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void createBucket(){
        String newBucketName = "java-new-bucket2";
        Region newBucketRegion = Region.US_EAST_2;

        CreateBucketRequest createBucketRequest = CreateBucketRequest
                .builder()
                .bucket(newBucketName)
                .createBucketConfiguration(CreateBucketConfiguration
                        .builder()
                        .locationConstraint(newBucketRegion.id())
                        .build())
                .build();
        try {
            log.info("Creating new bucket : {}", newBucketName);
            s3Client.createBucket(createBucketRequest);
            log.info("The bucket {} is created successfully !", newBucketName);
        } catch (Exception e) {
            log.error("Error creating bucket - {}", newBucketName);
            log.error(e.getMessage());
        }
    }

    // This example uses RequestBody.fromFile to avoid loading the whole file into memory.
    @PostConstruct
    private void putObjectOnS3() {
        String objectFilePath = "./AWS/MonaLisa.jpg";
        String objectKey = "imagen3.jpg";

        PutObjectRequest putObjectRequest = PutObjectRequest
                .builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();
        try {
            log.info("Put object : {}", objectFilePath);
            s3Client.putObject(putObjectRequest, RequestBody.fromFile(new File(objectFilePath)));
            log.info("The object {} was upload !", objectFilePath);
        } catch (Exception e) {
            log.error("Error putting object - {}", objectFilePath);
            log.error(e.getMessage());
        }

    }
}
