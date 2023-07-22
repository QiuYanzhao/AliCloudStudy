package com.example.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class FeignFilter implements RequestInterceptor {

    @Override public void apply(RequestTemplate requestTemplate) {
        // 在这里做feign调用之前的拦截器的逻辑
        // 注意：需要在配置文件中为feign客户端注册此拦截器
        System.out.println("feign filter");
    }
}

/**
 * 第二种实现feign拦截器的方式
 * 这种方式需要在feign接口的注解上指定拦截器：
 * @FeignClient(configuration = OsFeignApiFilter.class)
 *
 *  @Configuration
 *  public class OsFeignApiFilter {
 *
 *     @Bean
 *     public RequestInterceptor requestInterceptor() {
 *         return template -> {
 *             String host = HostHolder.getIpAddress();
 *             template.header("X-Forwarded-For", host);
 *             HostHolder.removeIpAddress();
 *         };
 *     }
 *
 *
 * }
 */
