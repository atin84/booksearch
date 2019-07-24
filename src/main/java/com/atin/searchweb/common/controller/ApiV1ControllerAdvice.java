package com.atin.searchweb.common.controller;

import com.atin.searchweb.common.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@Slf4j
@RequestMapping("/api/v1/")
@ControllerAdvice
public class ApiV1ControllerAdvice {

	@ResponseBody
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseDto illegalArgumentException(IllegalArgumentException e) {
		log.warn(e.toString());
		return ResponseDto.error(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseDto constraintViolationException(ConstraintViolationException e) {
		log.warn(e.toString());
		return ResponseDto.error(e.getMessage());
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseDto exception(Exception e) {
		log.error(e.toString(), e);
		return ResponseDto.error(e.getMessage());
	}
}
