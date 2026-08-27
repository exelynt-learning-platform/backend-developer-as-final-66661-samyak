package com.equipment.event_equipment_booking.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationExceptionResponseDto {

    private String apiPath;
    private Integer errorCode;
    private Map<String, String> fieldErrors;
    private LocalDateTime errorTime;

    public ValidationExceptionResponseDto() {
    }

    public ValidationExceptionResponseDto(String apiPath, Integer errorCode, Map<String, String> fieldErrors, LocalDateTime errorTime) {
        this.apiPath = apiPath;
        this.errorCode = errorCode;
        this.fieldErrors = fieldErrors;
        this.errorTime = errorTime;
    }

    public String getApiPath() {
        return apiPath;
    }

    public void setApiPath(String apiPath) {
        this.apiPath = apiPath;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, String> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    public LocalDateTime getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(LocalDateTime errorTime) {
        this.errorTime = errorTime;
    }
}