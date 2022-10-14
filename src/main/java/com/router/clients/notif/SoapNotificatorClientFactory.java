package com.router.clients.notif;

import lombok.extern.slf4j.Slf4j;
import ru.soap.teamservice.NotificatorReportService;
import ru.soap.teamservice.NotificatorReportServiceImplService;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class SoapNotificatorClientFactory {
    private static NotificatorReportService instance;

    private SoapNotificatorClientFactory() {
    }

    public static NotificatorReportService getNotificatorReportService() {
        if(instance == null) {
            try {
                URL url = new URL("http://localhost:8666/notificationXXX?wsdl");
                NotificatorReportServiceImplService implService = new NotificatorReportServiceImplService(url);
                instance = implService.getNotificatorReportServiceImplPort();
            } catch(MalformedURLException e) {
                log.error("Can not init notifier soap interface: {}", e.toString());
            }
        }
        return instance;
    }
}
