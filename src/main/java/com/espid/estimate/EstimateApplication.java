package com.espid.estimate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.espid" })
@MapperScan(basePackages = { "com.espid" })
@EnableAutoConfiguration
public class EstimateApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstimateApplication.class, args);
	}

}
