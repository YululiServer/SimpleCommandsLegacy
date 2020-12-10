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
        Player player;
        if (sender.isOp() && args.length == 1) {
            player = Bukkit.getPlayerExact(args[0]);
        } else {
            player = (Player) sender;
        }
        sender.sendMessage(ChatColor.GREEN + "Ping: " + PingAll.getPing(player) + "ms");
        return true;
    }
}
