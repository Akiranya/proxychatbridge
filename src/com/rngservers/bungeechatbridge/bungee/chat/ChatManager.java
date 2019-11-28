package com.rngservers.bungeechatbridge.bungee.chat;

import java.util.Map.Entry;

import com.rngservers.bungeechatbridge.bungee.config.ConfigManager;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ChatManager {
	private ConfigManager config;

	public ChatManager(ConfigManager config) {
		this.config = config;
	}

	public void syncChat(ProxiedPlayer player, String display, String format, String message) {
		String server = player.getServer().getInfo().getName();
		String serverName = server;

		if (config.getConfig().getBoolean("settings.titleCase")) {
			serverName = toTitleCase(serverName);
		}

		String prefix = config.getConfig().getString("servers." + server + ".prefix");
		if (prefix.equals("")) {
			prefix = config.getConfig().getString("settings.defaultPrefix");
		}
		prefix = prefix.replace("$server", serverName).replace("&", "§") + ChatColor.RESET;
		
		Integer subtractFromStart = config.getConfig().getInt("servers." + server + ".removeFromStart");
		if (subtractFromStart != null) {
			format = format.substring(subtractFromStart);
		}

		String formatedMessage = format.replace("%1$s", display).replace("%2$s", message);
		String fullMessage = prefix + formatedMessage;

		for (Entry<String, ServerInfo> servers : BungeeCord.getInstance().getServers().entrySet()) {
			if (servers.getKey().equals(server)) {
				continue;
			}
			for (ProxiedPlayer players : BungeeCord.getInstance().getServerInfo(servers.getKey()).getPlayers()) {
				players.sendMessage(TextComponent.fromLegacyText(fullMessage));
			}
		}
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
