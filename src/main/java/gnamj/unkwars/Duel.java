package gnamj.unkwars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.Nullable;


public class Duel implements Listener {

    public static @Nullable Duel currentDuel;

    private final Player p1;
    private final Player p2;

    public Duel(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;

        Bukkit.getServer().getPluginManager().registerEvents(this, UnkWars.plugin);
        currentDuel = this;

        initiate();
    }

    private void initiate() {

    }

    private void saveInventories() {

    }

    private void generateField() {

    }

    @EventHandler
    private void onWinCondition() {

        rewardWinner(p1);
        end();
    }

    private void rewardWinner(Player winner) {

    }

    public void end() {


        HandlerList.unregisterAll(this);
        currentDuel = null;
    }

    private void restoreInventories() {

    }

    private void clearField() {

    }
}
