package xyz.acrylicstyle.simplecommands.utils;

import util.yaml.YamlArray;
import util.yaml.YamlObject;
import xyz.acrylicstyle.simplecommands.SimpleCommands;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Utils {
    private Utils() {}

    private static final Map<Integer, Map.Entry<String, String>> RESOURCE_PACKS = new HashMap<>();
    private static String DEFAULT_RESOURCE_PACK = null;

    @SuppressWarnings("unchecked")
    // label, url
    public static Map.Entry<String, String> getResourcePackURL(int protocolVersion) {
        if (DEFAULT_RESOURCE_PACK == null) {
            DEFAULT_RESOURCE_PACK = SimpleCommands.config.getString("texture");
        }
        if (RESOURCE_PACKS.isEmpty()) {
            SimpleCommands.config.getList("textures", new ArrayList<>()).forEach(o -> {
                if (o instanceof Map) {
                    YamlObject object = new YamlObject((Map<String, Object>) o); // this really isn't yaml, but it can be used.
                    YamlArray arr = object.getArray("target");
                    String url = object.getString("url");
                    if (url != null) {
                        if (arr != null) {
                            arr.<Integer>forEachAsType(i -> RESOURCE_PACKS.put(i, new AbstractMap.SimpleImmutableEntry<>(object.getString("label"), url)));
                        } else {
                            DEFAULT_RESOURCE_PACK = url;
                        }
                    }
                }
            });
        }
        Map.Entry<String, String> res = RESOURCE_PACKS.get(protocolVersion);
        return res == null ?
                ( DEFAULT_RESOURCE_PACK == null ? null : new AbstractMap.SimpleImmutableEntry<>("Default", DEFAULT_RESOURCE_PACK) )
                : res;
    }
}
