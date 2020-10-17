package br.com.torne.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.torne.model.Topico;

public interface TopicosRepository extends JpaRepository<Topico, Long> {

	// findBy Curso é uma relacionamento, está filtrando por Nome. Pode utilizar
	// Curso_Nome que o spring vai entender.
	List<Topico> findByCursoNome(String cursoNome);
	
	/*
	 * Customizar método saindo do padrão do Spring
	 * 
	 * @Query("SELECT t FROM Topico t WHERE t.curso.nome = :cursoNome") List<Topico>
	 * buscarPorNomeCurso(@Param("cursoNome")String cursoNome);
	 */

}
