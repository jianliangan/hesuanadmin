package com.example.demo.repository.impl;

import com.example.demo.document.Inventory;
import com.example.demo.repository.InventoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Repository
public class InventoryRepositoryImpl implements InventoryRepository {
    @Resource
    private SolrClient solrClient;

    @Override
    public QueryResponse getList(Map<String, String> qparams) {
        SolrQuery solrQuery = new SolrQuery();
        String query = "";
        for (Map.Entry entry : qparams.entrySet()) {
            query += entry.getKey() + ":" + ClientUtils.escapeQueryChars((String) entry.getValue()) + " AND ";
        }
        if (query.length() > 1) {
            query = query.substring(0, query.length() - (" AND ").length());
        }
        if (query.length() == 0) {
            query = "*:*";
        }
        solrQuery.setQuery(query);

        // solrQuery.setHighlight(true);
        // solrQuery.addHighlightField("id");
        // solrQuery.setHighlightSimplePre("<font color='red'>");
        // solrQuery.setHighlightSimplePost("</font>");
        solrQuery.setStart(0);
        solrQuery.setRows(10);
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrClient.query("inventory", solrQuery);

        } catch (Exception e) {
            log.error("{}", e.toString());
            return null;
        }
        return queryResponse;
        //普通结果
        // List<Inventory> inventoryList = queryResponse.getBeans(Inventory.class);
        //高亮结果
        //  Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        //将普通结果中的字段name改成高亮后的
        //  for (Inventory inventory : inventoryList) {
        //高亮结果
        //      String name = highlighting.get(inventory.getId()).get("name").get(0);
        //      inventory.setName(name);
        //  }

//        Map<String, Object> objectMap = new HashMap<>();
//        objectMap.put("list", inventoryList);
//        objectMap.put("numFound", numFound);
        //return null;
    }

    @Override
    public UpdateResponse save(Inventory inventory) {
        UpdateResponse updateResponse = null;
        try {
            updateResponse = solrClient.addBean("inventory", inventory);
            solrClient.commit("inventory");
        } catch (Exception e) {
            log.error("{}", e.toString());
        }

        return updateResponse;
    }

    @Override
    public UpdateResponse delete(Inventory inventory) {
        UpdateResponse updateResponse = null;
        try {
            updateResponse = solrClient.deleteByQuery("inventory", "name:aaaa");
            solrClient.commit();
        } catch (Exception e) {
            log.error("{}", e.toString());
        }
        return updateResponse;
    }
}
