package br.com.torne.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.torne.model.Curso;
import br.com.torne.model.Topico;
import br.com.torne.repository.CursoRepository;
import br.com.torne.repository.TopicosRepository;

public class TopicoForm {
	
	@NotNull @NotEmpty(message = "Título é obrigatório") @Size(min = 2, message = "Título menor que o permitido. Mínimo 2.")
	private String titulo;
	
	@NotNull @NotEmpty(message = "Mensagem é obrigatório") @Size(min = 2, message = "Título menor que o permitido. Mínimo 2.")
	private String mensagem;
	
	@NotNull @NotEmpty(message = "Curso é obrigatório") @Size(min = 2, message = "Título menor que o permitido. Mínimo 2.")
	private String nomeCurso;
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMessage(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getNomeCurso() {
		return nomeCurso;
	}
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	
	
	
	public TopicoForm() {
	}
	
	public TopicoForm(String titulo, String mensagem, String nomeCurso) {
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.nomeCurso = nomeCurso;
	}
	
	public Topico converter(CursoRepository cursoRepository) {
		Curso curso = cursoRepository.findByNome(nomeCurso);
		return new Topico(titulo, mensagem, curso);
	}
	
}
