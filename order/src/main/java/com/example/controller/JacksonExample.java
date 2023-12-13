package com.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Map;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JacksonExample {

private static final ObjectMapper objectMapper = new ObjectMapper();

  private static final String exampleJson = "{\n"
      + "  \"name\": \"张三\",\n"
      + "  \"age\": 18,\n"
      + "  \"address\": {\n"
      + "    \"province\": \"北京\",\n"
      + "    \"city\": \"北京\",\n"
      + "    \"district\": \"海淀\"\n"
      + "  },\n"
      + "  \"hobby\": [\n"
      + "    \"篮球\",\n"
      + "    \"足球\",\n"
      + "    \"乒乓球\"\n"
      + "  ]\n"
      + "}";

  public static void main(String[] args) throws JsonProcessingException {
    // 将json字符串转换为Map
    jsonToMap();
    // 使用jackson处理树模型
    treeModel();
    // jsonNode转换为java对象
    jsonNodeToJavaObject();
    // 使用jsonnode构建json
    buildJson();
    // 使用路径获取JsonNode字段
    getFieldByPath();
  }

  public static void jsonToMap() throws JsonProcessingException {
    Map<String, Object> map = objectMapper.readValue(exampleJson, new TypeReference<Map<String, Object>>() {
    });
    System.out.println(map);
  }

  public static void treeModel() throws JsonProcessingException {

    JsonNode jsonNode = objectMapper.readTree(exampleJson);
    String text = jsonNode.get("address").get("city").asText();
    System.out.println(text);
    // 获取第一个爱好
    String hobby = jsonNode.get("hobby").get(0).asText();
    System.out.println(hobby);
  }

  // jsonNode转换为java对象
  public static void jsonNodeToJavaObject() throws JsonProcessingException {
    JsonNode jsonNode = objectMapper.readTree(exampleJson);
    // 将jsonNode转换为java对象
    User user = objectMapper.treeToValue(jsonNode, User.class);
    System.out.println(user);
  }
  
  // 使用jsonnode构建json
  public static void buildJson() throws JsonProcessingException {
    // 构建json
    ObjectNode jsonNode = objectMapper.createObjectNode();
    jsonNode.put("name", "张三");
    jsonNode.put("age", 18);
    // 构建address
    ObjectNode address = objectMapper.createObjectNode();
    address.put("province", "北京");
    address.put("city", "北京");
    address.put("district", "海淀");
    jsonNode.set("address", address);
    // 构建hobby
    ObjectNode hobby = objectMapper.createObjectNode();
    hobby.put("hobby", "篮球");
    hobby.put("hobby", "足球");
    hobby.put("hobby", "乒乓球");
    jsonNode.set("hobby", hobby);

    System.out.println(jsonNode);
  }

  // 使用路径获取JsonNode字段
  public static void getFieldByPath() throws JsonProcessingException {
    JsonNode jsonNode = objectMapper.readTree(exampleJson);
    JsonNode city = jsonNode.at("/address/city");
    System.out.println(city.asText());
    String text = jsonNode.path("address").path("city").asText();
    System.out.println(text);
    // 通过路径获取数组
    JsonNode hobby = jsonNode.at("/hobby");
    System.out.println(hobby);
    // 通过路径获取数组中的第一个元素
    JsonNode first = jsonNode.at("/hobby/0");
    System.out.println(first);
  }








  @Data
  static class User{
    private String name;
    private Integer age;
    private Address address;
    private String[] hobby;
  }
  static class Address{
    private String province;
    private String city;
    private String district;
  }


}
