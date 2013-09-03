/*
 * @author swatisinghi
 */

package com.es.client;

import com.es.ElasticSearch;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.util.Map;


public class DocumentSearcher {

    public DocumentSearcher() {
    }

    public SearchHit [] searchDocumentByAttribute(String index, String type, String key, String value) {


        SearchRequestBuilder searchRequestBuilder = ElasticSearch.client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setSize(100)
                .setQuery(QueryBuilders.fieldQuery(key, value));

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        return searchResponse.getHits().getHits();

    }

    public Map<String, Object> searchDocumentById(String index, String type, String id) {


        GetRequestBuilder getRequestBuilder = ElasticSearch.client.prepareGet(index, type, id);
        GetResponse getResponse = getRequestBuilder.execute().actionGet();

        return getResponse.getSource();

    }

}
