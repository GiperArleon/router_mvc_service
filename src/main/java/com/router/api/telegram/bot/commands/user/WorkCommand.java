package com.router.api.telegram.bot.commands.user;

import com.router.api.telegram.bot.commands.OperationCommand;
import com.router.clients.model.UserRecord;
import com.router.tools.Utils;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import java.util.Arrays;
import java.util.stream.Collectors;
import static com.router.api.telegram.bot.BotTextConstants.*;
import static com.router.tools.Utils.getUserName;

@Slf4j
public class WorkCommand extends OperationCommand {

    public static final int MAX_PARAMS = 3;

    public WorkCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = getUserName(user);
        log.debug("Пользователь {}. Начато выполнение команды {}", userName, this.getCommandIdentifier());
        log.info("команда {} пользователь {}", this.getCommandIdentifier(), Utils.getUserFullDescription(user));

        if(strings.length < MAX_PARAMS) {
            log.error("wrong parameters:");
            for(String str: strings)
                log.error(str);
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, BOT_ERROR_WORK_WRONG_PARAMS);
            return;
        }

        Integer hours = Integer.parseInt(strings[0]);
        Integer minutes = Integer.parseInt(strings[1]);
        String res = Utils.validateTime(hours, minutes);
        if(!res.equals(VALIDATOR_OK)) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, res);
            return;
        }
        res = Utils.validateText(strings[2]);
        if(!res.equals(VALIDATOR_OK)) {
            sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, res);
            return;
        }

        try {
            UserRecord userRecord = daoUser.findUserByTelegramId(user.getId().toString());
            if(userRecord != null && userRecord.getUsername() != null) {
                String message = Arrays.stream(strings)
                        .skip(2)
                        .collect(Collectors.joining(" "));
                restAccountantClient.postRecord(userRecord.getId(), hours, minutes, message);
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, "успешно, дела за сегодня\n" + getUserRecords((long)userRecord.getId(), 0));
            } else {
                log.info("user not found by telegram id {} need to reg first", user.getId());
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, START_COMMAND);
                return;
            }
        } catch(Exception e) {
            log.error("error {}", e.toString());
            sendError(absSender, chat.getId(), this.getCommandIdentifier(), userName);
        }

        log.debug("Пользователь {}. Завершено выполнение команды {}", userName, this.getCommandIdentifier());
    }
}

