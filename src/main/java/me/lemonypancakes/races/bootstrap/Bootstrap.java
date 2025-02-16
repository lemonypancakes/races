package me.lemonypancakes.races.bootstrap;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.bootstrap.PluginProviderContext;
import me.lemonypancakes.races.plugin.FoliaPlugin;
import me.lemonypancakes.races.plugin.PaperPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Bootstrap implements PluginBootstrap {
  @Override
  public void bootstrap(BootstrapContext context) {}

  @Override
  public JavaPlugin createPlugin(PluginProviderContext context) {
    ClassLoader classLoader = getClass().getClassLoader();
    String serverType =
        classLoader.getResource("io/papermc/paper/threadedregions/RegionizedServer.class") != null
            ? "Folia"
            : classLoader.getResource("com/destroystokyo/paper/PaperConfig.class") != null
                ? "Paper"
                : "Unknown (likely Spigot or Bukkit)";

    return switch (serverType) {
      case "Folia" -> new FoliaPlugin();
      case "Paper" -> new PaperPlugin();
      default -> throw new IllegalStateException("Unsupported server type: " + serverType);
    };
  }
}
