package com.dizstance;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SimpleHandler implements RequestHandler<String, String> {
    @Override
    public String handleRequest(String input, Context context) {
        context.getLogger().log("The Function ' "+context.getFunctionName()+" ' is executed !");
        return input.toUpperCase();
    }
}
