package com.vidal.elsrepr.Results;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/results")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ResultsController {
    private final ResultsService resultsService;

    @GetMapping(path="/ping")
    public String ping() {
        return resultsService.ping();
    }

    @PostMapping(path="/list")
    public List<ResultsResponse> listResults(@RequestBody ResultsParameters p) {
        return resultsService.listResults(p);
    }

    @PostMapping(path="/details")
    public Map<String, List<ResultResponseDetail>> detailsResult(@RequestBody ResultsParameters p) {
        System.out.println(p.getCarpeta());
        System.out.println(p.getArchivo());

        return resultsService.readFile(p.getCarpeta(), p.getArchivo());
    }


}
