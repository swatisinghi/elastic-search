package com.es;

import com.es.client.DocumentIndexer;
import com.es.client.DocumentSearcher;
import com.es.model.Product;
import com.es.model.Query;
import com.es.model.Type;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.search.SearchHit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 * @author swatisinghi
 */

public class ElasticSearch {

    private Scanner in;
    private HashMap<String, XContentBuilder> products;
    private HashMap<String, XContentBuilder> queries;

    public static Settings settings = ImmutableSettings.settingsBuilder()
            .put("cluster.name", "elasticsearch")
            .put("gateway.local.auto_import_dangled", "no").build();

    public static Client client = nodeBuilder().settings(settings).node().client();

    ElasticSearch() {
        in = new Scanner(System.in);
        products = new HashMap<String, XContentBuilder>();
        queries = new HashMap<String, XContentBuilder>();
    }

    private void populateData() {
        try {
            client.admin().indices().prepareDelete("queries").execute().actionGet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        populateProducts();
        populateQueries();
    }

    private void populateProducts() {

        System.out.println("Enter the path of products data");
        String productDataFile = in.nextLine();
        getFileContents(productDataFile, Type.PRODUCT);
    }


    private void populateQueries() {

        System.out.println("Enter the path of queries data");
        String queryFile = in.nextLine();
        getFileContents(queryFile, Type.QUERY);

    }

    private void indexItem(HashMap<String, XContentBuilder> items, String index, String type) {

        DocumentIndexer indexer = new DocumentIndexer();
        indexer.indexDocument(items, index, type);

    }

    private void getFileContents(String file, Type type) {
        BufferedReader br = null;
        try {
            String currentLine;
            br = new BufferedReader(new FileReader(file));
            while ((currentLine = br.readLine()) != null) {
                for (int i = 100; i >= 0; i--) {
                    if (type == Type.PRODUCT) {
                        Product product = new Product(currentLine);
                        products.put(product.getProductId(), product.productJson());

                    } else if (type == Type.QUERY) {
                        Query query = new Query(currentLine);
                        queries.put(query.getQueryId(), query.queryJson());
                    }
                }
                if (type == Type.PRODUCT) {
                    indexItem(products, "products", "product");
                    products.clear();
                } else if (type == Type.QUERY) {
                    indexItem(queries, "queries", "query");
                    queries.clear();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void searchData() {
        System.out.println("Enter a search query");
        String query = in.nextLine();
        DocumentSearcher searcher = new DocumentSearcher();
        SearchHit[] queryHits = searcher.searchDocumentByAttribute("queries", "query", "query", query);
        List<Map<String, Object>> products = new ArrayList<Map<String, Object>>();
        for (SearchHit hit : queryHits) {
            products.add(searcher.searchDocumentById("products", "product", hit.getSource().get("productId").toString()));
        }

        displayData(products);

    }

    private void displayData(List<Map<String, Object>> products) {
        Iterator iter = products.iterator();
        while (iter.hasNext()) {
            Object product = iter.next();
            System.out.println(product.toString());
        }
    }

    public void menu() {

        System.out.println("Menu");
        System.out.println("1. Populate Data");
        System.out.println("2. Search Data");
        System.out.println("Q. Quit");
        String input = in.nextLine();
        if (input.equals("1"))
            populateData();
        else if (input.equals("2"))
            searchData();
        else if (input.equals("Q"))
            System.exit(0);
        else
            menu();

    }

    public static void main(String args[]) {

        ElasticSearch elasticSearch = new ElasticSearch();
        elasticSearch.menu();
    }
}
