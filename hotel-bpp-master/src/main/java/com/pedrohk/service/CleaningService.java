package com.pedrohk.service;

import com.pedrohk.annotation.AuditLog;
import org.springframework.stereotype.Service;

@Service
@AuditLog
public class CleaningService {
    private boolean inspected = false;

    public boolean isInspected() {
        return inspected;
    }

    public void setInspected(boolean inspected) {
        this.inspected = inspected;
    }

    public String performCleaning() {
        return "Room is clean";
    }
}
