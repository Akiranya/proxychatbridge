package com.rngservers.bungeechatbridge.bungee;

import com.rngservers.bungeechatbridge.bungee.channel.ChannelListener;
import com.rngservers.bungeechatbridge.bungee.chat.ChatManager;
import com.rngservers.bungeechatbridge.bungee.commands.BCBReload;
import com.rngservers.bungeechatbridge.bungee.config.ConfigManager;

import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {
	@Override
	public void onEnable() {
		ConfigManager config = new ConfigManager();
		config.createConfig();
		config.loadConfig();
		ChatManager chat = new ChatManager(this, config);
		getProxy().getPluginManager().registerListener(this, new ChannelListener(chat));
        getProxy().getPluginManager().registerCommand(this, new BCBReload(config));
	}
}
