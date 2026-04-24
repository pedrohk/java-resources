package com.pedrohk.organizer.model;

import java.util.UUID;

public record SchoolClass(UUID id, String subject, String groupName, TimeSlot slot) {}
