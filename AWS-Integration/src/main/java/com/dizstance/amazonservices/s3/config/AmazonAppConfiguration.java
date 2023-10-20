package com.dizstance.amazonservices.s3.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.awscore.client.builder.AwsDefaultClientBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AmazonAppConfiguration {

    @Value(value = "${app-config.amazon.s3.access-key}")
    private String awsS3AccessKey;

    @Value(value = "${app-config.amazon.s3.secret-key}")
    private String awsS3SecretKey;

//    @Value(value = "${app-config.amazon.s3.region}")
//    private String awsS3Region;
//
//    @Value(value = "${app-config.amazon.s3.bucket-name}")
//    private String awsS3BucketName;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_2)
                .credentialsProvider(getStaticCredentialsProviderS3())
                .build();
    }

    private StaticCredentialsProvider getStaticCredentialsProviderS3() {
        return StaticCredentialsProvider
                .create(AwsBasicCredentials
                        .create(awsS3AccessKey, awsS3SecretKey));
    }


}
