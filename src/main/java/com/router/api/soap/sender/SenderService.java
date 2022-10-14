package com.router.api.soap.sender;

import com.router.clients.model.TimeRecord;
import com.router.clients.model.UserRecord;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style= SOAPBinding.Style.RPC)
public interface SenderService {
    @WebMethod
    UserRecord[] getAllUsers();

    @WebMethod
    TimeRecord[] getRecordsForToday(Long userId);

    @WebMethod
    boolean sendTxtReportToLectors(String report);

    @WebMethod
    boolean sendPdfReportToLectors(String report);
}
