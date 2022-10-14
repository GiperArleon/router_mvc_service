package com.router.clients.accountant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestAccountantClientFactory {
    private static RestAccountantClient instance;
    private static RestRequestHandler restRequestHandler;

    private RestAccountantClientFactory() {
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

    public static RestAccountantClient getRestAccountantClient() {
        if(instance == null) {
            try {
                instance = new RestAccountantClientImpl(getRestRequestHandler());
            } catch(Exception e) {
                log.error("Can not init rest accountant client: {}", e.toString());
            }
        }
        return instance;
    }
}
