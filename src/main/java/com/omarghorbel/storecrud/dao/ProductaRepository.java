package com.omarghorbel.storecrud.dao;

import com.omarghorbel.storecrud.entities.Producta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin("*")
@RepositoryRestResource
public interface ProductaRepository extends JpaRepository<Producta, Long> {
    Optional<Producta> findByName(String name);
}
