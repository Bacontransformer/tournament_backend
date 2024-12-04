package com.ybk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableCaching
@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
public class TournamentApplication {
    public static void main(String[] args) {
        SpringApplication.run(TournamentApplication.class, args);
        log.info("server started");
    }
}
