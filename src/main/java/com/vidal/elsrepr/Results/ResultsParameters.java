package com.vidal.elsrepr.Results;

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
public class ResultsParameters {
    @Id
    @GeneratedValue
    private Integer id;
    
    @Basic
    private String carpeta;
    private String archivo;
}
