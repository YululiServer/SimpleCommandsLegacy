package xyz.acrylicstyle.simplecommands.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import util.ICollectionList;
import xyz.acrylicstyle.simplecommands.SimpleCommands;
import xyz.acrylicstyle.simplecommands.utils.Utils;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Texture implements CommandExecutor {
    public static Sound BLOCK_NOTE_PLING;

    static {
        if (ICollectionList.asList(Sound.values()).map(Enum::name).contains("BLOCK_NOTE_BLOCK_PLING")) {
            BLOCK_NOTE_PLING = Sound.valueOf("BLOCK_NOTE_BLOCK_PLING");
        } else if (ICollectionList.asList(Sound.values()).map(Enum::name).contains("BLOCK_NOTE_PLING")) {
            BLOCK_NOTE_PLING = Sound.valueOf("BLOCK_NOTE_PLING");
        } else {
            BLOCK_NOTE_PLING = Sound.valueOf("NOTE_PLING");
        }
    }

    public static final List<UUID> confirm = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = TomeitoAPI.ensurePlayer(sender);
        if (player == null) return true;
        if (args.length != 0 && args[0].equalsIgnoreCase("clear")) {
            player.setResourcePack("https://google.com"); // invalid url will clear resource pack
            return true;
        }
        TomeitoAPI.getSingleProtocolVersion(player).then(protocolVersion -> {
            int pv = protocolVersion.getProtocolVersion();
            String name = protocolVersion.getName();
            if (pv == -1) {
                pv = TomeitoAPI.getProtocolVersion(player).complete();
                name = "??";
            }
            Map.Entry<String, String> texture = Utils.getResourcePackURL(pv);
            if (texture == null) return null;
            String finalName = name;
            Bukkit.getScheduler().runTask(SimpleCommands.getInstance(), () -> {
                if (args.length != 0 && player.isOp() && args[0].equalsIgnoreCase("all")) {
                    Bukkit.getOnlinePlayers().forEach(p -> p.setResourcePack(texture.getValue()));
                    return;
                }
                if (confirm.contains(player.getUniqueId())) {
                    player.setResourcePack(texture.getValue());
                    String key = texture.getKey() == null ? "v" + finalName : texture.getKey();
                    player.sendMessage(ChatColor.YELLOW + key + ChatColor.GREEN + "用のリソースパックを適用しました。");
                    player.sendMessage(ChatColor.GREEN + "元に戻す場合は" + ChatColor.YELLOW + "//texture clear" + ChatColor.GREEN + "を使用してください。");
                    confirm.remove(player.getUniqueId());
                } else {
                    player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "==============================");
                    player.sendMessage("");
                    player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "(!) " + ChatColor.RESET + ChatColor.GOLD
                            + "リソースパックを読み込みますか？");
                    player.sendMessage(ChatColor.GOLD + "読み込む場合は、10秒以内に"
                            + ChatColor.YELLOW + "//texture" + ChatColor.GOLD + "をもう一度打ってください。");
                    player.sendMessage("");
                    player.sendMessage("" + ChatColor.GOLD + ChatColor.BOLD + "==============================");
                    player.playSound(player.getLocation(), BLOCK_NOTE_PLING, 100, 1);
                    confirm.add(player.getUniqueId());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            confirm.remove(player.getUniqueId());
                        }
                    }.runTaskLater(SimpleCommands.getInstance(), 20 * 10);
                }
            });
            return null;
        }).queue();
        return true;
    }
}
