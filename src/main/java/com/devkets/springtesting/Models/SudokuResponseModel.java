package com.devkets.springtesting.Models;

public class SudokuResponseModel {
    private String message;
    private SudokuRequestModel request;

    public SudokuRequestModel getRequest() {
        return request;
    }
    public void setRequest(SudokuRequestModel request) {
        this.request = request;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
