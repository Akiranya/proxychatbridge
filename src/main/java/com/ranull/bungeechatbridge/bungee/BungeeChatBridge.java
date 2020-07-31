package com.ranull.bungeechatbridge.bungee;

import com.ranull.bungeechatbridge.bungee.channel.ChannelListener;
import com.ranull.bungeechatbridge.bungee.chat.ChatManager;
import com.ranull.bungeechatbridge.bungee.commands.BCBReload;
import com.ranull.bungeechatbridge.bungee.config.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeChatBridge extends Plugin {
    @Override
    public void onEnable() {
        ConfigManager configManager = new ConfigManager();
        ChatManager chatManager = new ChatManager(this, configManager);
		getProxy().registerChannel("BungeeCord");
        getProxy().getPluginManager().registerListener(this, new ChannelListener(chatManager));
        getProxy().getPluginManager().registerCommand(this, new BCBReload(configManager));
    }
}
