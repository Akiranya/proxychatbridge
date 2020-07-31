package com.ranull.bungeechatbridge.spigot.channel;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.ranull.bungeechatbridge.spigot.chat.ChatManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChannelListener implements PluginMessageListener {
    private ChatManager chatManager;

    public ChannelListener(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (channel.equalsIgnoreCase("BungeeCord")) {
            ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String subChannel = in.readUTF();
            if (subChannel.equalsIgnoreCase("BungeeChatBridge")) {
                String method = in.readUTF();
                if (method.equalsIgnoreCase("Message")) {
                    in.readUTF();
                    String displayName = in.readUTF();
                    String format = in.readUTF();
                    String message = in.readUTF();
                    chatManager.sendChat(displayName, format, message);
                }
            }
        }
    }
}
