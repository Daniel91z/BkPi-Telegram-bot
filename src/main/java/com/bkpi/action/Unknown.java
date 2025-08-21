package com.bkpi.action;

import com.bkpi.configuration.BotConfig;
import com.bkpi.enumerator.ActionEnum;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
@AllArgsConstructor
public class Unknown extends Action {

    private final BotConfig botConfig;

    @Override
    public ActionEnum getActionType() {
        return ActionEnum.UNKNOWN;
    }

   /* @Override
    public void execute(AbsSender bot, Update update) {
        reply(bot, update);
    }*/

    @Override
    @SneakyThrows
    protected void reply(AbsSender bot, Update update) {
        log.info("Unknown action");
        SendMessage message = new SendMessage();
        message.setChatId(botConfig.getBkpichatid());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText("Unknown action");
        bot.execute(message);
    }

}