package com.router.api.telegram.messager;

import com.router.clients.rest.RestRequestUrls;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import static com.router.tools.PropertyReader.PROPERTIES;
import org.springframework.http.ContentDisposition;
import java.text.MessageFormat;

@Slf4j
public class TelegramPdfUploader {

    private final String URL = RestRequestUrls.POST_TELEGRAM_URL_ZERO;
    private final String botToken = PROPERTIES.getProperties().get("telegram.api.token");;
    private final RestTemplate restTemplate = new RestTemplate();

    public void uploadFile(String chatId, ByteArrayResource value, String filePath) {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("document", value);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("document")
                .filename("1.pdf")
                .build();
        map.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());

        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        String url1 = MessageFormat.format("{0}bot{1}/sendDocument?chat_id={2}&document={3}&caption={4}", URL, botToken, chatId, filePath, "some");
        String url2 = MessageFormat.format("{0}bot{1}/sendDocument?chat_id={2}&document={3}&caption={4}", URL, botToken, chatId, filePath, "some");
        log.info(url2);
        try {
            restTemplate.exchange(
                    url2,
                    HttpMethod.POST,
                    requestEntity,
                    String.class);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }
}
