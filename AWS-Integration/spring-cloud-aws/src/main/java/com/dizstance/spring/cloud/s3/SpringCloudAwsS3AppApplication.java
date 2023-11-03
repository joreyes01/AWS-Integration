package com.dizstance.spring.cloud.s3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AutoConfiguration
public class SpringCloudAwsS3AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudAwsS3AppApplication.class, args);
	}

}
