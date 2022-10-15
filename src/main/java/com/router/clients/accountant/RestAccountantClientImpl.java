package com.router.clients.accountant;

import com.router.clients.model.TimeRecord;
import com.router.clients.rest.RestRequestHandler;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class RestAccountantClientImpl implements RestAccountantClient {
    private final RestRequestHandler restRequestHandler;

    RestAccountantClientImpl(RestRequestHandler restRequestHandler) {
        this.restRequestHandler = restRequestHandler;
    }

    @Override
    public List<TimeRecord> getRecords(Long userId, Integer days) {
        List<TimeRecord> result = restRequestHandler.getTimeRecords(userId, days);
        for(TimeRecord timeRecord: result) {
            log.debug("{} {} {} {} {} {}", timeRecord.getId(), timeRecord.getUserId(), timeRecord.getDate(), timeRecord.getHours(), timeRecord.getMinutes(), timeRecord.getDescription());
        }
        return result;
    }

    @Override
    public void postRecord(Integer user, Integer hours, Integer minutes, String description) {
        restRequestHandler.postTimeRecord(user, hours, minutes, description);
    }

    @Override
    public void postPDFtoTelegram(String chatId, String filePath, String description) {
        restRequestHandler.postPDFtoTelegram(chatId, filePath, description);
    }
}
