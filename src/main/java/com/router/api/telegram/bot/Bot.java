package com.router.api.telegram.bot;

import com.router.api.telegram.bot.commands.admin.CheckNoTracksCommand;
import com.router.api.telegram.bot.commands.admin.SendReportCommand;
import com.router.api.telegram.bot.commands.user.ShowCommand;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import com.router.api.telegram.bot.commands.service.HelpCommand;
import com.router.api.telegram.bot.commands.service.StartCommand;
import com.router.api.telegram.bot.commands.WrongCommand;
import com.router.api.telegram.bot.commands.user.RegCommand;
import com.router.api.telegram.bot.commands.user.WorkCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.router.tools.Utils;

@Slf4j
@Getter
public final class Bot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;
    private final WrongCommand wrongCommand;

    public Bot(String botName, String botToken) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
        this.wrongCommand = new WrongCommand();

        register(new StartCommand("start", "Старт"));
        register(new RegCommand("reg", "Регистрация"));
        register(new WorkCommand("work", "Рабочее время"));
        register(new ShowCommand("show","Дела"));
        register(new CheckNoTracksCommand("notify","Проверка пользователей без треков времени"));
        register(new SendReportCommand("report","Отчет треков времени"));
        register(new HelpCommand("help","Помощь"));
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = Utils.getUserName(msg);
        String answer = wrongCommand.wrongCommandExecute(chatId, userName, msg.getText());
        setAnswer(chatId, userName, answer);
    }

    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            log.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s", e.toString(), userName));
        }
    }
}
