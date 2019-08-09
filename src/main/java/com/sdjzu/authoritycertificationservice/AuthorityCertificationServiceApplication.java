package com.sdjzu.authoritycertificationservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@MapperScan("com.sdjzu.authoritycertificationservice.dao")
@EnableCaching//开启全局缓存
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800)//开启session共享，session有效期30分钟
public class AuthorityCertificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorityCertificationServiceApplication.class, args);
	}
}
