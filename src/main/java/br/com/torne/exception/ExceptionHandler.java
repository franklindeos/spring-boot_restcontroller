package br.com.torne.exception;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.torne.exception.dto.ErroDto;

@RestControllerAdvice
public class ExceptionHandler {
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@org.springframework.web.bind.annotation.ExceptionHandler(value = {EntityNotFoundException.class, EmptyResultDataAccessException.class})
	public List<ErroDto> handle(EmptyResultDataAccessException exception) {
		List<ErroDto> listDto = new ArrayList<ErroDto>();
			String mensagem = exception.getMessage();
			ErroDto erroDto = new ErroDto(mensagem, HttpStatus.NOT_FOUND.value());
			listDto.add(erroDto);
		return listDto;
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
	public List<ErroDto> handle(Exception exception) {
		List<ErroDto> listDto = new ArrayList<ErroDto>();
			String mensagem = exception.getMessage();
			ErroDto erroDto = new ErroDto(mensagem, HttpStatus.INTERNAL_SERVER_ERROR.value());
			listDto.add(erroDto);
		return listDto;
	}
	
	
	

}
