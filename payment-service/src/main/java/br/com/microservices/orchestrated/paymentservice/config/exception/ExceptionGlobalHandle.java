package br.com.microservices.orchestrated.paymentservice.config.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandle {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ExceptionDetails> handleValidationException(ValidationException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDetails(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
	}
}
