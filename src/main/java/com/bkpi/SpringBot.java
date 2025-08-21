package com.bkpi;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableEncryptableProperties
@ComponentScan("com.bkpi")
public class SpringBot {
    public static void main(String[] args) {
        SpringApplication.run(SpringBot.class, args);
    }
}