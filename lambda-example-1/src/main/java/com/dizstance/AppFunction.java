package com.dizstance;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.dizstance.context.OperationContext;
import com.dizstance.strategy.S3CreateBasicTxtStrategy;
import com.dizstance.strategy.S3CreateComplexTxtStrategy;

public class AppFunction implements RequestHandler<Integer,String> {

    @Override
    public String handleRequest(Integer s3Option, Context context) {
        OperationContext operationContext = new OperationContext(context.getLogger());

        String message = switch (s3Option) {
            case 1 -> {
                operationContext.setS3OperationStrategy(new S3CreateBasicTxtStrategy());
                yield "Basic TXT created";
            }
            case 2 -> {
                operationContext.setS3OperationStrategy(new S3CreateComplexTxtStrategy());
                yield "Complex TXT created";
            }
            default -> "This option its not supported";
        };

        operationContext.createTxt();
        return message;


    }
}
