package br.com.torne.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.torne.controller.dto.DetalhesDoTopicoDto;
import br.com.torne.controller.dto.TopicoDto;
import br.com.torne.controller.form.AtualizaTopicoForm;
import br.com.torne.controller.form.TopicoForm;
import br.com.torne.exception.RecursoNaoEncontradoException;
import br.com.torne.model.Topico;
import br.com.torne.repository.CursoRepository;
import br.com.torne.repository.TopicosRepository;

@RestController
@RequestMapping("/topicos")
@Validated
public class TopicosController {
	
	private final TopicosRepository topicosRepository;
	private final CursoRepository cursoRepository;
	
	
	public TopicosController(TopicosRepository topicosRepository,CursoRepository cursoRepository) {
		this.topicosRepository = topicosRepository;
		this.cursoRepository = cursoRepository;
	}

	@GetMapping
	public List<TopicoDto> listarTodos() {
		return TopicoDto.converter(topicosRepository.findAll());
	}
	
	@GetMapping("/curso/{cursoNome}")
	public List<TopicoDto> listarPorNome(@PathVariable String cursoNome) {
		List<TopicoDto> topicosDto = new ArrayList<TopicoDto>();
		if(cursoNome==null) {
			topicosDto = listarTodos(); 
		} else {
			List<Topico> topicosDoCurso = topicosRepository.findByCursoNome(cursoNome);
			if(topicosDoCurso.isEmpty()) {
				new RecursoNaoEncontradoException("Topicos do Curso com nome : " +cursoNome+ " não encontrados");
			}
			topicosDto = TopicoDto.converter(topicosDoCurso);
		}
		return topicosDto;
	}
	
	@PostMapping
	public ResponseEntity<TopicoDto> cadastrar( @RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder ) {
		Topico topico = topicoForm.converter(cursoRepository);
		topicosRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> detalhar(@PathVariable Long id) {
		Topico topico = topicosRepository.findById(id)
				.orElseThrow(()->new RecursoNaoEncontradoException("Tópico com id: " +id+ " não encontrado"));
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,@RequestBody @Valid AtualizaTopicoForm form) {
		Topico topico = form.atualizar(id,topicosRepository);
		topicosRepository.save(topico);
		return ResponseEntity.ok(new TopicoDto(topico));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> remover(@PathVariable @NotNull Long id) {
			topicosRepository.deleteById(id);
			return ResponseEntity.ok().build();
	}
	
//	@DeleteMapping("/{id}")
//	@Transactional
//	public ResponseEntity remover(@PathVariable Long id) {
//		Optional<Topico> topico = topicosRepository.findById(id);
//		if(topico.isPresent()) { 
//			topicosRepository.deleteById(id);
//			return ResponseEntity.ok().build();
//		}
//		
//		return ResponseEntity.notFound().build();
//	}

}
