package com.bkpi;

import com.bkpi.configuration.BotConfig;
import com.bkpi.handler.ActionHandler;
import com.bkpi.services.PhotoHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@AllArgsConstructor
public class BkPiBot extends TelegramLongPollingBot {

    private final ActionHandler actionHandler;
    private final BotConfig botConfig;
    private final PhotoHandler photoHandler;

    @Override
    public String getBotUsername() {
        return botConfig.getUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @SneakyThrows
    public void onUpdateReceived(Update update) {
        actionHandler.execute(this, update);
    }

}