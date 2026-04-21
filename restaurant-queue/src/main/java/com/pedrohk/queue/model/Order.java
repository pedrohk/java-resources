package com.pedrohk.queue.model;

import java.time.LocalDateTime;

public record Order(int id, Dish dish, LocalDateTime createdAt) {}
