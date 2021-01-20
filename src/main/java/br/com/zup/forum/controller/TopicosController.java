package br.com.zup.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.forum.dto.DetalhesDoTopicoDto;
import br.com.zup.forum.dto.TopicoDto;
import br.com.zup.forum.forms.AtualizacaoTopicoForm;
import br.com.zup.forum.forms.TopicoForm;
import br.com.zup.forum.modelo.Topico;
import br.com.zup.forum.repository.CursoRepository;
import br.com.zup.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public Page<TopicoDto> lista(String nomeCurso, @PageableDefault(sort = "id", direction = Direction.DESC) Pageable pageable) {
		Page<Topico> topicos;
		
		if (nomeCurso == null) {
			topicos = this.topicoRepository.findAll(pageable);
		} else {
			topicos = this.topicoRepository.findByCursoNome(nomeCurso, pageable);
		}
		
		return TopicoDto.converter(topicos);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(this.cursoRepository);
		
		this.topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		Optional<Topico> optional = this.topicoRepository.findById(id);
		
		if (optional.isPresent()) {
			Topico topico = optional.get();
			
			DetalhesDoTopicoDto detalhesTopico = new DetalhesDoTopicoDto(topico);
			
			return ResponseEntity.ok().body(detalhesTopico);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@Transactional
	@PutMapping("/{id}")
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		Optional<Topico> optional = this.topicoRepository.findById(id);
		
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, this.topicoRepository);
			
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> optional = this.topicoRepository.findById(id);
		
		if (optional.isPresent()) {			
			this.topicoRepository.deleteById(id);
			
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.badRequest().build();
	}
}
