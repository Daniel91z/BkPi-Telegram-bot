package com.bkpi.handler;


import com.bkpi.action.Action;
import com.bkpi.action.Unknown;
import com.bkpi.configuration.BotConfig;
import com.bkpi.enumerator.ActionEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class ActionHandler {

    private final List<Action> actions;
    private final BotConfig botConfig;

    public void execute(AbsSender bot, Update update) {
        Action service = actions.stream()
                .filter(s -> getAction(update).equals(s.getActionType()))
                .findFirst()
                .orElse(new Unknown(botConfig));
        service.execute(bot, update);
    }

    private ActionEnum getAction(Update update) {
        if (update.getMessage().hasText()) {
            return ActionEnum.getAction(update.getMessage().getText().toUpperCase());
        } else if (update.getMessage().hasPhoto()) {
            return ActionEnum.SAVE_PHOTO;
        } else if (update.getMessage().hasVideo()) {
            return ActionEnum.SAVE_VIDEO;
        } else if (update.getMessage().hasDocument()) {
            log.info("Document detected");
            return ActionEnum.SAVE_DOCUMENT;
        } else {
            log.info("Unknown action detected");
            return ActionEnum.UNKNOWN;
        }
    }

}