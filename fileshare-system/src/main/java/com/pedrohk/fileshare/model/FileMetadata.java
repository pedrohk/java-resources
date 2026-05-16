package com.pedrohk.fileshare.model;

import java.time.LocalDateTime;

public record FileMetadata(String fileName, long size, LocalDateTime createdAt) {}
