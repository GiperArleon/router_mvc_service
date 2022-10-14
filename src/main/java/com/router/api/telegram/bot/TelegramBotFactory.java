package com.router.api.telegram.bot;

import lombok.extern.slf4j.Slf4j;
import static com.router.tools.PropertyReader.PROPERTIES;

@Slf4j
public class TelegramBotFactory {
    private static Bot bot;
    private static final String BOT_NAME = PROPERTIES.getProperties().get("telegram.api.user");
    private static final String BOT_TOKEN = PROPERTIES.getProperties().get("telegram.api.token");

    private TelegramBotFactory() {
    }

    public static Bot getTelegramBot() {
        if(bot == null) {
            try {
                bot = new Bot(BOT_NAME, BOT_TOKEN);
            } catch(Exception e) {
                log.error("Can not init telegram bot: {}", e.toString());
            }
        }
        return bot;
    }
}
