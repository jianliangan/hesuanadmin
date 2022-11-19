package com.example.demo.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.core.SolrTemplate;

@Configuration
@ComponentScan
public class SolrConfig {

    @Value("${solr.baseUrl}")
    private String baseUrl;

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient.Builder()
                .withBaseSolrUrl(baseUrl)
                .build();
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient solrClient) throws Exception {
        return new SolrTemplate(solrClient);
    }
}
