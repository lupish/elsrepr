package com.vidal.elsrepr.Results;

import java.util.Date;

import jakarta.persistence.Basic;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultsResponse {
    @Basic
    private String nombreArchivo;
    private Date fechaCreacion;
    private String version;
    private Double tasaRecovery;
}
