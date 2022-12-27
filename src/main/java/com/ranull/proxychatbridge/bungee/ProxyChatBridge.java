package com.ranull.proxychatbridge.bungee;

import com.ranull.proxychatbridge.bungee.command.ProxyChatBridgeCommand;
import com.ranull.proxychatbridge.bungee.listener.PluginMessageListener;
import com.ranull.proxychatbridge.bungee.manager.ChatManager;
import com.ranull.proxychatbridge.bungee.manager.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public class ProxyChatBridge extends Plugin {
    private ConfigManager configManager;
    private ChatManager chatManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        chatManager = new ChatManager(this);

        registerChannels();
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        unregisterChannels();
    }

    public void registerChannels() {
        getProxy().registerChannel("BungeeCord");
    }

    public void unregisterChannels() {
        getProxy().unregisterChannel("BungeeCord");
    }

    public void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new PluginMessageListener(this));
    }

    public void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new ProxyChatBridgeCommand(this));
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public Configuration getConfig() {
        return configManager.getConfig();
    }

    public void reloadConfig() {
        configManager.loadConfig();
    }
}
