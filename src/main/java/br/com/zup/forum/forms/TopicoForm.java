package br.com.zup.forum.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.zup.forum.modelo.Curso;
import br.com.zup.forum.modelo.Topico;
import br.com.zup.forum.repository.CursoRepository;

public class TopicoForm {
	
	@NotNull
	@NotBlank
	@Length(min = 5)
	private String titulo;
	
	@NotNull
	@NotBlank
	@Length(min = 10)
	private String mensagem;
	
	@NotNull
	@NotBlank
	@Length(min = 5)
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
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public String getNomeCurso() {
		return nomeCurso;
	}
	
	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	
	public Topico converter(CursoRepository cursoRepository) {
		Curso curso = cursoRepository.findByNome(this.nomeCurso);
		
		Topico topico = new Topico(this.titulo, this.mensagem, curso);
		
		return topico;
	}
}
