package com.ranull.proxychatbridge.bukkit.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ranull.proxychatbridge.bukkit.ProxyChatBridge;
import com.ranull.proxychatbridge.bukkit.event.ExternalChatReceiveEvent;
import com.ranull.proxychatbridge.bukkit.event.ExternalChatSendEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChatManager {
    private final ProxyChatBridge plugin;

    public ChatManager(ProxyChatBridge plugin) {
        this.plugin = plugin;
    }

    public void sendMessage(UUID uuid, String name, String format, String message, Player player) {
        ExternalChatSendEvent externalChatSendEvent = new ExternalChatSendEvent(uuid, name, format, message);

        plugin.getServer().getPluginManager().callEvent(externalChatSendEvent);

        if (!externalChatSendEvent.isCancelled()) {
            sendChatData(externalChatSendEvent.getUUID(), externalChatSendEvent.getName(),
                    externalChatSendEvent.getFormat(), externalChatSendEvent.getMessage(), player);
        }
    }

    public void sendMessageToPlayers(UUID uuid, String name, String format, String message, String source) {
        String finalSource = !source.equals("") ? source : null;
        ;

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            ExternalChatReceiveEvent externalChatReceiveEvent = new ExternalChatReceiveEvent(uuid, name, format,
                    message, finalSource);

            plugin.getServer().getPluginManager().callEvent(externalChatReceiveEvent);

            if (!externalChatReceiveEvent.isCancelled()) {
                String messageFormatted = externalChatReceiveEvent.getFormat().replace("%1$s",
                        externalChatReceiveEvent.getName()).replace("%2$s",
                        externalChatReceiveEvent.getMessage());

                plugin.getLogger().info(ChatColor.stripColor(messageFormatted));

                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    player.sendMessage(messageFormatted);
                }
            }
        });
    }

    @SuppressWarnings("UnstableApiUsage")
    private void sendChatData(UUID uuid, String name, String format, String message, Player player) {
        ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();

        byteArrayDataOutput.writeUTF("ProxyChatBridge");
        byteArrayDataOutput.writeUTF("Message");
        byteArrayDataOutput.writeUTF(uuid != null ? uuid.toString() : "");
        byteArrayDataOutput.writeUTF(name);
        byteArrayDataOutput.writeUTF(format);
        byteArrayDataOutput.writeUTF(message);

        player.sendPluginMessage(plugin, "BungeeCord", byteArrayDataOutput.toByteArray());
    }
}
