package com.router.clients.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRecord {
    private int id;
    private String login;
    private String username;
    private String surname;
    private String telegramUser;
    private long telegramId;
    private String phone;
    private Role role;
    private Group group;
}
