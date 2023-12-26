package com.vidal.elsrepr.RunHeuristic;

import jakarta.persistence.Basic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunParameters {
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
