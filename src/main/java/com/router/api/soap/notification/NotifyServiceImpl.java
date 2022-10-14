package com.router.api.soap.notification;

import com.router.api.telegram.messager.TelegramMessageSender;
import com.router.api.telegram.messager.TelegramMessageSenderFactory;
import com.router.clients.accountant.RestAccountantClient;
import com.router.clients.accountant.RestAccountantClientFactory;
import com.router.clients.model.UserRecord;
import com.router.clients.teams.RestUserClient;
import com.router.clients.teams.RestUserClientFactory;
import com.router.clients.model.TimeRecord;
import com.router.clients.teams.UserRoles;
import lombok.extern.slf4j.Slf4j;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@WebService(endpointInterface = "com.router.api.soap.notification.NotifyService")
public class NotifyServiceImpl implements NotifyService {

    TelegramMessageSender telegramMessageSender = TelegramMessageSenderFactory.getTelegramMessageSender();
    RestUserClient daoUser = RestUserClientFactory.getRestUserClient();
    RestAccountantClient restAccountantClient = RestAccountantClientFactory.getRestAccountantClient();

    @Override
    public UserRecord[] getUsersWithoutTracks(Integer days) {
        log.debug("getUsersWithoutTracks called");
        ArrayList<UserRecord> result = new ArrayList<>();
        List<UserRecord> usersList = daoUser.getUsers();
        for(UserRecord user: usersList) {
            List<TimeRecord> records = restAccountantClient.getRecords((long)user.getId(), days);
            if(records.isEmpty()) {
                result.add(user);
            }
        }
        return result.toArray(new UserRecord[0]);
    }

    @Override
    public boolean sendNotificationToLeads(String message) {
        log.debug("sendNotificationToLead called");
        List<UserRecord> usersList = daoUser.getUsers();
        for(UserRecord user: usersList) {
            if(user.getRole().getR_id()>= UserRoles.LEAD.ordinal()) {
                telegramMessageSender.sendNotificationById(String.valueOf(user.getTelegramId()), message);
            }
        }
        return true;
    }

    @Override
    public boolean sendNotificationToLectors(String message) {
        log.debug("sendNotificationToLector called");
        List<UserRecord> usersList = daoUser.getUsers();
        for(UserRecord user: usersList) {
            if(user.getRole().getR_id()>= UserRoles.LECTOR.ordinal()) {
                telegramMessageSender.sendNotificationById(String.valueOf(user.getTelegramId()), message);
            }
        }
        return true;
    }
}
