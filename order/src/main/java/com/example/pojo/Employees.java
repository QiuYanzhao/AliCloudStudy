package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@Document(indexName = "employee_index")
public class Employees {
    @Id
    private Long id;
    /**
     * type:字段类型
     * type= FieldType.Keyword 表示不分词
     */
    @Field(type= FieldType.Keyword)
    private String name;
    private int sex;
    private int age;
    /**
     * type= FieldType.Text 表示分词
     * analyzer:指定分词器
     * ik_max_word:会将文本做最细粒度的拆分
     * ik_smart:会做最粗粒度的拆分
     * index:是否索引
     * store:是否存储
     * searchAnalyzer:指定搜索时使用的分词器
     */
    @Field(type= FieldType.Text,analyzer="ik_max_word")
    private String address;
    private String remark;
}
