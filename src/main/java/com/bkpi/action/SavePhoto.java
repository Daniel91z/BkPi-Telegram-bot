package com.bkpi.action;

import com.bkpi.configuration.BotConfig;
import com.bkpi.enumerator.ActionEnum;
import com.bkpi.services.PhotoHandler;
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
public class SavePhoto extends Action {

    private final BotConfig botConfig;
    private final PhotoHandler photoHandler;

    @Override
    public ActionEnum getActionType() {
        return ActionEnum.SAVE_PHOTO;
    }

    @Override
    @SneakyThrows
    protected void reply(AbsSender bot, Update update) {
        photoHandler.handlePhotoDownload(bot, update);
        if (!photoHandler.isFailed()) {
            SendMessage message = new SendMessage();
            message.setChatId(botConfig.getBkpichatid());
            message.setReplyToMessageId(update.getMessage().getMessageId());
            message.setText("Photo saved");
            bot.execute(message);
        }
    }

}