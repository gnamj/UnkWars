package gnamj.unkwars;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Game implements Listener {

    public static @Nullable Game currentGame;

    private final @NotNull Player[] participants;
    public final int[] scores;
    private final ItemStack[][] inventories;

    public Game(@NotNull Player... participants) {
        if (participants.length < 2) throw new IllegalArgumentException();

        this.participants = participants;
        this.scores = new int[participants.length];
        this.inventories = new ItemStack[participants.length][participants[0].getInventory().getContents().length];

        Bukkit.getServer().getPluginManager().registerEvents(this, UnkWars.plugin);

        currentGame = this;
        initiate();
    }

    public @NotNull Player[] getParticipants() {
        return participants;
    }

    public @NotNull Player getParticipant(int index) throws IndexOutOfBoundsException {
        return participants[index];
    }

    private void initiate() {
        saveInventories();
        initialize();
    }

    protected abstract void initialize();

    private void saveInventories() {
        for (int i = 0; i < inventories.length; i++) {
            ItemStack[] currentContents = participants[i].getInventory().getContents();
            ItemStack[] tempInventory = new ItemStack[inventories[0].length];

            for (int j = 0; j < inventories[0].length; j++) {
                inventories[i][j] = currentContents[j];
                tempInventory[j] = currentContents[j].clone();
            }

            participants[i].getInventory().setContents(tempInventory);
        }
    }

    public final void end(@NotNull Player... winners) {
        HandlerList.unregisterAll(this);
        restoreInventories();
        if (winners.length != 0)
            rewardWinners(winners);
    }

    protected void restoreInventories() {
        for (int i = 0; i < participants.length; i++)
            participants[i].getInventory().setContents(inventories[i]);
    }

    protected abstract void rewardWinners(@NotNull Player... winners);
}
