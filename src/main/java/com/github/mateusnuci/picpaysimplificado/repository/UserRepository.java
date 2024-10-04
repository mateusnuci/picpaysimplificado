package com.github.mateusnuci.picpaysimplificado.repository;

import com.github.mateusnuci.picpaysimplificado.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> fyndUserByDocument(String document);



}
