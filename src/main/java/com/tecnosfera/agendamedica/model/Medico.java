package com.tecnosfera.agendamedica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "medico")
public class Medico extends Usuario {

    @Column(nullable = false)
    private String crm;

    @Column(nullable = false)
    private String especialidade;
}
