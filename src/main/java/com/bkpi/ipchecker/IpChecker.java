package com.bkpi.ipchecker;

import com.bkpi.services.PingLogService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Component
public class IpChecker {

    private final PingLogService pingLogService;
    private final RestTemplate restTemplate;

    public IpChecker(RestTemplateBuilder restTemplateBuilder, PingLogService pingLogService) {
        this.restTemplate = restTemplateBuilder.build();
        this.pingLogService = pingLogService;
    }

    public String getIp() {
        String previousIp = pingLogService.getPing();
        String currentIp = getCurrentIp();
        if (Objects.nonNull(currentIp) && !currentIp.equals(previousIp)) {
            return currentIp;
        } else {
            return previousIp;
        }
    }

    @SneakyThrows
    public String getCurrentIp() {
        try {
            String ipAddress = restTemplate.getForObject("https://checkip.amazonaws.com/", String.class);
            return ipAddress != null ? ipAddress.trim() : null;
        } catch (RestClientException e) {
            log.info("Waiting for 5 minutes for the next request");
            Thread.sleep(300000);
            log.info("5 minutes have passed, trying again");
            return "Unable to get ip";
        }
    }

}