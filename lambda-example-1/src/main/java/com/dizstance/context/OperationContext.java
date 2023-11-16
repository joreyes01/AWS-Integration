package com.dizstance.context;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.dizstance.configuration.S3ClientConfiguration;
import com.dizstance.strategy.S3DefaultOperationStrategy;
import com.dizstance.strategy.S3OperationStrategy;

public class OperationContext {
    private S3OperationStrategy s3OperationStrategy;
    private final LambdaLogger lambdaLogger;

    private final S3ClientConfiguration s3ClientConfiguration = new S3ClientConfiguration();

    public OperationContext(LambdaLogger lambdaLogger) {
        this.s3OperationStrategy = new S3DefaultOperationStrategy();
        this.lambdaLogger = lambdaLogger;
    }

    public void setS3OperationStrategy(S3OperationStrategy s3OperationStrategy) {
        this.s3OperationStrategy = s3OperationStrategy;
    }

    public void createTxt() {
        s3OperationStrategy.createTxt(lambdaLogger, s3ClientConfiguration.getS3Client());
    }
}
