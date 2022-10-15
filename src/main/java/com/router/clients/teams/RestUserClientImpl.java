package com.router.clients.teams;

import com.router.clients.rest.RestRequestHandler;
import com.router.clients.model.UserRecord;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class RestUserClientImpl implements RestUserClient {
    private final RestRequestHandler restRequestHandler;

    RestUserClientImpl(RestRequestHandler restRequestHandler) {
        this.restRequestHandler = restRequestHandler;
    }

    @Override
    public UserRecord findUserByTelegramId(String id) {
        return restRequestHandler.getUserRecordByTelegramId(id);
    }

    @Override
    public boolean saveUser(UserRecord user) {
        restRequestHandler.postUserRecord(user);
        return true;
    }

    @Override
    public List<UserRecord> getUsers() {
        return restRequestHandler.getUserRecords();
    }
}
