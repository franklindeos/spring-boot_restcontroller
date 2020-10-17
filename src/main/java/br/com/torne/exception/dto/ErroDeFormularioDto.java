package br.com.torne.exception.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErroDeFormularioDto {
	
	private final String campo;
	private final String mensagem;
	private final int status;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private final LocalDateTime timestamp = LocalDateTime.now();
	
	public ErroDeFormularioDto(String campo, String mensagem, int status) {
		this.campo = campo;
		this.mensagem = mensagem;
		this.status = status;
	}
	
	public String getCampo() {
		return campo;
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
