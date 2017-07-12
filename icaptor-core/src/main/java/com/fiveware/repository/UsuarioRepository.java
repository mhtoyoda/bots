package com.fiveware.repository;

import com.fiveware.model.entities.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{


	Optional<Usuario> findByEmailAndAtivo(String email, boolean ativo);
}