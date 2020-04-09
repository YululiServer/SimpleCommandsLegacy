package xyz.acrylicstyle.simplecommands;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.acrylicstyle.simplecommands.commands.Ping;
import xyz.acrylicstyle.simplecommands.commands.PingAll;
import xyz.acrylicstyle.simplecommands.commands.Suicide;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class SimpleCommands extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        List<String> disabledCommands = this.getConfig().getStringList("disabledCommands");
        if (!disabledCommands.contains("ping")) Objects.requireNonNull(Bukkit.getPluginCommand("ping")).setExecutor(new Ping());
        if (!disabledCommands.contains("pingall")) Objects.requireNonNull(Bukkit.getPluginCommand("pingall")).setExecutor(new PingAll());
        if (!disabledCommands.contains("suicide")) Objects.requireNonNull(Bukkit.getPluginCommand("suicide")).setExecutor(new Suicide());
        Bukkit.getPluginManager().registerEvents(this, this);
    }
}
