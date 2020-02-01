package com.rngservers.bungeechatbridge.spigot.util;

import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.rngservers.bungeechatbridge.spigot.Main;

public class Util {
	private Main plugin;

	public Util(Main plugin) {
		this.plugin = plugin;
	}

	public void sendChatBungee(Player player, String format, String message) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("BungeeChatBridge");
		out.writeUTF("Message");
		out.writeUTF(player.getName());
		out.writeUTF(player.getDisplayName());
		out.writeUTF(format);
		out.writeUTF(message);
		player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}
}
