package com.ranull.bungeechatbridge.spigot;

import com.ranull.bungeechatbridge.spigot.channel.ChannelListener;
import com.ranull.bungeechatbridge.spigot.chat.ChatManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BungeeChatBridge extends JavaPlugin {
    @Override
    public void onEnable() {
        ChatManager chatManager = new ChatManager(this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new ChannelListener(chatManager));
    }
}