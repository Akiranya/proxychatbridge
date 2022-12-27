package com.ranull.proxychatbridge.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.proxy.ProxyServer;

import java.util.logging.Logger;

public class ProxyChatBridge {
    private final ProxyServer proxyServer;
    private final Logger logger;

    @Inject
    public ProxyChatBridge(ProxyServer proxyServer, Logger logger) {
        this.proxyServer = proxyServer;
        this.logger = logger;

        logger.info("ProxyChatBridge Velocity support not implemented.");
    }

    public ProxyServer getProxyServer() {
        return proxyServer;
    }

    public Logger getLogger() {
        return logger;
    }
}