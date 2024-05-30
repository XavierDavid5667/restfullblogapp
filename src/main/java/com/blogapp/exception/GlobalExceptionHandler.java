package com.blogapp.exception;


import java.time.LocalDate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	 @ExceptionHandler(Exception.class)
	    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request)
	    {
	        ExceptionResponse exceptionResponse=new ExceptionResponse(
	                LocalDate.now(),ex.getMessage(),request.getDescription(false),"Internal Server Error");
	        log.info(ex.getMessage());
	        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	
	@ExceptionHandler(ResouceNotFoundException.class)
	public ResponseEntity<String> resourceNotFoundExceptionHandler(ResouceNotFoundException resouceNotFoundException){
		return new ResponseEntity<String>(resouceNotFoundException.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	 @Override
	    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {


	        StringBuilder sb=new StringBuilder();
	        for(FieldError fe:ex.getBindingResult().getFieldErrors()) {
	            sb.append(fe.getField()).append(":").append(fe.getDefaultMessage()).append(".");
	        }
	        ExceptionResponse exceptionResponse=new ExceptionResponse(
	                LocalDate.now(),"Validation Fails",sb.toString(),"Bad Request");
	        log.info("Validation Fail");
	        return new ResponseEntity<Object> (exceptionResponse, HttpStatus.BAD_REQUEST);
	    }

}
