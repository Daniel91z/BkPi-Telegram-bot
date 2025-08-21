package com.bkpi.services;

import com.bkpi.configuration.BotConfig;
import com.bkpi.dto.PingLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Slf4j
@Component
public class PingLogService {

    private final BotConfig botConfig;
    private final ObjectMapper objectMapper;
    private File pingLog;

    public PingLogService(BotConfig botConfig, ObjectMapper objectMapper) {
        this.botConfig = botConfig;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @PostConstruct
    public void init() {
        pingLog = createFile();
        writePingLog("1.1.1");
    }

    public void writePingLog(String ping) throws IOException {
        objectMapper.writeValue(pingLog, new PingLog(ping));
    }

    @SneakyThrows
    public String getPing() {
        PingLog file = objectMapper.readValue(pingLog, PingLog.class);
        return file.getPing();
    }

    @SneakyThrows
    public String checkPingUpdate(String newPing) {
        PingLog file = objectMapper.readValue(pingLog, PingLog.class);
        if (Objects.nonNull(file) && !newPing.equals(file.getPing())) {
            log.info("New ping detected: {}", newPing);
            writePingLog(newPing);
            return newPing;
        } else {
            log.info("Ping is still: {}", file.getPing());
            return file.getPing();
        }
    }

    @SneakyThrows
    private File createFile() {
        File file = new File(botConfig.getPinglog());
        Files.createDirectories(file.getParentFile().toPath());
        boolean created = file.createNewFile();
        log.info("File {} created", created ? "was" : "wasn't");
        return file;
    }

}