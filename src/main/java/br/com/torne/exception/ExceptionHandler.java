package br.com.torne.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.torne.exception.dto.ErroDto;

@RestControllerAdvice
public class ExceptionHandler {
	
	private MessageSource messageSource;

	@Autowired
	public ExceptionHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
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
	
	@org.springframework.web.bind.annotation.ExceptionHandler(RestException.class)
    public ResponseEntity<RestMessage> handleIllegalArgument(RestException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale);
        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }
	
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<RestMessage> handleArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
	        BindingResult result = ex.getBindingResult();
	        List<String> errorMessages = result.getAllErrors()
	                .stream()
	                .map(objectError -> messageSource.getMessage(objectError, locale))
	                .collect(Collectors.toList());
	        return new ResponseEntity<>(new RestMessage(errorMessages), HttpStatus.BAD_REQUEST);
	    }

	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	    public ResponseEntity<RestMessage> handleExceptions(Exception ex, Locale locale) {
	        String errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);
	        ex.printStackTrace();
	        return new ResponseEntity<>(new RestMessage(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	

}
