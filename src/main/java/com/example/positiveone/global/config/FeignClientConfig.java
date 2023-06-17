package com.example.positiveone.global.config;

import com.example.positiveone.PositiveOneApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = PositiveOneApplication.class)
public class FeignClientConfig {
}
