package br.com.torne.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import br.com.torne.exception.dto.ErroDeFormularioDto;

@RestControllerAdvice
public class ErroDeValidacaoHandler {
	
	private MessageSource messageSource;

	@Autowired
	public ErroDeValidacaoHandler(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public List<ErroDeFormularioDto> handle(MethodArgumentNotValidException exception, WebRequest request) {
		List<ErroDeFormularioDto> listDto = new ArrayList<ErroDeFormularioDto>();
		List<FieldError> fildErrors = exception.getBindingResult().getFieldErrors();
		System.out.println(request.getLocale());
		fildErrors.forEach(ex -> {
			MessageSourceResolvable messageSourceResolvable = new DefaultMessageSourceResolvable("erro.mensagem");
			String mensagem = (messageSource).getMessage(messageSourceResolvable, LocaleContextHolder.getLocale());
			ErroDeFormularioDto erroDto = new ErroDeFormularioDto(null, mensagem, HttpStatus.BAD_REQUEST.value());
			listDto.add(erroDto);
		});
		return listDto;
	}
	
}
