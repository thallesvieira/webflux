package com.programming.reactive.domain.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ExceptionResponseCustom {
	private LocalDateTime timestamp;
	private HttpStatus status;
	private int code;
	private String message;
	private String path;
}
