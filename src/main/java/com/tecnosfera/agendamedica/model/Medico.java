package com.tecnosfera.agendamedica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "medico")
public class Medico extends Usuario {

    @Column(nullable = false)
    private String CRM;

    @Column(nullable = false)
    private String especialidade;
}
