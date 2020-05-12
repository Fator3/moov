package com.fator3.nudoor.models;


public class ResponseDTO {
	
	private String message;
	private Object body;
	private int code;
	
	public ResponseDTO(String message, Object body, int code) {
        this.message = message;
        this.body = body;
        this.code = code;
    }
    
    private ResponseDTO(String message, int code) {
        this.message = message;
        this.code = code;
    }
    
    public static ResponseDTO of(final String message, final Object body, final int code) {
        return new ResponseDTO(message, body, code);
    }
    
    public static ResponseDTO of(final String message, final int code) {
        return new ResponseDTO(message, code);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}


    
	
}
