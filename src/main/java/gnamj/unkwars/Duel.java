package gnamj.unkwars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class Duel implements Listener {

    public static @Nullable Duel currentDuel;

    private static final int winningScore = 5;

    private final Player p1;
    private int score1 = 0;
    private ItemStack[] inv1;

    private final Player p2;
    private int score2 = 0;
    private ItemStack[] inv2;

    public Duel(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;

        inv1 = new ItemStack[p1.getInventory().getContents().length];
        inv2 = new ItemStack[p2.getInventory().getContents().length];

        Bukkit.getServer().getPluginManager().registerEvents(this, UnkWars.plugin);
        currentDuel = this;

        initiate();
    }

    private void initiate() {

    }

    private void saveInventories() {
        ItemStack[] tempInv1 = new ItemStack[inv1.length];
        ItemStack[] tempInv2 = new ItemStack[inv2.length];

        for (int i = 0; i < inv1.length; i++) {
            inv1[i] = p1.getInventory().getContents()[i];
            tempInv1[i] = p1.getInventory().getContents()[i].clone();
            inv2[i] = p2.getInventory().getContents()[i];
            tempInv2[i] = p2.getInventory().getContents()[i].clone();
        }

        p1.getInventory().setContents(tempInv1);
        p2.getInventory().setContents(tempInv2);
    }

    private void generateField() {

    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        if (!event.getEntity().equals(p1) && !event.getEntity().equals(p2)) return;

        if (event.getEntity().equals(p1) && p1.getKiller() != null && p1.getKiller().equals(p2))
            score2++;
        else if (event.getEntity().equals(p2) && p2.getKiller() != null && p2.getKiller().equals(p1))
            score1++;

        checkWinCondition();
    }

    private void checkWinCondition() {
        if (score1 == winningScore) {
            rewardWinner(p1);
            end();
        }
        else if (score2 == winningScore) {
            rewardWinner(p2);
            end();
        }
    }

    private void rewardWinner(Player winner) {

    }

    public void end() {

        clearField();
        HandlerList.unregisterAll(this);
        currentDuel = null;
    }

    private void restoreInventories() {
        p1.getInventory().setContents(inv1);
        p2.getInventory().setContents(inv2);
    }

    private void clearField() {

    }

    @EventHandler
    private void onPlayerLeave(PlayerQuitEvent event)
    {
        if (!event.getPlayer().equals(p1) && !event.getPlayer().equals(p2))
            return;

        if (event.getPlayer().equals(p1))
            score2 = winningScore;
        else if (event.getPlayer().equals(p2))
            score1 = winningScore;

        checkWinCondition();
    }
}
