/*
 * @author swatisinghi
 */

package com.es.client;

import com.es.ElasticSearch;
import org.elasticsearch.action.bulk.BulkRequestBuilder;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.util.Map;

public class DocumentIndexer {

    public DocumentIndexer() {

    }

    public void indexDocument(Map<String, XContentBuilder> jsonDocs, String index, String type) {

        BulkRequestBuilder bulkRequest = ElasticSearch.client.prepareBulk();

        System.out.println(jsonDocs.size());
        for(String key : jsonDocs.keySet()) {
            bulkRequest.add(ElasticSearch.client.prepareIndex(index, type, key).setSource(jsonDocs.get(key)));
        }

        bulkRequest.execute().actionGet();
    }
}
