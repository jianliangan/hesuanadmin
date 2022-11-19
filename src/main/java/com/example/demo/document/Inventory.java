package com.example.demo.document;

import com.example.demo.entity.Base;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(collection = "inventory")
@Data
public class Inventory extends Base {
    @Field
    private String id;
    @Field
    private String type;
    @Field
    private String subject;
    @Field
    private String code;
    @Field
    private String category;
    @Field
    private String name;
    @Field
    private String distinction;
    @Field
    private String unit;
    @Field
    private Double have;
    @Field
    private Double workAmount;
    @Field
    private Double costUnitprice;
    @Field
    private Double costSumprice;
    @Field
    private Double costManprice;
    @Field
    private Double costMaterialsprice;
    @Field
    private Double costMechanicsprice;
    @Field
    private Double costDeviceprice;
    @Field
    private Double costSubpackageprice;
    @Field
    private Double manageUnitprice;
    @Field
    private Double profitUnitprice;
    @Field
    private Double manageSumprice;
    @Field
    private Double profitSumprice;


    @Override
    public Object fetchPrimeId() {
        return null;
    }

    @Override
    public void pushPrimeId(Object value) {

    }

    @Override
    public Object fetchParentId() {
        return null;
    }
}
