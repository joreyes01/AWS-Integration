package com.dizstance.strategy;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import software.amazon.awssdk.services.s3.S3Client;

public class S3CreateComplexTxtStrategy implements S3OperationStrategy {
    @Override
    public void createTxt(LambdaLogger lambdaLogger, S3Client s3Client) {
        lambdaLogger.log("Creating a complex txt");
    }
}
