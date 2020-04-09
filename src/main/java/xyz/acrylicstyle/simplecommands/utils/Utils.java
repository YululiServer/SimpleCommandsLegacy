package xyz.acrylicstyle.simplecommands.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import util.CollectionList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Utils {
    private Utils() {}

    public static CollectionList<Player> getOnlinePlayers() {
        CollectionList<Player> players = new CollectionList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        return players;
    }

    public static Object getHandle(Object instance) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        try {
            method = instance.getClass().getDeclaredMethod("getHandle");
        } catch (NoSuchMethodException e) {
            method = instance.getClass().getSuperclass().getDeclaredMethod("getHandle");
        }
        method.setAccessible(true);
        return method.invoke(instance);
    }
}
