package com.router.clients.teams;

import com.router.clients.model.UserRecord;

import java.util.List;

public interface RestUserClient {
    UserRecord findUserByTelegramId(String id);
    boolean saveUser(UserRecord user);
    List<UserRecord> getUsers();
}
