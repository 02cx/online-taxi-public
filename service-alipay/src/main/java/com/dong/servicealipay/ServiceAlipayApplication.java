package com.dong.servicealipay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceAlipayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAlipayApplication.class,args);
    }
}
