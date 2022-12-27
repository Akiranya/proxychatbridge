package com.ranull.proxychatbridge.bukkit.listener;

import com.ranull.proxychatbridge.bukkit.ProxyChatBridge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
    private final ProxyChatBridge plugin;

    public AsyncPlayerChatListener(ProxyChatBridge plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (plugin.getServer().getOnlinePlayers().size() == event.getRecipients().size()) {
            plugin.getChatManager().sendChatData(event.getPlayer(), event.getFormat(), event.getMessage());
        }
    }
}
