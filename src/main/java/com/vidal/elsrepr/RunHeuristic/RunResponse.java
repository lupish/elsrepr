package com.vidal.elsrepr.RunHeuristic;

import jakarta.persistence.Basic;
import lombok.Data;

@Data
public class RunResponse {
    @Basic
    private String salida;
}
