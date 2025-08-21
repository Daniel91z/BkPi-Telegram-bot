package com.bkpi.services;

import com.bkpi.configuration.BotConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
@AllArgsConstructor
public class FileDownloader {

    private final BotConfig botConfig;

    /**
     * Downloads a file from the Telegram server using the file ID.
     *
     * @param fileId       the file ID provided by Telegram to retrieve the file
     * @param bot          the bot instance to execute API calls
     * @param downloadPath the path where the file should be saved locally
     * @param fileName     the name to assign to the downloaded file
     * @throws TelegramApiException if the download or file saving process fails
     */
    public void downloadFile(String fileId, AbsSender bot, String downloadPath, String fileName) throws TelegramApiException {
        GetFile getFileMethod = new GetFile(fileId);
        File file = bot.execute(getFileMethod);
        String filePath = file.getFilePath();
        String fullUrl = "https://api.telegram.org/file/bot" + botConfig.getToken() + "/" + filePath;
        Path path = Paths.get(downloadPath, fileName);
        try (InputStream in = new BufferedInputStream(new URL(fullUrl).openStream());
             FileOutputStream out = new FileOutputStream(path.toFile())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            log.info("File saved at: {}", botConfig.getPath());
        } catch (Exception e) {
            throw new TelegramApiException("Failed to save file: " + fileId, e);
        }
    }

}