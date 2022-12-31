package com.ranull.proxychatbridge.bukkit.listener;

import com.ranull.proxychatbridge.bukkit.ProxyChatBridge;
import org.bukkit.entity.Player;
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
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (plugin.getServer().getOnlinePlayers().size() == event.getRecipients().size()) {
            Player player = event.getPlayer();

            plugin.getChatManager().sendMessage(player.getUniqueId(), player.getDisplayName(),
                    event.getFormat(), event.getMessage(), player);
        }
    }
}
