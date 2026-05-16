package com.pedrohk.router.core;

import com.pedrohk.router.Logger;

public class FileSystemLogger implements Logger {
    @Override
    public void log(String message) {
        System.out.println("[FS] Writing to log file: " + message);
    }
}
