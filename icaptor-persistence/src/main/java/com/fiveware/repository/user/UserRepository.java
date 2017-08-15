package com.fiveware.repository.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fiveware.model.user.IcaptorUser;

@Repository
public interface UserRepository extends CrudRepository<IcaptorUser, Long>{

	Optional<IcaptorUser> findByEmailAndActive(String email, boolean ativo);
}
