package com.example.SpringBootElasticsearch.service;

import com.example.SpringBootElasticsearch.base.response.ScrollResponse;
import org.elasticsearch.search.SearchHit;

import java.io.IOException;

public interface ElasticSearchService {

    public ScrollResponse getResults() throws IOException;

    public ScrollResponse getNextResults(String scrollId) throws IOException;
}
