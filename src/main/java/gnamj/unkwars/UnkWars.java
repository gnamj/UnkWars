package gnamj.unkwars;

import gnamj.unkwars.games.Game;
import gnamj.unkwars.games.SkyDuel;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class UnkWars extends JavaPlugin {

    public static @NotNull UnkWars plugin;

    @Override
    public void onEnable() {
        plugin = this;
        confirmUpAndReadyMessage();
        setGameCommands();
    }

    @Override
    public void onDisable() {
        if (Game.currentGame != null)
            Game.currentGame.end();
    }

    private void confirmUpAndReadyMessage() {
        System.out.println("Up and ready.");
    }

    private void setGameCommands() {
        getServer().getPluginCommand(SkyDuel.SkyDuelExecutor.commandLabel).setExecutor(new SkyDuel.SkyDuelExecutor());
    }
}