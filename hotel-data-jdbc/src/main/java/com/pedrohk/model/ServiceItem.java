package com.pedrohk.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("SERVICE_ITEM")
public record ServiceItem(@Id Long id, String description, double price) {}
