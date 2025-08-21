package com.bkpi.action;


import com.bkpi.enumerator.ActionEnum;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
public abstract class Action {

    public abstract ActionEnum getActionType();

    protected abstract void reply(AbsSender bot, Update update);

    public void execute(AbsSender bot, Update update) {
        log.info("Executing {} action", getAction(update));
        reply(bot, update);
    }

    private ActionEnum getAction(Update update) {
        if (update.getMessage().hasText()) {
            return ActionEnum.getAction(update.getMessage().getText().toUpperCase());
        } else if (update.getMessage().hasPhoto()) {
            return ActionEnum.SAVE_PHOTO;
        } else if (update.getMessage().hasDocument()) {
            return ActionEnum.UNKNOWN;
        } else {
            return ActionEnum.UNKNOWN;
        }
    }

}