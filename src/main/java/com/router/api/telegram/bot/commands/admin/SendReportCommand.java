package com.router.api.telegram.bot.commands.admin;

import com.router.api.telegram.bot.commands.OperationCommand;
import com.router.clients.model.UserRecord;
import com.router.clients.sender.SoapSenderClientFactory;
import com.router.clients.model.UserRoles;
import com.router.tools.Utils;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.soap.teamservice.SenderReportService;
import static com.router.api.telegram.bot.BotTextConstants.NOT_ENOUGH_RIGHTS;
import static com.router.api.telegram.bot.BotTextConstants.START_COMMAND;
import static com.router.tools.Utils.getUserName;

@Slf4j
public class SendReportCommand extends OperationCommand {

    protected SenderReportService senderReportService = SoapSenderClientFactory.getSenderReportService();

    public SendReportCommand(String identifier, String description) {
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
                    log.info("user with telegram id {} do not have enough rights to get reports", user.getId());
                    sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, NOT_ENOUGH_RIGHTS);
                    return;
                }

                if(strings.length == 1 && strings[0].equals("pdf"))
                    senderReportService.sendPdfReportToLectors();
                else
                    senderReportService.sendReportToLectors();
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
