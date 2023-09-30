package com.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.pojo.Employee;
import com.example.pojo.Employees;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class ESController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostMapping("/createIndex")
    public void testCreateIndex(){
        //创建索引
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("employee_index"));
        if (indexOperations.exists()) {
            log.info("索引已经存在");
        }else {
            //创建索引
            indexOperations.create();
        }
    }
    @DeleteMapping("/deleteIndex")
    public void testDeleteIndex(){
        //删除索引
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of("employee_index"));
        indexOperations.delete();
    }

    @GetMapping("/queryDocument")
    public void testQueryDocument(){
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //查询
        builder.withQuery(QueryBuilders.matchQuery("address","公园"));
        // 设置分页信息
        builder.withPageable(PageRequest.of(0, 5));
        // 设置排序
        builder.withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC));

        SearchHits<Employees> search = elasticsearchRestTemplate.search(builder.build(), Employees.class);
        List<SearchHit<Employees>> searchHits = search.getSearchHits();
        for (SearchHit hit: searchHits){
            log.info("返回结果："+hit.toString());
        }

    }


    @PostMapping("/insertBatch")
    public void testInsertBatch(){
        List<Employees> employees = new ArrayList<>();
        employees.add(new Employees(2L,"张三",1,25,"广州天河公园","java developer"));
        employees.add(new Employees(3L,"李四",1,28,"广州荔湾大厦","java assistant"));
        employees.add(new Employees(4L,"小红",0,26,"广州白云山公园","php developer"));

        List<IndexQuery> queries = new ArrayList<>();
        for (Employees employee : employees) {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(employee.getId().toString());
            String json = JSONObject.toJSONString(employee);
            indexQuery.setSource(json);
            queries.add(indexQuery);
        }
        //bulk批量插入
//        elasticsearchRestTemplate.bulkIndex(queries,Employees.class);
        //bulk批量插入
        elasticsearchRestTemplate.bulkIndex(queries, IndexCoordinates.of("employee_index"));
    }
}
