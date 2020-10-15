package com.omarghorbel.storecrud.dao;

import com.omarghorbel.storecrud.entities.ImageRetour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin("*")
@RepositoryRestResource
public interface ImageRetourRepository extends JpaRepository<ImageRetour, Long> {
    Optional<ImageRetour> findByName(String name);
}
