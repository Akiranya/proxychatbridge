package com.rngservers.bungeechatbridge.bungee.commands;

import com.rngservers.bungeechatbridge.bungee.config.ConfigManager;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class BCBReload extends Command {
	private ConfigManager config;
	public BCBReload(ConfigManager config) {
		super("bcbreload", "bungeechatbridge.reload");
		this.config = config;
	}

	public void execute(CommandSender sender, String[] args) {
		config.loadConfig();
		sender.sendMessage(new TextComponent("BungeeChatBridge Reloaded!"));
	}
}
