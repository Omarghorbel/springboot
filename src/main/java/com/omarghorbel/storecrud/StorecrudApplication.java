package com.omarghorbel.storecrud;

import com.omarghorbel.storecrud.dao.CommandeRepository;
import com.omarghorbel.storecrud.entities.AppRole;
import com.omarghorbel.storecrud.entities.Commande;
import com.omarghorbel.storecrud.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class StorecrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorecrudApplication.class, args);
    }
    @Bean
    CommandLineRunner start(AccountService accountService, CommandeRepository commandeRepository){
        return args -> {
            accountService.save(new AppRole(null, "USER"));
            accountService.save(new AppRole(null,"ADMIN"));
            Stream.of("user1","user2","tarak","admin").forEach(un->{
                accountService.saveUser(un,"1234","1234");

            });
            accountService.addRoleToUser("admin","ADMIN");


            accountService.addRoleToUser("admin","ADMIN");
//            commandeRepository.save(new Commande(null, "omar ghorbel","omar za","xx","zz","white","tunis aouina hay wahat","23742760","disponible le jeudi",6.0,"confirmé","livraison vers gofast confirmé","en cours",new Date()));
//            commandeRepository.save(new Commande(null, "omar ghorbel","omar za","xx","zz","white","tunis aouina hay wahat","23742760","disponible le jeudi",6.0,"confirmé","livraison vers gofast confirmé","en cours",new Date()));
//            commandeRepository.save(new Commande(null, "omar ghorbel","omar za","xx","zz","white","tunis aouina hay wahat","23742760","disponible le jeudi",6.0,"confirmé","livraison vers gofast confirmé","en cours",new Date()));
//            commandeRepository.save(new Commande(null, "omar ghorbel","omar za","xx","zz","white","tunis aouina hay wahat","23742760","disponible le jeudi",6.0,"confirmé","livraison vers gofast confirmé","en cours",new Date()));

//            commandeRepository.save(new Commande(null, "omar ghorbel","xx","white","sfax rte tunis km4","55390287",6.0,"confirmé","livraison vers gofast confirmé","en cours"));
//            commandeRepository.save(new Commande(null, "omar ghorbel","xx","white","kairaouina hay xy rte abc","21213605",6.0,"confirmé","livraison vers gofast confirmé","en cours"));













        };

    }
    @Bean
    BCryptPasswordEncoder getBCPE(){
        return  new BCryptPasswordEncoder();
    }


}