package com.router.api.telegram.bot.commands.admin;

import com.router.api.telegram.bot.commands.OperationCommand;
import com.router.clients.model.UserRecord;
import com.router.clients.notif.SoapNotificatorClientFactory;
import com.router.clients.model.UserRoles;
import com.router.tools.Utils;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.soap.teamservice.NotificatorReportService;
import static com.router.api.telegram.bot.BotTextConstants.*;
import static com.router.tools.Utils.getUserName;

@Slf4j
public class CheckNoTracksCommand extends OperationCommand {

    protected NotificatorReportService notificatorReportService = SoapNotificatorClientFactory.getNotificatorReportService();

    public CheckNoTracksCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = getUserName(user);
        log.debug("Пользователь {}. Начато выполнение команды {}", userName, this.getCommandIdentifier());
        log.info("команда {} пользователь {}", this.getCommandIdentifier(), Utils.getUserFullDescription(user));

        try {
            UserRecord userRecord = daoUser.findUserByTelegramId(user.getId().toString());
            if(userRecord != null && userRecord.getUsername() != null) {
                if(userRecord.getRole().getR_id()< UserRoles.LEAD.ordinal()) {
                    log.info("user with telegram id {} do not have enough rights to get notifications", user.getId());
                    sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, NOT_ENOUGH_RIGHTS);
                    return;
                }

                int days = Integer.parseInt(strings[0]);
                if(days == 1) {
                    notificatorReportService.sendNotificationReportToLeads();
                }
                else if(days == 3) {
                    notificatorReportService.sendNotificationReportToLectors();
                }
                else {
                    log.error("wrong parameters:");
                    for(String str: strings)
                        log.error(str);
                    sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, BOT_ERROR_CHECK_WRONG_PARAMS);
                }
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
