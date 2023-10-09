package com.vidal.elsrepr.Results;

import jakarta.persistence.Basic;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultResponseDetail {
    @Basic
    private String tipoCosto;
    private Double valor;
}
