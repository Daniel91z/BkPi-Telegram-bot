package com.bkpi.action;

import com.bkpi.configuration.BotConfig;
import com.bkpi.enumerator.ActionEnum;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@Service
@AllArgsConstructor
public class Ping extends Action {

    private final BotConfig botConfig;

    @Override
    public ActionEnum getActionType() {
        return ActionEnum.PING;
    }

    @Override
    @SneakyThrows
    public void reply(AbsSender bot, Update update) {
        log.info("Text detected");
        SendMessage message = new SendMessage();
        message.setChatId(botConfig.getBkpichatid());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText("Pong");
        bot.execute(message);
    }

}