package com.router.api.telegram.bot.commands.user;

import com.router.api.telegram.bot.commands.OperationCommand;
import com.router.clients.model.UserRecord;
import com.router.tools.Utils;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import static com.router.api.telegram.bot.BotTextConstants.START_COMMAND;
import static com.router.tools.Utils.getUserName;

@Slf4j
public class ShowCommand extends OperationCommand {

    public ShowCommand(String identifier, String description) {
        super(identifier, description);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings)  {
        String userName = getUserName(user);
        log.debug("Пользователь {}. Начато выполнение команды {}", userName, this.getCommandIdentifier());
        log.info("команда {} пользователь {}", this.getCommandIdentifier(), Utils.getUserFullDescription(user));

        try {
            UserRecord userRecord = daoUser.findUserByTelegramId(user.getId().toString());
            if(userRecord != null && userRecord.getUsername() != null) {
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, "дела за сегодня\n" + getUserRecords((long)userRecord.getId(), 0));
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
