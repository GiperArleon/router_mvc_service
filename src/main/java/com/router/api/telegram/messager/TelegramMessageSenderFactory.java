package com.router.api.telegram.messager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TelegramMessageSenderFactory {
    private static TelegramMessageSender instance;

    private TelegramMessageSenderFactory() {
    }

    public static TelegramMessageSender getTelegramMessageSender() {
        if(instance == null) {
            try {
                instance = new TelegramMessageSender();
            } catch(Exception e) {
                log.error("Can not init telegram sender: {}", e.toString());
            }
        }
        return instance;
    }
}
