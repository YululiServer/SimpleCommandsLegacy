package xyz.acrylicstyle.simplecommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.acrylicstyle.authlib.GameProfile;
import xyz.acrylicstyle.authlib.properties.Property;
import xyz.acrylicstyle.authlib.properties.PropertyMap;
import xyz.acrylicstyle.nmsapi.abstracts.craftbukkit.entity.CraftPlayer;
import xyz.acrylicstyle.simplecommands.commands.Ping;
import xyz.acrylicstyle.simplecommands.commands.PingAll;
import xyz.acrylicstyle.simplecommands.commands.Suicide;
import xyz.acrylicstyle.simplecommands.commands.Texture;
import xyz.acrylicstyle.tomeito_api.providers.ConfigProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimpleCommands extends JavaPlugin implements Listener {
    private static SimpleCommands instance = null;
    public static ConfigProvider config = new ConfigProvider("./plugins/SimpleCommandsLegacy/config.yml");

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        List<String> disabledCommands = this.getConfig().getStringList("disabledCommands");
        if (disabledCommands == null) disabledCommands = new ArrayList<>();
        if (!disabledCommands.contains("ping")) Objects.requireNonNull(Bukkit.getPluginCommand("ping")).setExecutor(new Ping());
        if (!disabledCommands.contains("pingall")) Objects.requireNonNull(Bukkit.getPluginCommand("pingall")).setExecutor(new PingAll());
        if (!disabledCommands.contains("suicide")) Objects.requireNonNull(Bukkit.getPluginCommand("suicide")).setExecutor(new Suicide());
        Objects.requireNonNull(Bukkit.getPluginCommand("textures")).setExecutor(new Texture());
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    public static SimpleCommands getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!SimpleCommands.getInstance().getConfig().getBoolean("autoResourcePack", false)) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                String texture = SimpleCommands.getInstance().getConfig().getString("texture");
                if (texture == null) return;
                e.getPlayer().setResourcePack(texture);
                e.getPlayer().sendMessage(ChatColor.GREEN + "リソースパックが変更されました。元に戻すには、"
                        + ChatColor.YELLOW + "//texture clear" + ChatColor.GREEN + "を入力してください。");
            }
        }.runTaskLater(this, 20*3);
        CraftPlayer craftPlayer = new CraftPlayer(e.getPlayer());
        GameProfile profile = craftPlayer.getProfile();
        PropertyMap map = profile.getProperties();
        map.delegate().removeAll("textures");
        map.delegate().put("textures", new Property("textures", "a", "b"));
        map.removeAll("textures");
        map.put("textures", new Property("textures", "a", "b"));
        profile.setProperties(map);
        craftPlayer.getHandle().setProfile(profile);
    }
}
