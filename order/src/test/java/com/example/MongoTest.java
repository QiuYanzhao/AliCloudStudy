package com.example;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建集合
     */
    @Test
    public void testCollection() {
        boolean exists = mongoTemplate.collectionExists("emp");
        if (exists) {
            //删除集合
            mongoTemplate.dropCollection("emp");
        }
        //创建集合
        mongoTemplate.createCollection("emp");
    }
}
