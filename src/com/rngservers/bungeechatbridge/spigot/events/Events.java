package com.rngservers.bungeechatbridge.spigot.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.rngservers.bungeechatbridge.spigot.Main;
import com.rngservers.bungeechatbridge.spigot.util.Util;

public class Events implements Listener {
	private Main plugin;
	private Util util;

	public Events(Main plugin, Util util) {
		this.plugin = plugin;
		this.util = util;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		if (event.isCancelled()) {
			return;
		}
		if (plugin.getServer().getOnlinePlayers().size() == event.getRecipients().size()) {
			util.sendChatBungee(player, event.getFormat(), event.getMessage());
		}
	}
}
