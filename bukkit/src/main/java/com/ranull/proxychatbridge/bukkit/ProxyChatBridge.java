package com.ranull.proxychatbridge.bukkit;

import com.ranull.proxychatbridge.bukkit.listener.AsyncPlayerChatListener;
import com.ranull.proxychatbridge.bukkit.listener.PluginMessageListener;
import com.ranull.proxychatbridge.bukkit.manager.ChatManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class ProxyChatBridge extends JavaPlugin {
    private ChatManager chatManager;

    @Override
    public void onEnable() {
        chatManager = new ChatManager(this);

        registerListeners();
        registerChannels();
        registerCommands();
    }

    @Override
    public void onDisable() {
        unregisterListeners();
        unregisterChannels();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(this), this);
    }

    private void unregisterListeners() {
        HandlerList.unregisterAll(this);
    }

    private void registerChannels() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageListener(this));
    }

    private void unregisterChannels() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    private void registerCommands() {

    }

    public ChatManager getChatManager() {
        return chatManager;
    }
}