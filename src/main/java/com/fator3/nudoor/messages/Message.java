package com.fator3.nudoor.messages;

public enum Message {

    TOOL_INIT(true, 200, "Novo usuário");

    private boolean result;
    private int code;
    private String message;

    Message(boolean result, int code, String meesage) {
        this.result = result;
        this.code = code;
        this.message = meesage;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
