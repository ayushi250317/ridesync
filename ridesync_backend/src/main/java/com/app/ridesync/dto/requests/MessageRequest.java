package com.app.ridesync.dto.requests;

public record MessageRequest(Integer senderId, Integer recipientId, String message) {}
