package com.bkpi.services;

import com.bkpi.configuration.BotConfig;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@AllArgsConstructor
public class VideoHandler {

    private final FileDownloader fileDownloader;
    private final BotConfig botConfig;

    public void handleVideoDownload(AbsSender bot, Update update) {
        try {
            log.info("Video detected");
            Video video = update.getMessage().getVideo();
            String fileId = video.getFileId();
            String fileName = "video_" + getLocalDateTime() + getFileExtension(video.getMimeType());
            fileDownloader.downloadFile(fileId, bot, botConfig.getPath(), fileName);
        } catch (TelegramApiException e) {
            log.error("Failed to save video", e);
            reply(bot, update, "Failed to save video");
        }
    }

    @SneakyThrows
    public void reply(AbsSender bot, Update update, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(botConfig.getBkpichatid());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText(text);
        bot.execute(message);
    }

    private String getLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH_mm_ss");
        return now.format(formatter);
    }

    public String getFileExtension(String mimeType) {
        return String.format(".%s", mimeType.substring(mimeType.indexOf('/') + 1));
    }

}