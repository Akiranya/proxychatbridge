package com.ranull.proxychatbridge.bukkit;

import com.ranull.proxychatbridge.bukkit.command.ReloadCommand;
import com.ranull.proxychatbridge.bukkit.handler.MessageHandler;
import com.ranull.proxychatbridge.bukkit.listener.CustomChatListener;
import com.ranull.proxychatbridge.bukkit.listener.VanillaChatListener;
import com.ranull.proxychatbridge.bukkit.util.CommandMapUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import org.jetbrains.annotations.NotNull;

public class ProxyChatBridge extends JavaPlugin implements PluginMessageListener {

    public static final String PLUGIN_MESSAGE_CHANNEL = "mew:chat";
    private MessageHandler messageProcessor;

    public ProxyChatBridge() {
    }

    @Override
    public void onEnable() {
        messageProcessor = new MessageHandler(this);

        registerListeners();
        registerChannels();
        registerCommands();

        saveDefaultConfig();
        reloadConfig();
    }

    @Override
    public void onDisable() {
        unregisterListeners();
        unregisterChannels();
    }

    public MessageHandler getMessageProcessor() {
        return messageProcessor;
    }

    private void registerCommands() {
        // I just don't want to depend on "helper" for just a simple command
        CommandMapUtil.registerCommand(
                this,
                new ReloadCommand(this),
                "proxychatbridge.command.reload",
                "You don't have permission to use this command",
                "Reloads the configuration of ProxyChatBridge-Bukkit",
                new String[]{"proxychatbridge"}
        );
    }

    private void registerListeners() {
        if (getServer().getPluginManager().getPlugin("ChatChat") != null) {
            getServer().getPluginManager().registerEvents(new CustomChatListener(this), this);
        } else {
            getServer().getPluginManager().registerEvents(new VanillaChatListener(this), this);
        }
    }

    private void unregisterListeners() {
        HandlerList.unregisterAll(this);
    }

    private void registerChannels() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, PLUGIN_MESSAGE_CHANNEL);
        getServer().getMessenger().registerIncomingPluginChannel(this, PLUGIN_MESSAGE_CHANNEL, this);
    }

    private void unregisterChannels() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    public String getForwardChannel() {
        return getConfig().getString("forward");
    }

    @Override public void onPluginMessageReceived(@NotNull final String channel, @NotNull final Player player, final byte @NotNull [] data) {
        if (getConfig().getBoolean("debug")) {
            getLogger().info("Received a plugin message");
            getLogger().info("  > channel: " + channel);
            getLogger().info("  > player: " + player.getName());
            getLogger().info("  > data: " + new String(data));
        }

        getMessageProcessor().handleIncomingMessage(data);
    }

}