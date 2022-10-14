package com.router.api.telegram.bot.commands.service;

import com.router.api.telegram.bot.commands.ServiceCommand;
import com.router.clients.model.UserRecord;
import com.router.clients.teams.RestUserClient;
import com.router.clients.teams.RestUserClientFactory;
import com.router.tools.Utils;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import static com.router.api.telegram.bot.BotTextConstants.START_COMMAND;
import static com.router.api.telegram.bot.BotTextConstants.WORK_COMMAND;

@Slf4j
public class StartCommand extends ServiceCommand {

    protected RestUserClient daoUser = RestUserClientFactory.getRestUserClient();

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);
        log.info("команда {} пользователь {}", this.getCommandIdentifier(), Utils.getUserFullDescription(user));
        log.debug("Пользователь {}. Начато выполнение команды {}", userName, this.getCommandIdentifier());
        try {
            UserRecord userRecord = daoUser.findUserByTelegramId(user.getId().toString());
            if(userRecord != null && userRecord.getUsername() != null) {
                log.info("user {} found by telegram id {} work mode", userRecord.getUsername(), user.getId());
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, WORK_COMMAND);
            } else {
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, START_COMMAND);
            }
        } catch(Exception e) {
            log.error("error {}", e.toString());
        }
        log.debug("Пользователь {}. Завершено выполнение команды {}", userName, this.getCommandIdentifier());
    }
}
