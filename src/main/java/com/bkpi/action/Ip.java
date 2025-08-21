package com.bkpi.action;

import com.bkpi.configuration.BotConfig;
import com.bkpi.enumerator.ActionEnum;
import com.bkpi.ipchecker.IpChecker;
import com.bkpi.services.PingLogService;
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
public class Ip extends Action {

    private final BotConfig botConfig;
    private final PingLogService pingLogService;
    private final IpChecker ipChecker;

    @Override
    public ActionEnum getActionType() {
        return ActionEnum.IP;
    }

    @Override
    @SneakyThrows
    protected void reply(AbsSender bot, Update update) {
        log.info("Text detected");
        SendMessage message = new SendMessage();
        message.setChatId(botConfig.getBkpichatid());
        message.setReplyToMessageId(update.getMessage().getMessageId());
        message.setText(pingLogService.checkPingUpdate(ipChecker.getIp()));
        bot.execute(message);
    }

}