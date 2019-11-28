package com.rngservers.bungeechatbridge.bungee.channel;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.rngservers.bungeechatbridge.bungee.chat.ChatManager;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChannelListener implements Listener {
	private ChatManager chat;

	public ChannelListener(ChatManager chat) {
		this.chat = chat;
	}

	@EventHandler
	public void onPluginMessage(PluginMessageEvent event) throws IOException {
		if (!event.getTag().equals("BungeeCord")) {
			return;
		}
		ByteArrayInputStream stream = new ByteArrayInputStream(event.getData());
		DataInputStream channel = new DataInputStream(stream);

		String name = channel.readUTF();
		if (!name.equals("BungeeChatBridge")) {
			return;
		}
		if (!(event.getSender() instanceof Server)) {
			return;
		}
		String method = channel.readUTF();

		if (method.equals("Message")) {
			String user = channel.readUTF();
			String display = channel.readUTF();
			String format = channel.readUTF();
			String message = channel.readUTF();
			ProxiedPlayer player = BungeeCord.getInstance().getPlayer(user);
			chat.syncChat(player, display, format, message);
		}
	}
}
