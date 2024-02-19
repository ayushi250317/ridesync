package com.app.ridesync.dto.responses;

public record ApiResponse<T> (T responseObject,boolean success,String message) {}
