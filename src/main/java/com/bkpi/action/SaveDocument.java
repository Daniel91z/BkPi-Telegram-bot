package com.bkpi.action;

import com.bkpi.configuration.BotConfig;
import com.bkpi.enumerator.ActionEnum;
import com.bkpi.services.DocumentHandler;
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
public class SaveDocument extends Action {

    private final BotConfig botConfig;
    private final DocumentHandler documentHandler;

    @Override
    public ActionEnum getActionType() {
        return ActionEnum.SAVE_DOCUMENT;
    }

    @Override
    @SneakyThrows
    protected void reply(AbsSender bot, Update update) {
        documentHandler.handleDocumentDownload(bot, update);
        SendMessage message = new SendMessage();
        message.setChatId(botConfig.getBkpichatid());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText("Document saved");
        bot.execute(message);
    }

}