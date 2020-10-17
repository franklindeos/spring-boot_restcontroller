package br.com.torne.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.torne.controller.dto.DetalhesDoTopicoDto;


@SpringBootTest
@AutoConfigureMockMvc
public class TopicosControllerIntegrationTest {
	
	@Autowired
	private JacksonTester<DetalhesDoTopicoDto> json;
	
	@Autowired
    ObjectMapper objectMapper;
	
	@Test
	public void deveriaRetornarTodosOsTopicos(@Autowired MockMvc mvc) throws Exception {
		  mvc.perform(get("/topicos"))
		  	.andExpect(status().isOk())
		  	.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void deveriaRetornarODetalhe(@Autowired MockMvc mvc) throws Exception {
		String content = mvc.perform(get("/topicos/1")).andReturn().getResponse().getContentAsString();
		assertThat(this.json.parseObject(content).getId()==1L);
		System.out.println(this.json.parseObject(content).getTitulo());
	}

}
