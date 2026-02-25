package com.devashree.ticketing.dto;

public class CreateCommentRequest {

    private String message;

    public CreateCommentRequest() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}