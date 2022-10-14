package com.router;

import com.router.api.soap.notification.NotifyServiceImpl;
import com.router.api.soap.sender.SenderServiceImpl;
import com.router.api.telegram.bot.Bot;
import com.router.api.telegram.bot.TelegramBotFactory;
import com.router.api.telegram.messager.TelegramMessageSender;
import com.router.api.telegram.messager.TelegramMessageSenderFactory;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import javax.xml.ws.Endpoint;
import static com.router.api.telegram.bot.BotTextConstants.BOT_STARTED_MESSAGE;
import static com.router.tools.PropertyReader.PROPERTIES;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            log.info("* * *");
            PROPERTIES
                    .getProperties()
                    .forEach((key, value) -> log.debug("{} = {}", key, value));

            Endpoint endpoint1 = Endpoint.create(new NotifyServiceImpl());
            endpoint1.publish("http://localhost:8333/routerNotifyXXX");

            Endpoint endpoint2 = Endpoint.create(new SenderServiceImpl());
            endpoint2.publish("http://localhost:8333/senderNotifyXXX");

            String DEV_ID = PROPERTIES.getProperties().get("telegram.developer.id");
            TelegramMessageSender telegramMessageSender = TelegramMessageSenderFactory.getTelegramMessageSender();
            Bot bot = TelegramBotFactory.getTelegramBot();
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(bot);

            if(!telegramMessageSender.sendNotificationById(DEV_ID, BOT_STARTED_MESSAGE))
                log.error("error in sending start message to dev id {}", DEV_ID);
        } catch(Throwable e) {
            log.error(e.toString());
        }
    }
}
