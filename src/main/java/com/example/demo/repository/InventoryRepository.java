package com.example.demo.repository;

import com.example.demo.document.Inventory;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InventoryRepository {

    class ResData {
        Long numFound;
        List data;

        public Long getNumFound() {
            return numFound;
        }

        public List getData() {
            return data;
        }

        public void setData(List data) {
            this.data = data;
        }

        public void setNumFound(Long numFound) {
            this.numFound = numFound;
        }


    }

    QueryResponse getList(Map<String, String> qparams);

    UpdateResponse save(Inventory inventory);

    UpdateResponse delete(Inventory inventory);
//    List<Inventory> findByDocTitleEndsWith(String title); // find documents whose docTitle ends with specified string
//
//    List<Inventory> findByDocTitleStartsWith(String title); // find documents whose docTitle starts with specified string
//
//    List<Inventory> findByDocTypeEndsWith(String type); //find documents whose docType ends with specified string
//
//    List<Inventory> findByDocTypeStartsWith(String type);//find documents whose docType start with specified string
}
