package br.com.zup.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.forum.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
