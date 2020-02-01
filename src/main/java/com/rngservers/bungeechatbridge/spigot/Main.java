package com.rngservers.bungeechatbridge.spigot;

import org.bukkit.plugin.java.JavaPlugin;

import com.rngservers.bungeechatbridge.spigot.events.Events;
import com.rngservers.bungeechatbridge.spigot.util.Util;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		Util util = new Util(this);
		this.getServer().getPluginManager().registerEvents(new Events(this, util), this);
	}
}