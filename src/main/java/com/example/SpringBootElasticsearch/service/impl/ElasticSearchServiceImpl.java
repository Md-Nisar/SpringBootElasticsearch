package com.example.SpringBootElasticsearch.service.impl;

import com.example.SpringBootElasticsearch.base.params.Scroll;
import com.example.SpringBootElasticsearch.base.response.ScrollResponse;
import com.example.SpringBootElasticsearch.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private final RestHighLevelClient client;

    @Autowired
    public ElasticSearchServiceImpl(RestHighLevelClient client) {
        this.client = client;
    }

    @Override
    public ScrollResponse getResults() throws IOException {
        log.info("Starting Elasticsearch....");

        SearchRequest searchRequest = new SearchRequest("employee");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.size(Scroll.SIZE.value);
        log.info("SCROLL SIZE: " + Scroll.SIZE.value);

        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueMinutes(((Scroll.INTERVAL.value))));
        log.info("SCROLL INTERVAL: " + Scroll.INTERVAL.value + "MINS");

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        String scrollId = searchResponse.getScrollId();
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        log.info("---------HITS------------");
        log.info("NO OF HITS: " + searchHits.length);
        for (SearchHit hit : searchHits) {
            log.info(String.valueOf(hit));
        }

        ScrollResponse scrollResponse = new ScrollResponse(scrollId, searchHits);
        return scrollResponse;
    }

    @Override
    public ScrollResponse getNextResults(String scrollId) throws IOException {

        SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
        scrollRequest.scroll(TimeValue.timeValueMinutes(Scroll.INTERVAL.value));
        log.info("SCROLL INTERVAL: " + Scroll.INTERVAL.value);
        SearchResponse searchScrollResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
        scrollId = searchScrollResponse.getScrollId();
        SearchHits hits = searchScrollResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        log.info("---------HITS------------");
        log.info("NO OF HITS: " + searchHits.length);
        for (SearchHit hit : searchHits) {
            log.info(String.valueOf(hit));
        }
        ScrollResponse scrollResponse = new ScrollResponse(scrollId, searchHits);
        return scrollResponse;
    }
}
