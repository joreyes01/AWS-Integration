package com.dizstance.cloudfunctions.functions;

import java.util.function.Supplier;

public class Greeter implements Supplier<String> {
    @Override
    public String get() {
        return "Hello from Supplier Functions!!";
    }
}
