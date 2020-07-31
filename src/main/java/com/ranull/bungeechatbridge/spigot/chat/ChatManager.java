package com.ranull.bungeechatbridge.spigot.chat;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ranull.bungeechatbridge.spigot.BungeeChatBridge;
import org.bukkit.entity.Player;

public class ChatManager {
    private BungeeChatBridge plugin;

    public ChatManager(BungeeChatBridge plugin) {
        this.plugin = plugin;
    }

    public void bridgeChatToBungee(Player player, String format, String message) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("BungeeChatBridge");
        out.writeUTF("Message");
        out.writeUTF(player.getName());
        out.writeUTF(player.getDisplayName());
        out.writeUTF(format);
        out.writeUTF(message);
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    public void sendChat(String displayName, String format, String message) {
        String sendMessage = format.replace("%1$s", displayName)
                .replace("%2$s", message);

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.sendMessage(sendMessage);
        }
    }
}
