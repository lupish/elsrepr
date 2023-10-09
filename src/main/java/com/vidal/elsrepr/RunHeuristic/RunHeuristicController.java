package com.vidal.elsrepr.RunHeuristic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;



import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/heuristic")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Permitir solicitudes desde http://localhost:3000
public class RunHeuristicController {

    private final RunHeuristicService heuristicService;
    
    @PostMapping("/hello")
    public String helloWorld(@RequestBody RunParameters p) {
        return heuristicService.hello(p);
    }

    @PostMapping(path="/run")
    public ResponseEntity<RunResponse> byeWorld(@RequestBody RunParameters p) {
        RunResponse resp = new RunResponse();

        String output = heuristicService.run(p);
        resp.setSalida(output);
        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<RunResponse> r = new ResponseEntity<>(resp,headers,HttpStatus.OK);

        return r;
        
    }


}
