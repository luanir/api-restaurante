package com.example.exception;
import java.util.Map;

import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> tratarRuntime(RuntimeException ex){
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("erro", ex.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> tratarValidacao(MethodArgumentNotValidException ex){
		String mensagem = ex.getBindingResult()
				.getFieldErrors()
				.get(0)
				.getDefaultMessage();
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("erro", mensagem));
		
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Map<String, String>> tratarBanco(DataIntegrityViolationException ex){
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(Map.of("erro", "Erro de integridade no banco de dados!"));
	}
	
}
