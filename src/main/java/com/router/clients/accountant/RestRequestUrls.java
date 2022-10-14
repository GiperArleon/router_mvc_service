package com.router.clients.accountant;

import static com.router.tools.PropertyReader.PROPERTIES;

public class RestRequestUrls {
    public static final String ACCOUNTANT_SERVER = PROPERTIES.getProperties().get("accountant.server.url");
    public static final String TEAMS_SERVER = PROPERTIES.getProperties().get("teams.server.url");

    public static final String GET_TIME_RECORDS_URL = ACCOUNTANT_SERVER + "/get_user_by_id/?user_id=%d&days=%d";
    public static final String POST_TIME_RECORD_URL = ACCOUNTANT_SERVER + "/create_record";
    public static final String POST_TIME_RECORD_BODY = "{ \"userId\": \"%d\",  \"description\": \"%s\",  \"hours\": \"%d\",  \"minutes\": \"%d\"}";

    public static final String GET_USER_RECORDS_URL = TEAMS_SERVER + "/get_users";
    public static final String GET_TELEGRAM_USER_URL = TEAMS_SERVER + "/get_telegram_user/%s";
    public static final String POST_USER_RECORD = TEAMS_SERVER + "/save_user";
    public static final String POST_USER_RECORD_BODY = " { \"login\": \"%s\", \"username\": \"%s\", \"surname\": \"%s\", \"telegramUser\": \"%s\", \"telegramId\": %d, \"phone\": \"%s\", \"role\": { \"r_id\": %d, \"roleName\": \"%s\" }, \"group\": { \"g_id\": %d, \"group\": \"%s\" } }";

    public static final String POST_TELEGRAM_URL_ZERO = "https://api.telegram.org/";
    public static final String POST_TELEGRAM_URL_ONE = "https://api.telegram.org/bot5767971183:AAHPBunR20Uymng6_URJecqYcMbWt8k_K2Q/sendDocument";
    public static final String POST_TELEGRAM_URL = "https://api.telegram.org/bot5767971183:AAHPBunR20Uymng6_URJecqYcMbWt8k_K2Q/sendDocument?chat_id=%s&document=%s&caption=%s";
    public static final String POST_TELEGRAM_BODY = "{'chat_id': '%s', 'document': '%s', 'caption': '%s'}";
}
