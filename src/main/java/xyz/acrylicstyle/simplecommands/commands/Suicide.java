package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.simplecommands.SimpleCommands;

public class Suicide implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be invoked from console.");
            return true;
        }
        Player player = (Player) sender;
        if (!SimpleCommands.getInstance().getConfig().getBoolean("allowSuicide", false)) {
            if (!player.hasPermission("simplecommands.suicide") && !player.isOp()) {
                player.sendMessage(ChatColor.RED + "権限がありません。");
                return true;
            }
        }
        player.setHealth(0.0D);
        player.sendMessage(ChatColor.GOLD + "You took your own life.");
        return true;
    }
}
