package com.es.model;

/**
 * @author swatisinghi
 */

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
public class Product {

    private String genre;
    private String artist;
    private String productId;
    private String productName;

    public Product(String productString) {
        this.parseRecord(productString);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public XContentBuilder productJson() {
        XContentBuilder jsonDoc = null;
        try {
            jsonDoc = jsonBuilder()
                    .startObject()
                    .field("genre", this.genre)
                    .field("artist", this.artist)
                    .field("name", this.productName)
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonDoc;
    }

    private void parseRecord(String productRecord) {
        String[] productAttr = productRecord.split(",");
        this.setGenre(productAttr[0].replaceAll("\"", ""));
        this.setArtist(productAttr[1].replaceAll("\"", ""));
        this.setProductName(productAttr[2].replaceAll("\"", ""));
        this.setProductId(productAttr[3].replaceAll("\\s", ""));
    }

}
