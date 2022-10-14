package com.router.api.telegram.bot.commands;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import com.router.clients.model.TimeRecord;
import com.router.clients.teams.RestUserClient;
import lombok.extern.slf4j.Slf4j;
import com.router.clients.accountant.RestAccountantClient;
import com.router.clients.accountant.RestAccountantClientFactory;
import com.router.clients.teams.RestUserClientFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import static com.router.api.telegram.bot.BotTextConstants.BOT_ERROR_MESSAGE;
import static com.router.api.telegram.bot.BotTextConstants.BOT_OK_MESSAGE;

@Slf4j
public abstract class OperationCommand extends BotCommand {

    protected RestUserClient daoUser = RestUserClientFactory.getRestUserClient();
    protected RestAccountantClient restAccountantClient = RestAccountantClientFactory.getRestAccountantClient();

    public OperationCommand(String identifier, String description) {
        super(identifier, description);
    }

    private void sendDocument(AbsSender absSender, Long chatId, String description, String commandName, String userName) {
        try {
            absSender.execute(createDocument(chatId, description));
        } catch (IOException | RuntimeException e) {
            log.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
            sendError(absSender, chatId, commandName, userName);
            e.printStackTrace();
        } catch(TelegramApiException e) {
            log.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }

    protected SendDocument createDocument(Long chatId, String fileName) throws IOException {
        String value = "Hello";
        FileOutputStream fos = new FileOutputStream(fileName);
        DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos));
        outStream.writeUTF(value);
        outStream.close();
        FileInputStream stream = new FileInputStream(fileName);
        SendDocument document = new SendDocument();
        document.setChatId(chatId.toString());
        document.setDocument(new InputFile(stream, String.format("%s.docx", fileName)));
        return document;
    }

    protected void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String message) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), message));
        } catch (TelegramApiException e) {
            log.error(String.format("Ошибка %s. Команда %s. Пользователь: %s", e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }

    protected void sendOK(AbsSender absSender, Long chatId, String commandName, String userName) {
        sendAnswer(absSender, chatId, commandName, userName, BOT_OK_MESSAGE);
    }

    protected void sendError(AbsSender absSender, Long chatId, String commandName, String userName) {
        sendAnswer(absSender, chatId, commandName, userName, BOT_ERROR_MESSAGE);
    }

    protected String getUserRecords(Long userId, Integer days) {
        List<TimeRecord> records = restAccountantClient.getRecords(userId, days);
        log.debug("records size {} all {}", records.size(), records);
        return records.stream()
                .map(t->String.format("%s %d ч %d м", t.getDescription(), t.getHours(), t.getMinutes()))
                .collect(Collectors.joining("\n"));
    }
}
