package com.tecnosfera.agendamedica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "paciente")
public class Paciente extends Usuario {

    @Column(nullable = false)
    private int idade;
}
