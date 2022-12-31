package com.ranull.proxychatbridge.bungee;

import com.ranull.proxychatbridge.bungee.command.ProxyChatBridgeCommand;
import com.ranull.proxychatbridge.bungee.listener.PluginMessageListener;
import com.ranull.proxychatbridge.bungee.manager.ChatManager;
import com.ranull.proxychatbridge.bungee.manager.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import org.bstats.bungeecord.Metrics;

public class ProxyChatBridge extends Plugin {
    private static ProxyChatBridge instance;
    private ConfigManager configManager;
    private ChatManager chatManager;

    public static void sendMessage(String name, String format, String message) {
        sendMessage(name, format, message, "global");
    }

    public static void sendMessage(String name, String format, String message, String group) {
        sendMessage(name, format, message, group, null);
    }

    public static void sendMessage(String name, String format, String message, String group, String source) {
        instance.getChatManager().sendMessage(name, format, message, group, source);
    }

    @Override
    public void onEnable() {
        instance = this;
        configManager = new ConfigManager(this);
        chatManager = new ChatManager(this);

        registerMetrics();
        registerChannels();
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        unregisterChannels();
    }

    private void registerMetrics() {
        new Metrics(this, 17238);
    }

    private void registerChannels() {
        getProxy().registerChannel("BungeeCord");
    }

    private void unregisterChannels() {
        getProxy().unregisterChannel("BungeeCord");
    }

    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new PluginMessageListener(this));
    }

    private void registerCommands() {
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
