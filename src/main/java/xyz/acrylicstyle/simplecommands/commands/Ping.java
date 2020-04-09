package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ping implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be invoked from console.");
            return true;
        }
        try {
            if (args.length == 1) {
                Player player = Bukkit.getPlayer(args[0]);
                sender.sendMessage(ChatColor.GREEN + "Ping: " + PingAll.getPing(player) + "ms");
            } else {
                Player player = (Player) sender;
                player.sendMessage(ChatColor.GREEN + "Ping: " + PingAll.getPing(player) + "ms");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
