package com.tecnosfera.agendamedica.model;

import jakarta.persistence.*;

@Entity
@Table(name= "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false)
    private String CPF;

    @Column(nullable = false)
    private String email;
}
