package com.ranull.proxychatbridge.bungee.listener;

import com.ranull.proxychatbridge.bungee.ProxyChatBridge;
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
    public void onPluginMessage(PluginMessageEvent event) throws IOException {
        if (event.getTag().equals("BungeeCord") && event.getSender() instanceof Server) {
            DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(event.getData()));

            if (dataInputStream.readUTF().equals("ProxyChatBridge") && dataInputStream.readUTF().equals("Message")) {
                try {
                    UUID uuid = UUID.fromString(dataInputStream.readUTF());
                    String displayName = dataInputStream.readUTF();
                    String format = dataInputStream.readUTF();
                    String message = dataInputStream.readUTF();

                    plugin.getChatManager().bridgeChat(uuid, displayName, format, message);
                } catch (IllegalArgumentException ignored) {
                }
            }
        }
    }
}
