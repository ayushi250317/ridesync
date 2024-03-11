package com.app.ridesync.projections;

import java.time.LocalDateTime;

public record MessageProjection(String senderName, String recipientName, String message, LocalDateTime sentTime) {}
