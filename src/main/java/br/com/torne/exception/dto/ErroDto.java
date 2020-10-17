package br.com.torne.exception.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErroDto {
	
	private final String mensagem;
	private final int status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private final LocalDateTime timestamp = LocalDateTime.now();
	
	public ErroDto(String mensagem, int status) {
		this.mensagem = mensagem;
		this.status = status;
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public int getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

}
