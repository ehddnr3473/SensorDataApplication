package com.example.sensor.global.result;

import com.example.sensor.common.Constants;

public enum ResultCode {

	SUCCESS(Constants.RESULT_SUCCESS, "성공"), 
	FAILURE(Constants.RESULT_FAILURE, "실패"), 
	UNDEFINED(Constants.RESULT_FAILURE, "알 수 없는 오류가 발생했습니다.");
	
    private final String code;
    private final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() { return code; }
    public String getMessage() { return message; }
}
