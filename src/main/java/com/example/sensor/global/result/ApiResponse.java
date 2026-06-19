package com.example.sensor.global.result;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

	private String code;
	private String message;
	private T data;
	
	public static <T> ApiResponse<T> success(ResultCode resultCode, T data) {
		return ApiResponse.<T>builder()
				.code(resultCode.getCode())
				.message(resultCode.getMessage())
				.data(data)
				.build();
	}
	
	public static <T> ApiResponse<T> fail(ResultCode resultCode) {
        return ApiResponse.<T>builder()
                .code(resultCode.getCode())
                .message(resultCode.getMessage())
                .build();
    }
}
