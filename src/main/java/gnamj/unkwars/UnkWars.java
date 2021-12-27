package gnamj.unkwars;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class UnkWars extends JavaPlugin {

    public static @NotNull UnkWars plugin;

    @Override
    public void onEnable() {
        plugin = this;
        confirmUpAndReadyMessage();
    }

    @Override
    public void onDisable() {
        if (Game.currentGame != null)
            Game.currentGame.end();
    }

    private void confirmUpAndReadyMessage() {
        System.out.println("Up and ready.");
    }
}
