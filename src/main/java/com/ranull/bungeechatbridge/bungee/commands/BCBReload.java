package com.ranull.bungeechatbridge.bungee.commands;

import com.ranull.bungeechatbridge.bungee.config.ConfigManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class BCBReload extends Command {
    private ConfigManager configManager;

    public BCBReload(ConfigManager configManager) {
        super("bcbreload", "bungeechatbridge.reload");
        this.configManager = configManager;
    }

    public void execute(CommandSender sender, String[] args) {
        configManager.loadConfig();
        sender.sendMessage(new TextComponent("BungeeChatBridge Reloaded!"));
    }
}
