elastic-search
==============
A small maven project using elastic search for indexing and searching products and their related queries.

flexible and powerful open source, distributed real-time search and analytics engine for the cloud
http://www.elasticsearch.org/

About the project
=================

We have 2 files, one containing unique rows which are unique products that an eCommerce site is currently selling on their site.
Here are the list of attributes per product:

productId
productName
genre
artist

Second containing the list of queries, each row has the query and the product that was clicked as a result of this. Each row contains 
query
productId
timestamp

The project allows you to create indicies for these products and queries in elastic search and enables you to search relevant products with a given query as an input.
