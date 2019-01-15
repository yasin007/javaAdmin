package com.yangyi.resume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import com.yangyi.resume.common.utils.SpringContextHolder;

@SpringBootApplication
@EnableTransactionManagement
@EnableWebSocketMessageBroker
public class ResumeApplication {


    public static void main(String[] args) {
        SpringApplication.run(ResumeApplication.class, args);
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}

