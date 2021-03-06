package com.omarghorbel.storecrud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    public Product(String name, String type, byte[] picByte) {
        this.name = name;
        this.type = type;
        this.picByte = picByte;
    }



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//	@Column(name = "nom")
//	private String nom;
//
//
//	@Column(name = "category")
//	private String category;
//
//	@Column(name = "prix")
//	private Double prix;

    @Column(name = "name", unique=true)
    private String name;

    @Column(name = "type")
    private String type;



    @Column(name = "picByte", columnDefinition = "LONGBLOB")
    private byte[] picByte;



}