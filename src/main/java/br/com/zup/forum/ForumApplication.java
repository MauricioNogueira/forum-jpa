package br.com.zup.forum;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.zup.forum.modelo.Curso;
import br.com.zup.forum.modelo.Topico;
import br.com.zup.forum.modelo.Usuario;
import br.com.zup.forum.repository.CursoRepository;
import br.com.zup.forum.repository.TopicoRepository;
import br.com.zup.forum.repository.UsuarioRepository;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
public class ForumApplication implements CommandLineRunner {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}
	
	/**
	 *  Executado toda vez que o projeto for iniciado
	 *  Servi para popular o banco de dados
	 */
	@Override
	public void run(String... args) throws Exception {
		Curso curso = new Curso("Spring Boot", "Programação");
		Curso curso2 = new Curso("HTML 5", "Front-end");
		
		Usuario usuario = new Usuario("Aluno", "aluno@email", new BCryptPasswordEncoder().encode("123456"));
		
		this.cursoRepository.saveAll(Arrays.asList(curso, curso2));
		this.usuarioRepository.save(usuario);
		
		Topico topico1 = new Topico("Dúvida", "Erro ao criar projeto", curso);
		topico1.setAutor(usuario);
		Topico topico2 = new Topico("Dúvida 2", "Erro qualquer", curso);
		topico2.setAutor(usuario);
		Topico topico3 = new Topico("Dúvida 3", "Tag HTML", curso2);
		topico3.setAutor(usuario);
		
		this.topicoRepository.saveAll(Arrays.asList(topico1, topico2, topico3));
	}
}
