package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import util.ReflectionHelper;
import xyz.acrylicstyle.shared.NMSAPI;
import xyz.acrylicstyle.shared.OBCAPI;

public class PingAll implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Bukkit.getOnlinePlayers().forEach(player -> sender.sendMessage(ChatColor.GREEN + player.getName() + "'s ping: " + getPing(player) + "ms"));
        return true;
    }

    public static String getPing(Player player) {
        Object ep = ReflectionHelper.invokeMethodWithoutException(OBCAPI.getClassWithoutException("entity.CraftPlayer"), player, "getHandle");
        //noinspection ConstantConditions
        int ping = (int) ReflectionHelper.getFieldWithoutException(NMSAPI.getClassWithoutException("EntityPlayer"), ep, "ping");
        String message;
        if (ping <= 5) message = "" + ChatColor.LIGHT_PURPLE + ping;
        else if (ping <= 50) message = "" + ChatColor.GREEN + ping;
        else if (ping <= 150) message = "" + ChatColor.YELLOW + ping;
        else if (ping <= 250) message = "" + ChatColor.GOLD + ping;
        else if (ping <= 350) message = "" + ChatColor.RED + ping;
        else message = "" + ChatColor.DARK_RED + ping;
        return message;
    }
}
