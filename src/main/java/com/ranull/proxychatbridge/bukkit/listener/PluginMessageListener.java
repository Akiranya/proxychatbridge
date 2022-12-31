package com.ranull.proxychatbridge.bukkit.listener;

import com.ranull.proxychatbridge.bukkit.ProxyChatBridge;
import com.ranull.proxychatbridge.common.util.UUIDUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.UUID;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {
    private final ProxyChatBridge plugin;

    public PluginMessageListener(ProxyChatBridge plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] bytes) {
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));

        try {
            if (dataInputStream.readUTF().equals("ProxyChatBridge") && dataInputStream.readUTF().equals("Message")) {
                String source = dataInputStream.readUTF();
                UUID uuid = UUIDUtil.getUUID(dataInputStream.readUTF());
                String name = dataInputStream.readUTF();
                String format = dataInputStream.readUTF();
                String message = dataInputStream.readUTF();

                plugin.getChatManager().sendMessageToPlayers(uuid, name, format, message, source);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
