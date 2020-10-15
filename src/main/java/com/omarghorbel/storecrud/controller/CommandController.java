package com.omarghorbel.storecrud.controller;

import com.omarghorbel.storecrud.dao.CommandeRepository;
import com.omarghorbel.storecrud.entities.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class CommandController {

    @Autowired
    private CommandeRepository commandeRepository;

//    @GetMapping("/list")
//    public Page<Commande> showPage (Model model , @RequestParam(defaultValue = "0") int page){
//        model.addAttribute("data", commandeRepository.findAll(new PageRequest(page, 4)));
//        return commandeRepository.findAll(new PageRequest(page, 4));
//
//    }

    @GetMapping("/commandex")
    public String commandex(Model model,
                           @RequestParam(name="page", defaultValue = "0")int page,
                           @RequestParam(name = "size", defaultValue = "5")int size){
        Page<Commande> Commandes= commandeRepository.findAll(PageRequest.of(page,size));
        model.addAttribute("listcommandes",Commandes);
        return "commandex";

    }

    @GetMapping("/list")
    public Page<Commande> showPage (@RequestParam(name="page", defaultValue = "2")int page,
                                    @RequestParam(name = "size", defaultValue = "5")int size){
        return  commandeRepository.findAllx(PageRequest.of(page,size));

    }

}

