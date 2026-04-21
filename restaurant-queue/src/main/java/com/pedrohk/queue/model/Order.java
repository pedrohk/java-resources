package com.pedrohk.queue.model;

import java.time.LocalDateTime;

public record Order(int id, String customerEmail, Dish dish, LocalDateTime createdAt) {}
