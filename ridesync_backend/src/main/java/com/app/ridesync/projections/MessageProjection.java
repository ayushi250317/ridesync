package com.app.ridesync.projections;

import java.time.LocalDateTime;

public record MessageProjection(Integer senderId, Integer recipientId, String message, LocalDateTime sentTime) {}
