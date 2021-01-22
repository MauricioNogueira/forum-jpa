package br.com.zup.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.zup.forum.modelo.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
