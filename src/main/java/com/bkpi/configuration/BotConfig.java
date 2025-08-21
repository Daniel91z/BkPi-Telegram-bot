package com.bkpi.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotConfig {

    private String token;
    private String username;
    private String bkpichatid;
    private String pinglog;
    private String path;

}