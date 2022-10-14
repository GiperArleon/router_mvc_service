package com.router.clients.sender;

import lombok.extern.slf4j.Slf4j;
import ru.soap.teamservice.SenderReportService;
import ru.soap.teamservice.SenderReportServiceImplService;
import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class SoapSenderClientFactory {
    private static SenderReportService instance;

    private SoapSenderClientFactory() {
    }

    public static SenderReportService getSenderReportService() {
        if(instance == null) {
            try {
                URL url = new URL("http://localhost:8444/reportsXXX?wsdl");
                SenderReportServiceImplService implService = new SenderReportServiceImplService(url);
                instance = implService.getSenderReportServiceImplPort();
            } catch(MalformedURLException e) {
                log.error("Can not init sender soap interface: {}", e.toString());
            }
        }
        return instance;
    }
}
