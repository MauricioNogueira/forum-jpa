package br.com.zup.forum.forms;

import java.util.Optional;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.zup.forum.modelo.Topico;
import br.com.zup.forum.repository.TopicoRepository;

public class AtualizacaoTopicoForm {
	
	@NotBlank
	@NotNull
	@Length(min = 5)
	private String titulo;
	
	@NotBlank
	@NotNull
	@Length(min = 10)
	private String mensagem;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public Topico atualizar(Long id, TopicoRepository topicoRepository) {
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if (optional.isPresent()) {
			Topico topico = optional.get();
			
			topico.setTitulo(this.titulo);
			topico.setMensagem(this.mensagem);
			
			return topico;
		}
		
		return null;
	}
}
