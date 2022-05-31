package com.videouploadchallenge.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private String message;
    private Object result;

    public static final String OPERATION_FAILURE_MESSAGE = "Operation has failed.";
    public static final String RETRIEVE_SUCCESS_MESSAGE = "File list";
    public static final String NO_RECORD_FOUND_MESSAGE = "No videos found";
    public static final String CREATION_SUCCESS_MESSAGE = "File uploaded";
    public static final String SEARCH_PARAMETER_MESSAGE = "Search parameter is empty.";
    public static final String UPDATE_SUCCESS_MESSAGE = "Resource successfully updated.";
    public static final String DELETION_SUCCESS_MESSAGE = "File was successfully removed";
    public static final String UNSUPPORTED_MEDIA_TYPE_MESSAGE = "Unsupported Media Type.";

}

