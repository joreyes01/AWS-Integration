package com.dizstance.strategy;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

public interface S3OperationStrategy {
    void createTxt(LambdaLogger lambdaLogger, S3Client s3Client);
}
