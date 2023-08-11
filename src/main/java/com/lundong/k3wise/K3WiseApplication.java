package com.lundong.k3wise;

import com.lundong.k3wise.event.CustomServletAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class K3WiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(K3WiseApplication.class, args);
	}

	// 注入扩展实例到 IOC 容器
	@Bean
	public CustomServletAdapter getServletAdapter() {
		return new CustomServletAdapter();
	}

}
