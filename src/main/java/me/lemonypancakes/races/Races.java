package me.lemonypancakes.races;

public final class Races {
    private static RacesPlugin plugin;

    private Races() {
    }

    public static RacesPlugin getPlugin() {
        return plugin;
    }

    public static void setPlugin(final RacesPlugin plugin) {
        if (Races.plugin != null) throw new RuntimeException();
        Races.plugin = plugin;
    }
}
