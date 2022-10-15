package com.router.api.soap.sender;

import com.router.api.telegram.bot.Bot;
import com.router.api.telegram.bot.TelegramBotFactory;
import com.router.api.telegram.messager.TelegramMessageSender;
import com.router.api.telegram.messager.TelegramMessageSenderFactory;
import com.router.clients.accountant.RestAccountantClient;
import com.router.clients.accountant.RestAccountantClientFactory;
import com.router.clients.model.TimeRecord;
import com.router.clients.model.UserRecord;
import com.router.clients.teams.RestUserClient;
import com.router.clients.teams.RestUserClientFactory;
import com.router.clients.model.UserRoles;
import com.router.pdf.PdfManager;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import javax.jws.WebService;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@WebService(endpointInterface = "com.router.api.soap.sender.SenderService")
public class SenderServiceImpl implements SenderService {

    TelegramMessageSender telegramMessageSender = TelegramMessageSenderFactory.getTelegramMessageSender();
    RestUserClient daoUser = RestUserClientFactory.getRestUserClient();
    RestAccountantClient restAccountantClient = RestAccountantClientFactory.getRestAccountantClient();
    Bot bot = TelegramBotFactory.getTelegramBot();

    @Override
    public UserRecord[] getAllUsers() {
        log.debug("getAllUsers called");
        List<UserRecord> usersList = daoUser.getUsers();
        return usersList.toArray(new UserRecord[0]);
    }

    @Override
    public TimeRecord[] getRecordsForToday(Long userId) {
        log.debug("getTodayRecords called");
        List<TimeRecord> records = restAccountantClient.getRecords(userId, 0);
        return records.toArray(new TimeRecord[0]);
    }

    @Override
    public boolean sendTxtReportToLectors(String report) {
        log.info("sendTxtReportToLectors called");
        List<UserRecord> usersList = daoUser.getUsers();
        for(UserRecord user: usersList) {
            if(user.getRole().getR_id()>= UserRoles.LECTOR.ordinal()) {
                telegramMessageSender.sendNotificationById(String.valueOf(user.getTelegramId()), report);
            }
        }
        return true;
    }

    @Override
    public boolean sendPdfReportToLectors(String report) {
        log.info("sendPdfReportToLectors called");
        try {
            String fileName = PdfManager.prepearePdf(report);
            List<UserRecord> usersList = daoUser.getUsers();
            File file = new File(fileName);
            InputFile inputFile = new InputFile();
            inputFile.setMedia(file, LocalDate.now().toString().concat("_report.pdf"));
            for(UserRecord user: usersList) {
                if(user.getRole().getR_id() >= UserRoles.LECTOR.ordinal()) {
                    SendDocument response = new SendDocument(String.valueOf(user.getTelegramId()), inputFile);
                    bot.execute(response);
                }
            }
            return true;
        } catch(Exception e) {
            log.error("send pdf ex {}", e.toString());
            return false;
        }
    }
}
