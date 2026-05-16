package com.pedrohk.ticket.model;

import java.time.LocalDateTime;
import java.util.Map;

public record Show(String id, String name, LocalDateTime date, Map<Zone, Integer> capacity) {}
