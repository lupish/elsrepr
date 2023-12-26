package com.vidal.elsrepr.Results;

import jakarta.persistence.Basic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultsParameters {
    @Basic
    private String carpeta;
    private String archivo;
}
