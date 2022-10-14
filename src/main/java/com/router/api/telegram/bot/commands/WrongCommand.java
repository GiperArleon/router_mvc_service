package com.router.api.telegram.bot.commands;

import lombok.extern.slf4j.Slf4j;
import static com.router.api.telegram.bot.BotTextConstants.WRONG_COMMAND;

@Slf4j
public class WrongCommand {
    public String wrongCommandExecute(Long chatId, String userName, String text) {
        log.debug("Пользователь {}. Начата обработка сообщения \"{}\", не являющегося командой", userName, text);
        log.debug("Пользователь {}. Завершена обработка сообщения \"{}\", не являющегося командой", userName, text);
        return WRONG_COMMAND;
    }
}
