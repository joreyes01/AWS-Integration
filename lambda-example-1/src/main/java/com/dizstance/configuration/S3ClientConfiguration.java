package com.dizstance.configuration;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3ClientConfiguration {

    private final S3Client s3Client;

    public S3ClientConfiguration() {
        this.s3Client = S3Client.builder().build();
    }

    public S3Client getS3Client() {
        return s3Client;
    }
}
