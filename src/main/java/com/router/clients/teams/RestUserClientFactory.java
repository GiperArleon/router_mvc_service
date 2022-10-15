package com.router.clients.teams;

import com.router.clients.rest.RestRequestHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestUserClientFactory {
    private static RestUserClient instance;
    private static RestRequestHandler restRequestHandler;

    private RestUserClientFactory() {
    }

    private static RestRequestHandler getRestRequestHandler() {
        if(restRequestHandler == null) {
            try {
                restRequestHandler = new RestRequestHandler();
            } catch(Exception e) {
                log.error("Can not init rest request handler: {}", e.toString());
            }
        }
        return restRequestHandler;
    }

    public static RestUserClient getRestUserClient() {
        if(instance == null) {
            try {
                instance = new RestUserClientImpl(getRestRequestHandler());
            } catch(Exception e) {
                log.error("Can not init rest user client: {}", e.toString());
            }
        }
        return instance;
    }
}
