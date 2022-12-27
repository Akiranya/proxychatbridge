package com.ranull.proxychatbridge.bukkit.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ranull.proxychatbridge.bukkit.ProxyChatBridge;
import com.ranull.proxychatbridge.bukkit.event.ExternalChatEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ChatManager {
    private final ProxyChatBridge plugin;

    public ChatManager(ProxyChatBridge plugin) {
        this.plugin = plugin;
    }

    public void sendExternalMessage(String serverName, UUID uuid, String displayName, String format, String message) {
        ExternalChatEvent externalChatEvent = new ExternalChatEvent(serverName, uuid, displayName, format, message);

        plugin.getServer().getPluginManager().callEvent(externalChatEvent);

        if (!externalChatEvent.isCancelled()) {
            String messageFormatted = externalChatEvent.getFormat().replace("%1$s",
                    externalChatEvent.getDisplayName()).replace("%2$s",
                    externalChatEvent.getMessage());

            plugin.getLogger().info(ChatColor.stripColor(messageFormatted));

            for (Player player : plugin.getServer().getOnlinePlayers()) {
                player.sendMessage(messageFormatted);
            }
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    public void sendChatData(Player player, String format, String message) {
        ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();

        byteArrayDataOutput.writeUTF("ProxyChatBridge");
        byteArrayDataOutput.writeUTF("Message");
        byteArrayDataOutput.writeUTF(player.getUniqueId().toString());
        byteArrayDataOutput.writeUTF(player.getDisplayName());
        byteArrayDataOutput.writeUTF(format);
        byteArrayDataOutput.writeUTF(message);

        player.sendPluginMessage(plugin, "BungeeCord", byteArrayDataOutput.toByteArray());
    }
}
