package com.nhnacademy.servicereview_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServiceReviewV2Application {

    public static void main(String[] args) {
        SpringApplication.run(ServiceReviewV2Application.class, args);
    }

}
