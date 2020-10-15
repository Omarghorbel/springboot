package com.omarghorbel.storecrud.dao;

import com.omarghorbel.storecrud.entities.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@CrossOrigin("*")
@RepositoryRestResource
public interface CommandeRepository extends JpaRepository<Commande,Long> {

//    @Query("select c from Commande c order by 'id' desc")
//    List<Commande> findAllx();
//@Query("select c from commande c order by 'id' desc")

    @Query("select c from Commande c order by c.id desc")
    Page<Commande> findAllx(Pageable pageable);


}
