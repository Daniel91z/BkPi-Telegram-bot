package com.bkpi.services;

import com.bkpi.configuration.BotConfig;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@AllArgsConstructor
public class DocumentHandler {

    private final FileDownloader fileDownloader;
    private final BotConfig botConfig;

    public void handleDocumentDownload(AbsSender bot, Update update) {
        try {
            log.info("Document detected");
            Document document = update.getMessage().getDocument();
            String fileId = document.getFileId();
            fileDownloader.downloadFile(fileId, bot, botConfig.getPath(), document.getFileName());
        } catch (TelegramApiException e) {
            log.error("Failed to save document", e);
            reply(bot, update, "Failed to save document");
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

}