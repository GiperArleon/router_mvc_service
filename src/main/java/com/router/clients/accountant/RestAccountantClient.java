package com.router.clients.accountant;

import com.router.clients.model.TimeRecord;
import java.util.List;

public interface RestAccountantClient {
    List<TimeRecord> getRecords(Long userId, Integer days);
    void postRecord(Integer user, Integer hours, Integer minutes, String description);
    void postPDFtoTelegram(String chatId, String filePath, String description);
}
