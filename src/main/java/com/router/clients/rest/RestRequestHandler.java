package com.router.clients.rest;

import com.router.clients.model.TimeRecord;
import com.router.clients.model.UserRecord;
import lombok.extern.slf4j.Slf4j;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import static com.router.clients.rest.RestRequestUrls.*;

@Slf4j
public class RestRequestHandler {
    private final HttpClient client;
    private final JsonParser jsonParser;

    public RestRequestHandler() {
        this.client = HttpClient.newHttpClient();
        this.jsonParser = new JsonParser();
    }

    public List<TimeRecord> getTimeRecords(Long userId, Integer days) {
        HttpResponse<String> response;
        List<TimeRecord> result = new ArrayList<>();
        String urlStr = String.format(GET_TIME_RECORDS_URL, userId, days);
        try {
            log.debug("send get rest request {}", urlStr);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlStr))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                log.info("got response code {} body {}", response.statusCode(), response.body());
                result = jsonParser.getListOfTimeRecordsFromJson(response.body());
                log.info("parsing result: {}", result);
            } else {
                log.error("wrong response code = {}", response.statusCode());
            }
        } catch(Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public void postTimeRecord(Integer user, Integer hours, Integer minutes, String description) {
        HttpResponse<String> response;
        String jsonUser = String.format(POST_TIME_RECORD_BODY, user, description, hours, minutes);
        try {
            log.debug("send post rest request {} body {}", POST_TIME_RECORD_URL, jsonUser);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(POST_TIME_RECORD_URL))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonUser))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                log.debug("got response code {} body {}", response.statusCode(), response.body());
            } else {
                log.error("wrong response code = {}", response.statusCode());
            }
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    public List<UserRecord> getUserRecords() {
        HttpResponse<String> response;
        List<UserRecord> result = new ArrayList<>();
        String urlStr = GET_USER_RECORDS_URL;
        try {
            log.debug("send get rest request {}", urlStr);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlStr))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                log.debug("got response code {} body {}", response.statusCode(), response.body());
                result = jsonParser.getListOfUserRecordsFromJson(response.body());
                log.debug("parsing result: {}", result);
            } else {
                log.error("wrong response code = {}", response.statusCode());
            }
        } catch(Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public UserRecord getUserRecordByTelegramId(String userId) {
        HttpResponse<String> response;
        UserRecord result = new UserRecord();
        String urlStr = String.format(GET_TELEGRAM_USER_URL, userId);
        try {
            log.debug("send get rest request {}", urlStr);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlStr))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                log.debug("got response code {} body {}", response.statusCode(), response.body());
                result = jsonParser.getUserRecordFromJson(response.body(), UserRecord.class);
                log.debug("parsing result: {}", result);
            } else {
                log.error("wrong response code = {}", response.statusCode());
            }
        } catch(Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public void postUserRecord(UserRecord userRecord) {
        HttpResponse<String> response;
        String jsonUser = String.format(POST_USER_RECORD_BODY,
                userRecord.getLogin(),
                userRecord.getUsername(),
                userRecord.getSurname(),
                userRecord.getTelegramUser(),
                userRecord.getTelegramId(),
                userRecord.getPhone(),
                userRecord.getRole().getR_id(),
                userRecord.getRole().getRoleName(),
                userRecord.getGroup().getG_id(),
                userRecord.getGroup().getGroup());
        try {
            log.debug("send post rest request {} body {}", POST_USER_RECORD, jsonUser);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(POST_USER_RECORD))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonUser))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                log.debug("got response code {} body {}", response.statusCode(), response.body());
            } else {
                log.error("wrong response code = {}", response.statusCode());
            }
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    public void postPDFtoTelegram(String chatId, String filePath, String description) {
        HttpResponse<String> response;
        String jsonUser = String.format(POST_TELEGRAM_BODY, chatId, filePath, description);
        try {
            log.info("send post rest request to telegram {} body {}", POST_TELEGRAM_URL_ONE, jsonUser);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(POST_TELEGRAM_URL_ONE))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonUser))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                log.debug("got response code {} body {}", response.statusCode(), response.body());
            } else {
                log.error("wrong response code = {}", response.statusCode());
            }
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }
}
