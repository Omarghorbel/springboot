package com.omarghorbel.storecrud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String nomClient;
    private  String nomFb;
    private  String ville;

    private String imageProduit;
    private String imageProduita;
    private String imageRetour;
    private String imageAvantLivraison;


    private String adress;
    private String telephone;
    private String commentaireLivraison;
    private String commentaireCollier;
    private double prixaveclivraison;
    private double prixsanslivraison;
    private  String receptionUsine;
    private String goFast;
    private String etat;
    private Date datedecreation;
    private Date datedereceptionusine;
    private Date datelivreversgofast;
    private Date etatFinal;
    private String utilisateur;
    private String notes;
    private double prixusineinitial;
    private String etatpaimentarticleusine;
    private Date datepaimentarticleusine;

    private double prixusineretour;
    private String etatpaimentprixusineretour;
    private Date datepaimentarticleusineretour;
    private String etatfinalcloture;






}
