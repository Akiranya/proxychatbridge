package com.ranull.proxychatbridge.bungee.listener;

import com.ranull.proxychatbridge.bungee.ProxyChatBridge;
import com.ranull.proxychatbridge.bungee.event.ExternalChatReceiveEvent;
import com.ranull.proxychatbridge.common.util.UUIDUtil;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class PluginMessageListener implements Listener {
    private final ProxyChatBridge plugin;

    public PluginMessageListener(ProxyChatBridge plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) {
        if (event.getTag().equals("BungeeCord") && event.getSender() instanceof Server) {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(event.getData()));

            try {
                if (dataInputStream.readUTF().equals("ProxyChatBridge") && dataInputStream.readUTF().equals("Message")) {
                    ServerInfo serverInfo = ((Server) event.getSender()).getInfo();
                    UUID uuid = UUIDUtil.getUUID(dataInputStream.readUTF());
                    String name = dataInputStream.readUTF();
                    String format = dataInputStream.readUTF();
                    String message = dataInputStream.readUTF();
                    String group = plugin.getChatManager().getGroup(serverInfo.getName());

                    if (!group.equals("")) {
                        ExternalChatReceiveEvent externalChatReceiveEvent = new ExternalChatReceiveEvent(uuid, name,
                                format, message, group, serverInfo.getName(), serverInfo);

                        plugin.getProxy().getPluginManager().callEvent(externalChatReceiveEvent);

                        if (!externalChatReceiveEvent.isCancelled()) {
                            plugin.getChatManager().bridgeServerChat(uuid, name, format, message, serverInfo);
                        }
                    }
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
