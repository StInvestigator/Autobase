package org.example.autobase;

import lombok.extern.log4j.Log4j2;
import org.example.autobase.service.db.AutobaseDBManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Log4j2
@SpringBootApplication
public class AutobaseApplication {
    @Autowired
    private AutobaseDBManager dbManager;

    public static void main(String[] args) {
        SpringApplication.run(AutobaseApplication.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        log.info("ApplicationRunner has started");
        return args -> {
            dbManager.deleteAllRowsInDB();
            dbManager.fillAllRowsInDB();
        };
    }
}
