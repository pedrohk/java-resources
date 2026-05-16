package com.pedrohk.router;

import com.pedrohk.router.core.ConsoleLogger;
import com.pedrohk.router.core.ElkLogger;
import com.pedrohk.router.core.FileSystemLogger;

public class LoggerBuilder {
    private LogDestination destination = LogDestination.CONSOLE;
    private boolean async = false;

    public LoggerBuilder to(LogDestination destination) {
        this.destination = destination;
        return this;
    }

    public LoggerBuilder async() {
        this.async = true;
        return this;
    }

    public Logger build() {
        Logger logger = switch (destination) {
            case FILESYSTEM -> new FileSystemLogger();
            case ELK -> new ElkLogger();
            case CONSOLE -> new ConsoleLogger();
        };

        return async ? new AsyncLogger(logger) : logger;
    }
}
