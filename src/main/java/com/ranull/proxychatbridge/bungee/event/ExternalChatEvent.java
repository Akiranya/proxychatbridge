package com.ranull.proxychatbridge.bungee.event;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;

import java.util.UUID;

public class ExternalChatEvent extends Event implements Cancellable {
    private final UUID uuid;
    private final String groupName;
    private final ProxiedPlayer proxiedPlayer;
    private final ServerInfo serverInfo;
    private String displayName;
    private String format;
    private String message;
    private boolean cancel;

    public ExternalChatEvent(UUID uuid, String displayName, String format, String message, String groupName,
                             ProxiedPlayer player, ServerInfo serverInfo) {
        this.uuid = uuid;
        this.displayName = displayName;
        this.format = format;
        this.message = message;
        this.groupName = groupName;
        this.proxiedPlayer = player;
        this.serverInfo = serverInfo;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGroupName() {
        return groupName;
    }

    public ProxiedPlayer getProxiedPlayer() {
        return proxiedPlayer;
    }

    public ServerInfo getServerInfo() {
        return serverInfo;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
