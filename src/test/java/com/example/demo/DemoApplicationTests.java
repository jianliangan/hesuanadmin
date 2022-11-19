package com.example.demo;

import com.example.demo.document.Inventory;
import com.example.demo.service.IProjectService;
import com.example.demo.service.inventory.IInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
@Slf4j
public class DemoApplicationTests {

    @Autowired
    private IProjectService projectService;
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private IInventoryService inventoryService;

    @Test
    public void projectList() {
        //  List<Project> proList = projectService.list();
        // proList.forEach(System.out::println);
    }

    @Test
    public void getDataSourceConnect() {
        // SqlSession session = getSqlSession();
        //  Connection con = session.getConnection();
        //  System.out.println(con.toString());
    }

    public SqlSession getSqlSession() {
        return SqlSessionUtils.getSqlSession(sqlSessionTemplate.getSqlSessionFactory(),
                sqlSessionTemplate.getExecutorType(), sqlSessionTemplate.getPersistenceExceptionTranslator());
    }


    public void closeSqlSession(SqlSession session) {
        SqlSessionUtils.closeSqlSession(session, sqlSessionTemplate.getSqlSessionFactory());
    }

    /**
     * solr test
     */
    @Autowired
    private SolrClient solrClient;

    @Test
    public void addOrUpdateTest() throws IOException, SolrServerException {
        //solr里面操作的记录，document
        SolrInputDocument document = new SolrInputDocument();
        //需要有唯一的标识
        document.setField("id", "11");
        document.setField("product_name", "thinkpad2019");
        document.setField("product_price", "9999");
        document.setField("sale_point", "速度快，现存非常棒，你值得拥有！！");
        document.setField("product_images", "无");

        //提交
        solrClient.add(document);
        solrClient.commit();
    }

    @Test
    public void queryData() throws IOException, SolrServerException {
        //组装查询条件
        SolrQuery queryCondition = new SolrQuery();
        //查询所有
        //queryCondition.setQuery("*:*");
        //设置查询条件
        queryCondition.setQuery("sale_point:速度很快"); //分词之后在匹配
        //根据查询条件返回查询的结果集
        QueryResponse response = solrClient.query(queryCondition);

        SolrDocumentList results = response.getResults();
        for (SolrDocument document : results) {
            System.out.print(document.get("id"));
            System.out.println(document.get("product_name"));
            System.out.println(document.get("sale_point"));
        }
    }

    @Test
    public void deleteData() throws IOException, SolrServerException {
        //精确匹配是按照id
        //分词匹配
        solrClient.deleteByQuery("sale_point:速度很快");
        solrClient.commit();
    }

    @Test
    public void SpringDataSolrAdd() {
        //精确匹配是按照id
        //分词匹配
        Inventory inventory = new Inventory();
        inventory.setId("123123");
        inventory.setCode("dfdff");
        inventory.setSubject("dddd");
        inventory.setType("yusuan");
        inventory.setUnit("danwei ");
        inventory.setWorkAmount(34.5);
        inventory.setCategory("fenlei");
        inventory.setHave(10D);
        inventory.setId("fffff");
        inventory.setName("namename");
        inventoryService.save(inventory);
    }

    @Test
    public void SpringDataSolrList() {
        //精确匹配是按照id
        //分词匹配
        QueryResponse objectMap = inventoryService.getList(null);

        log.info("ddddd{}", objectMap.toString());

    }
}
