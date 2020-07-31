package com.ranull.bungeechatbridge.bungee.chat;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ranull.bungeechatbridge.bungee.BungeeChatBridge;
import com.ranull.bungeechatbridge.bungee.config.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Map.Entry;

public class ChatManager {
    private BungeeChatBridge plugin;
    private ConfigManager configManager;

    public ChatManager(BungeeChatBridge plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public void syncChat(String user, String display, String format, String message) {
        ProxiedPlayer player = plugin.getProxy().getPlayer(user);
        String server = player.getServer().getInfo().getName();
        String serverName = server;

        if (configManager.getConfig().getBoolean("settings.titleCase")) {
            serverName = toTitleCase(serverName);
        }

        String prefix = configManager.getConfig().getString("servers." + server + ".prefix");
        if (prefix.equals("")) {
            prefix = configManager.getConfig().getString("settings.defaultPrefix");
        }
        prefix = prefix.replace("$server", serverName).replace("&", "§") + ChatColor.RESET;

        Integer subtractFromStart = configManager.getConfig().getInt("servers." + server + ".removeFromStart");
        if (subtractFromStart != null) {
            format = format.substring(subtractFromStart);
        }
        format = prefix + format;

        for (Entry<String, ServerInfo> servers : plugin.getProxy().getServers().entrySet()) {
            if (!servers.getKey().equals(server)) {
                sendChat(servers.getValue(), player, display, message, format);
            }
        }
    }

    private void sendChat(ServerInfo server, ProxiedPlayer player, String displayName, String message, String format) {
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("BungeeChatBridge");
			out.writeUTF("Message");
			out.writeUTF(player.getName());
			out.writeUTF(displayName);
			out.writeUTF(format);
			out.writeUTF(message);
			server.sendData("BungeeCord", out.toByteArray());
	}

    private static String toTitleCase(String str) {
        if (str == null || str.isEmpty())
            return "";
        if (str.length() == 1)
            return str.toUpperCase();
        String[] parts = str.split(" ");
        StringBuilder sb = new StringBuilder(str.length());
        for (String part : parts) {
            if (part.length() > 1)
                sb.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
            else
                sb.append(part.toUpperCase());
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
