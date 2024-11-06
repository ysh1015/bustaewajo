package com.hk.transportProject.model;

// 서버 응답을 받아 getter로 저장
public class LoginResponse {

    private boolean success;
    private String message;

    public LoginResponse() {}


    public LoginResponse(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    // getter and setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
