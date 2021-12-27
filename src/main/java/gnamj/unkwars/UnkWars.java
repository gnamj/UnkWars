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
        if (Duel.currentDuel != null) {
            Duel.currentDuel.end();
        }
    }

    private void confirmUpAndReadyMessage() {
        System.out.println("Up and ready.");
    }
}
