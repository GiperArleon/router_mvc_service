package com.router.tools;

import lombok.extern.slf4j.Slf4j;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class PropertyReader {
    public static final PropertyReader PROPERTIES = new PropertyReader();
    private final Map<String, String> properties = readProperties();

    private PropertyReader() {
    }

    private Map<String, String> readProperties() {
        Map<String, String> properties = new HashMap<>();
        String fileName = "application.properties";
        try(InputStream input = PropertyReader.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            Properties prop = new Properties();
            if(input != null) {
                prop.load(input);
            } else {
                String tmp = "property file '" + fileName + "' not found";
                log.error(tmp);
                throw new FileNotFoundException(tmp);
            }
            properties.put("accountant.server.url", prop.getProperty("accountant.server.url"));
            properties.put("teams.server.url", prop.getProperty("teams.server.url"));
            properties.put("telegram.api.url", prop.getProperty("telegram.api.url"));
            properties.put("telegram.api.user", prop.getProperty("telegram.api.user"));
            properties.put("telegram.api.token", prop.getProperty("telegram.api.token"));
            properties.put("telegram.developer.id", prop.getProperty("telegram.developer.id"));
        } catch(IOException e) {
            log.error("Exception: " + e.toString());
        }
        return properties;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
