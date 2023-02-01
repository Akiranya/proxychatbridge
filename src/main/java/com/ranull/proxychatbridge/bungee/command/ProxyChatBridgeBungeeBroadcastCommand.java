package com.ranull.proxychatbridge.bungee.command;

import com.ranull.proxychatbridge.bungee.ProxyChatBridge;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

import java.util.Arrays;

public class ProxyChatBridgeBungeeBroadcastCommand extends Command {
    private final ProxyChatBridge plugin;

    public ProxyChatBridgeBungeeBroadcastCommand(ProxyChatBridge plugin) {
        super("proxychatbridgeproxybroadcast", null, "pcbpb");
        this.plugin = plugin;
    }

    @SuppressWarnings("deprecation")
    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender.hasPermission("proxychatbridge.broadcast")) {
            if (args.length > 1) {
                String group = args[0];
                String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).replace("&", "§");

                plugin.getChatManager().broadcast(group, message);
                commandSender.sendMessage(ChatColor.WHITE + "✉" + ChatColor.DARK_GRAY + " » "
                        + ChatColor.RESET + "Broadcast sent to group (" + group + ").");
            } else {
                commandSender.sendMessage(ChatColor.WHITE + "✉" + ChatColor.DARK_GRAY + " » "
                        + ChatColor.RESET + "/pcbpb <group> <message>");
            }
        } else {
            commandSender.sendMessage(ChatColor.WHITE + "✉" + ChatColor.DARK_GRAY + " » "
                    + ChatColor.RESET + "No permission.");
        }
    }
}
