package com.bkpi.services;

import com.bkpi.configuration.BotConfig;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@Getter
//@AllArgsConstructor
public class PhotoHandler {

    private boolean isFailed;
    private final FileDownloader fileDownloader;
    private final BotConfig botConfig;

    public PhotoHandler(FileDownloader fileDownloader, BotConfig botConfig) {
        this.fileDownloader = fileDownloader;
        this.botConfig = botConfig;
        this.isFailed = false;
    }

    public void handlePhotoDownload(AbsSender bot, Update update) {
        try {
            log.info("Photo detected");
            List<PhotoSize> photos = update.getMessage().getPhoto();
            PhotoSize photo = photos.get(photos.size() - 1);
            String fileId = photo.getFileId();
            GetFile getFileMethod = new GetFile(fileId);
            File file = bot.execute(getFileMethod);
            String fileName = "photo_" + getLocalDateTime() + getFileExtensionFromPath(file.getFilePath());
            fileDownloader.downloadFile(fileId, bot, botConfig.getPath(), fileName);
            isFailed = false;
        } catch (TelegramApiException e) {
            log.error("Failed to save photo", e);
            saveFailedReply(bot, update);
        }
    }

    @SneakyThrows
    private void saveFailedReply(AbsSender bot, Update update) {
        isFailed = true;
        SendMessage message = new SendMessage();
        message.setChatId(botConfig.getBkpichatid());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText("Failed to save photo");
        bot.execute(message);
    }

    private String getLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH_mm_ss");
        return now.format(formatter);
    }

    private String getFileExtensionFromPath(String filePath) {
        String fileName = Paths.get(filePath).getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return String.format(".%s", (dotIndex > 0) ? fileName.substring(dotIndex + 1) : "");
    }

}