package com.ranull.proxychatbridge.bukkit;

import com.ranull.proxychatbridge.bukkit.command.ProxyChatBridgeBukkitBroadcastCommand;
import com.ranull.proxychatbridge.bukkit.listener.AsyncPlayerChatListener;
import com.ranull.proxychatbridge.bukkit.listener.PluginMessageListener;
import com.ranull.proxychatbridge.bukkit.manager.ChatManager;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class ProxyChatBridge extends JavaPlugin {
    private static ProxyChatBridge instance;
    private ChatManager chatManager;

    public static void sendMessage(UUID uuid, String name, String format, String message, Player player) {
        instance.getChatManager().sendMessage(uuid, name, format, message, player);
    }

    @Override
    public void onEnable() {
        instance = this;
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
        PluginCommand proxyChatBridgeBroadcastPluginCommand = getCommand("proxychatbridgebukkitbroadcast");
        PluginCommand sitPluginCommand = getCommand("sit");

        if (proxyChatBridgeBroadcastPluginCommand != null) {
            proxyChatBridgeBroadcastPluginCommand.setExecutor(new ProxyChatBridgeBukkitBroadcastCommand(this));
        }
    }

    public ChatManager getChatManager() {
        return chatManager;
    }
}