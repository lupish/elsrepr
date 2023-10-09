package com.vidal.elsrepr.RunHeuristic;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RunParameters {

    @Id
    @GeneratedValue
    private Integer id;

    @Basic
    private Integer version;
    private Integer cantClientes;
    private Integer cantPeriodos;
    private Float tasaRecovery;
    private String carpetaEntrada;
    private Integer esMasivo;
    private Integer config;
    private String configArchivo;
    private Integer cantIteraciones;
    private Integer cantSaltos;
    private Integer estrategiaSaltos;
    
}
