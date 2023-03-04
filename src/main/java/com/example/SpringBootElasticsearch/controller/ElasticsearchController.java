package com.example.SpringBootElasticsearch.controller;

import com.example.SpringBootElasticsearch.base.response.ScrollResponse;
import com.example.SpringBootElasticsearch.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/es")
@Slf4j
public class ElasticsearchController {

    private final ElasticSearchService elasticSearchService;

    @Autowired
    public ElasticsearchController(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }

    @GetMapping("/scroll")
    public ResponseEntity<ScrollResponse> getResults() throws IOException {
        ScrollResponse scrollResponse = elasticSearchService.getResults();
        return ResponseEntity.status(HttpStatus.OK).body(scrollResponse);
    }

    @GetMapping("/scroll/{scrollId}")
    public ResponseEntity<ScrollResponse> getNextResults(@PathVariable("scrollId") String scrollId) throws IOException {
      ScrollResponse scrollResponse = elasticSearchService.getNextResults(scrollId);
        return ResponseEntity.status(HttpStatus.OK).body(scrollResponse);
    }

}
