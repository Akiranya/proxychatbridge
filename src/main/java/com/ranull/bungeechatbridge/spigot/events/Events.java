package com.ranull.bungeechatbridge.spigot.events;

import com.ranull.bungeechatbridge.spigot.chat.ChatManager;
import com.ranull.bungeechatbridge.spigot.BungeeChatBridge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Events implements Listener {
    private BungeeChatBridge plugin;
    private ChatManager chatManager;

    public Events(BungeeChatBridge plugin, ChatManager chatManager) {
        this.plugin = plugin;
        this.chatManager = chatManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!event.isCancelled()) {
            if (plugin.getServer().getOnlinePlayers().size() == event.getRecipients().size()) {
                chatManager.bridgeChatToBungee(event.getPlayer(), event.getFormat(), event.getMessage());
            }
        }
    }
}
