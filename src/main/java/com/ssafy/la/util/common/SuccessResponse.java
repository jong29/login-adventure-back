package com.ssafy.la.util.common;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SuccessResponse implements CommonResponse {
	private final static int SUCCESSCODE = 0;
	private final int code;
	private final String msg;
	private final Map<String, Object> data;
	
	
	public static ResponseEntity<CommonResponse> toResponseEntity(int status, String msg, Map<String, Object> data) {
		return ResponseEntity.status(status)
				.body(SuccessResponse.builder()
						.code(SUCCESSCODE)
						.msg(msg)
						.data(data).build()
				);
	}
	

}
