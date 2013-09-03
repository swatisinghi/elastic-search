package com.es.model;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Random;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author swatisinghi
 */
public class Query {

    private String query;
    private String productId;
    private String queryId;
    private String timestamp;

    public Query(String queryRecord) {
        this.parseQuery(queryRecord);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String getRandomQueryId() {
        long rand = new Random().nextLong();
        if(rand < 0) {
            rand *= -1;
        }
        return String.valueOf(rand);

    }

    public XContentBuilder queryJson() {
        XContentBuilder jsonDoc = null;
        try {
            jsonDoc = jsonBuilder()
                    .startObject()
                    .field("query", this.query)
                    .field("timestamp", this.timestamp)
                    .field("productId", this.productId)
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonDoc;
    }

    private void parseQuery(String productRecord) {

        String[] queryAttr = productRecord.split(",");
        this.setQuery(queryAttr[0].replaceAll("\"", ""));
        this.setTimestamp(queryAttr[1].replaceAll("\\s", "").replaceAll("\"", ""));
        this.setProductId(queryAttr[2].replaceAll("\\s", ""));
        this.setQueryId(getRandomQueryId());

    }
}
