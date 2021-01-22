package br.com.zup.forum;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.zup.forum.modelo.Curso;
import br.com.zup.forum.modelo.Perfil;
import br.com.zup.forum.modelo.Topico;
import br.com.zup.forum.modelo.Usuario;
import br.com.zup.forum.repository.CursoRepository;
import br.com.zup.forum.repository.PerfilRepository;
import br.com.zup.forum.repository.TopicoRepository;
import br.com.zup.forum.repository.UsuarioRepository;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
public class ForumApplication implements CommandLineRunner {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;

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
		
		Perfil perfil = new Perfil("ROLE_ALUNO");
		Perfil perfil2 = new Perfil("ROLE_MODERADOR");
		
		this.perfilRepository.saveAll(Arrays.asList(perfil, perfil2));
		
		Usuario usuario = new Usuario("Aluno", "aluno@email.com", new BCryptPasswordEncoder().encode("123456"));
		Usuario usuario2 = new Usuario("Moderador", "moderador@email.com", new BCryptPasswordEncoder().encode("123456"));
		
		usuario.setPerfis(Arrays.asList(perfil));
		usuario2.setPerfis(Arrays.asList(perfil2));
		
		this.cursoRepository.saveAll(Arrays.asList(curso, curso2));
		this.usuarioRepository.saveAll(Arrays.asList(usuario, usuario2));
		
		Topico topico1 = new Topico("Dúvida", "Erro ao criar projeto", curso);
		topico1.setAutor(usuario);
		Topico topico2 = new Topico("Dúvida 2", "Erro qualquer", curso);
		topico2.setAutor(usuario);
		Topico topico3 = new Topico("Dúvida 3", "Tag HTML", curso2);
		topico3.setAutor(usuario);
		
		this.topicoRepository.saveAll(Arrays.asList(topico1, topico2, topico3));
	}
	
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(ForumApplication.class);
//	}
}
