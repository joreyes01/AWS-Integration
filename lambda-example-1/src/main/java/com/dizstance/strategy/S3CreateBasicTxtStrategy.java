package com.dizstance.strategy;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class S3CreateBasicTxtStrategy implements S3OperationStrategy {
    @Override
    public void createTxt(LambdaLogger lambdaLogger, S3Client s3Client) {

        String content = "This is a basic txt";

        lambdaLogger.log("Init creating basic TXT");
        putObject(s3Client,"dizstance-test-bucket", content, lambdaLogger);
        lambdaLogger.log("TXT Created");

    }

    private void putObject(S3Client s3Client, String bucket, String content, LambdaLogger lambdaLogger) {
        try {
            s3Client.putObject(builder -> builder
                            .bucket(bucket)
                            .key("demo.txt")
                            .build()
                    , RequestBody.fromString(content));
        }catch (Exception e) {
            lambdaLogger.log("ERROR : " + e.getMessage());
        }
    }
}
