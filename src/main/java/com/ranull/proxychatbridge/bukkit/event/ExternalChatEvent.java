package com.ranull.proxychatbridge.bukkit.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ExternalChatEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final String serverName;
    private final UUID uuid;
    private String displayName;
    private String format;
    private String message;
    private boolean cancel;

    public ExternalChatEvent(String serverName, UUID uuid, String displayName, String format, String message) {
        this.serverName = serverName;
        this.uuid = uuid;
        this.displayName = displayName;
        this.format = format;
        this.message = message;
    }

    public String getServerName() {
        return serverName;
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

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
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
