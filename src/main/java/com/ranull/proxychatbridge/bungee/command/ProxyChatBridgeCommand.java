package com.ranull.proxychatbridge.bungee.command;

import com.ranull.proxychatbridge.bungee.ProxyChatBridge;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ProxyChatBridgeCommand extends Command {
    private final ProxyChatBridge plugin;

    public ProxyChatBridgeCommand(ProxyChatBridge plugin) {
        super("proxychatbridge", null, "bungeechatbridge", "velocitychatbridge", "pcb", "bcb", "vcb");
        this.plugin = plugin;
    }

    public void execute(CommandSender commandSender, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(ChatColor.WHITE + "✉" + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE
                    + "ProxyChatBridge " + ChatColor.DARK_GRAY + "v" + plugin.getDescription().getVersion());
            commandSender.sendMessage(
                    ChatColor.GRAY + "/pcb " + ChatColor.DARK_GRAY + "-" + ChatColor.RESET + " Plugin info");

            if (commandSender.hasPermission("proxychatbridge.reload")) {
                commandSender.sendMessage(ChatColor.GRAY + "/pcb reload " + ChatColor.DARK_GRAY + "-" + ChatColor.RESET
                        + " Reload plugin");
            }

            commandSender.sendMessage(ChatColor.DARK_GRAY + "Author: " + ChatColor.GRAY + "Ranull");
        } else if (args[0].equals("reload")) {
            if (commandSender.hasPermission("proxychatbridge.reload")) {
                plugin.reloadConfig();
                commandSender.sendMessage(ChatColor.WHITE + "✉" + ChatColor.DARK_GRAY + " » "
                        + ChatColor.RESET + "Reloaded config file.");
            } else {
                commandSender.sendMessage(ChatColor.WHITE + "✉" + ChatColor.DARK_GRAY + " » "
                        + ChatColor.RESET + "No Permission.");
            }
        }
    }
}
