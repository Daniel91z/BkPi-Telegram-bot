package com.bkpi.action;

import com.bkpi.configuration.BotConfig;
import com.bkpi.enumerator.ActionEnum;
import com.bkpi.services.VideoHandler;
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
public class SaveVideo extends Action {

    private final BotConfig botConfig;
    private final VideoHandler videoHandler;

    @Override
    public ActionEnum getActionType() {
        return ActionEnum.SAVE_VIDEO;
    }

    @Override
    @SneakyThrows
    protected void reply(AbsSender bot, Update update) {
        videoHandler.handleVideoDownload(bot, update);
        SendMessage message = new SendMessage();
        message.setChatId(botConfig.getBkpichatid());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText("Video saved");
        bot.execute(message);
    }

}