package com.router.api.telegram.messager;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static com.router.clients.accountant.RestRequestUrls.POST_TELEGRAM_URL;
import static com.router.tools.PropertyReader.PROPERTIES;

@Slf4j
public class TelegramNotifier {
    HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .version(HttpClient.Version.HTTP_2)
            .build();

    public boolean sendMessage(String CHAT_ID, String TOKEN, String message) {

        UriBuilder builder = UriBuilder
                .fromUri(PROPERTIES.getProperties().get("telegram.api.url"))
                .path("/{token}/sendMessage")
                .queryParam("chat_id", CHAT_ID)
                .queryParam("text", message);

        URI uri = builder.build("bot" + TOKEN);
        log.info(uri.toString());

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("TelegramNotifier response {} body {}", response.statusCode(), response.body());
            return response.statusCode() == 200;
        } catch(Exception ex) {
            log.error("ex {}", ex.getMessage());
        }
        return false;
    }

    public boolean sendFile(String CHAT_ID, String TOKEN, String filePath, String descr) {
        //https://api.telegram.org/botMYT0KEN/sendDocument?
        //https://api.telegram.org/bot{token}/sendDocument
        String jsonBody = "";
        UriBuilder builder = UriBuilder
                .fromUri(PROPERTIES.getProperties().get("telegram.api.url"))
                .path("/{token}/sendDocument")
                .queryParam("chat_id", CHAT_ID)
                .queryParam("document", filePath)
                .queryParam("caption", descr);

        URI uri = builder.build("bot" + TOKEN);
        log.info(uri.toString());

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                //.uri(builder.build("bot" + TOKEN))
                .uri(URI.create(POST_TELEGRAM_URL))
                .timeout(Duration.ofSeconds(5))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.debug("TelegramNotifier response {} body {}", response.statusCode(), response.body());
            return response.statusCode() == 200;
        } catch(Exception ex) {
            log.error("ex {}", ex.getMessage());
        }
        return false;
    }

//    public boolean sendDocument(String CHAT_ID, File file, String message) {
//        SendDocument sendDocumentRequest = new SendDocument();
//        InputFile inputFile = new InputFile();
//        inputFile.setMedia(file);
//        sendDocumentRequest.setChatId(CHAT_ID);
//        sendDocumentRequest.setDocument(inputFile);
//        sendDocumentRequest.setCaption(message);
//        //execute(sendDocumentRequest);
//        return true;
//    }
}
