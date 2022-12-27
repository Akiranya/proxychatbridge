package com.ranull.proxychatbridge.bungee.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.ranull.proxychatbridge.bungee.ProxyChatBridge;
import com.ranull.proxychatbridge.bungee.event.ExternalChatEvent;
import com.ranull.proxychatbridge.common.util.StringUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class ChatManager {
    private final ProxyChatBridge plugin;

    public ChatManager(ProxyChatBridge plugin) {
        this.plugin = plugin;
    }

    public void bridgeChat(UUID uuid, String displayName, String format, String message) {
        ProxiedPlayer proxiedPlayer = plugin.getProxy().getPlayer(uuid);
        String serverName = proxiedPlayer.getServer().getInfo().getName();
        String serverNameFormatted = replaceText(serverName);

        if (plugin.getConfig().getBoolean("settings.title-case", true)) {
            serverNameFormatted = StringUtil.toTitleCase(serverNameFormatted);
        }

        String prefix = plugin.getConfig().getString("settings.servers." + serverName + ".prefix", "");

        if (prefix.equals("")) {
            prefix = plugin.getConfig().getString("settings.prefix", "");
        }

        format = replaceText(prefix.replace("%server%", serverNameFormatted).replace("&", "§")
                + ChatColor.RESET + format);
        message = replaceText(message);
        String group = plugin.getConfig().getString("settings.servers." + serverName + ".group", "global");

        for (Entry<String, ServerInfo> servers : plugin.getProxy().getServers().entrySet()) {
            String externalGroup = plugin.getConfig().getString("settings.servers." + servers.getKey()
                    + ".group", "global");

            if ((externalGroup.equals("global") || externalGroup.equals(group))
                    && !servers.getKey().equals(serverName)) {
                ExternalChatEvent externalChatEvent = new ExternalChatEvent(uuid, displayName, format, message,
                        group, proxiedPlayer, servers.getValue());

                plugin.getProxy().getPluginManager().callEvent(externalChatEvent);

                if (!externalChatEvent.isCancelled()) {
                    sendChatData(externalChatEvent.getServerInfo(), serverName, externalChatEvent.getProxiedPlayer(),
                            externalChatEvent.getDisplayName(), externalChatEvent.getFormat(),
                            externalChatEvent.getMessage());
                }
            }
        }
    }

    public String replaceText(String string) {
        Map<String, String> stringMap = new HashMap<>();

        for (String key : plugin.getConfig().getSection("settings.replace-text").getKeys()) {
            stringMap.put(key, plugin.getConfig().getString("settings.replace-text." + key));
        }

        return StringUtil.replaceAll(string, stringMap);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void sendChatData(ServerInfo server, String serverName, ProxiedPlayer player, String displayName,
                              String format, String message) {
        ByteArrayDataOutput byteArrayDataOutput = ByteStreams.newDataOutput();

        byteArrayDataOutput.writeUTF("ProxyChatBridge");
        byteArrayDataOutput.writeUTF("Message");
        byteArrayDataOutput.writeUTF(serverName);
        byteArrayDataOutput.writeUTF(player.getUniqueId().toString());
        byteArrayDataOutput.writeUTF(displayName);
        byteArrayDataOutput.writeUTF(format);
        byteArrayDataOutput.writeUTF(message);

        server.sendData("BungeeCord", byteArrayDataOutput.toByteArray());
    }
}
