package com.pedrohk.router.core;

import com.pedrohk.router.Logger;

public class ElkLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("[ELK] Routing to Logstash: " + message);
    }
}
