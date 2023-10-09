package com.vidal.elsrepr.RunHeuristic;

import org.springframework.stereotype.Service;

import Heuristic.TSSolver;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RunHeuristicService {
    
    public String hello(RunParameters p) {
        return "Running version " + p.getVersion() + " for " + p.getCantClientes() + " clients and " + p.getCantPeriodos() + " periods";
    }

    public String run(RunParameters p) {
        String output = "";
        try {
            output = TSSolver.apiMasterEjecucion(p);
            return output;
        } catch (Exception e) {
            output = "Error";
            e.printStackTrace();
        }

        return output;
    }

}
