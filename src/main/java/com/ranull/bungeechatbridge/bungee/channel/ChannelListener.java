package com.ranull.bungeechatbridge.bungee.channel;

import com.ranull.bungeechatbridge.bungee.chat.ChatManager;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class ChannelListener implements Listener {
    private ChatManager chatManager;

    public ChannelListener(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    @EventHandler
    public void onPluginMessage(PluginMessageEvent event) throws IOException {
        if (event.getTag().equals("BungeeCord")) {
            ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
            DataInputStream channel = new DataInputStream(stream);

            String name = channel.readUTF();
            if (!name.equals("BungeeChatBridge") || !(event.getSender() instanceof Server)) {
                return;
            }
            String method = channel.readUTF();
            if (method.equals("Message")) {
                String user = channel.readUTF();
                String display = channel.readUTF();
                String format = channel.readUTF();
                String message = channel.readUTF();
                chatManager.syncChat(user, display, format, message);
            }
        }
    }
}
