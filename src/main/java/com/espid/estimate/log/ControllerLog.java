package com.espid.estimate.log;

import java.time.ZonedDateTime;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ControllerLog {
    
    private String httpMethod;

    private String urlPattern;

    private ZonedDateTime requestAt;

    public ControllerLog(String httpMethod, String urlPattern, ZonedDateTime requestAt) {
        this.httpMethod = httpMethod;
        this.urlPattern = urlPattern;
        this.requestAt = requestAt;
    }
}