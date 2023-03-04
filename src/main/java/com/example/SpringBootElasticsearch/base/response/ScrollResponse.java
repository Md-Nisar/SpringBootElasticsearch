package com.example.SpringBootElasticsearch.base.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.search.SearchHit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScrollResponse {

    private String scrollId;
    private SearchHit[] searchHits;



}
