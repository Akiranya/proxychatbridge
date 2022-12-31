package com.ranull.proxychatbridge.bungee.event;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Cancellable;
import net.md_5.bungee.api.plugin.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ExternalChatReceiveEvent extends Event implements Cancellable {
    private final UUID uuid;
    private final String group;
    private final String source;
    private final ServerInfo destination;
    private String name;
    private String format;
    private String message;
    private boolean cancel;

    public ExternalChatReceiveEvent(UUID uuid, String name, String format, String message, String group, String source,
                                    ServerInfo destination) {
        this.uuid = uuid;
        this.name = name;
        this.format = format;
        this.message = message;
        this.source = source;
        this.group = group;
        this.destination = destination;
    }

    @Nullable
    public UUID getUUID() {
        return uuid;
    }

    @Nullable
    public ProxiedPlayer getProxiedPlayer() {
        return uuid != null ? ProxyServer.getInstance().getPlayer(uuid) : null;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(@NotNull String format) {
        this.format = format;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    public void setMessage(@NotNull String message) {
        this.message = message;
    }

    @NotNull
    public String getGroup() {
        return group;
    }

    @Nullable
    public String getSource() {
        return source;
    }

    @NotNull
    public ServerInfo getDestination() {
        return destination;
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
