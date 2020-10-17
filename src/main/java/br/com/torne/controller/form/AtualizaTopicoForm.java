package br.com.torne.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.torne.model.Topico;
import br.com.torne.repository.TopicosRepository;

public class AtualizaTopicoForm {
	
	@NotNull @NotEmpty(message = "Título é obrigatório") @Size(min = 2, message = "Título menor que o permitido. Mínimo 2.")
	private String titulo;
	
	@NotNull @NotEmpty(message = "Mensagem é obrigatório") @Size(min = 2, message = "Título menor que o permitido. Mínimo 2.")
	private String mensagem;
	
	
	
	public AtualizaTopicoForm(Topico topico) {
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
	}

	


	public AtualizaTopicoForm() {
	}




	public Topico atualizar(Long id, TopicosRepository topicosRepository) {
		Topico topico = topicosRepository.getOne(id);
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
		return topico;
	}




	public String getTitulo() {
		return titulo;
	}




	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}




	public String getMensagem() {
		return mensagem;
	}




	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
	
}
