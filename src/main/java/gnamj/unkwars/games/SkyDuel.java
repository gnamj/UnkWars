package gnamj.unkwars.games;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public class SkyDuel extends Game {

    private static final int winningScore = 5;

    public SkyDuel(@NotNull Player player1, @NotNull Player player2) {
        super(player1, player2);
    }

    @Override
    protected void initialize() {

    }

    private void checkWinCondition() {
        if (scores[0] == winningScore)
            end(getParticipant(0));
        else if (scores[1] == winningScore)
            end(getParticipant(1));
    }

    @Override
    protected void rewardWinners(@NotNull Player... winners) {

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!event.getEntity().equals(getParticipant(0)) && !event.getEntity().equals(getParticipant(1)))
            return;

        if (event.getEntity().equals(getParticipant(0))
                && getParticipant(0).getKiller() != null
                && getParticipant(0).getKiller().equals(getParticipant(1)))
            scores[1]++;
        else if (event.getEntity().equals(getParticipant(1))
                && getParticipant(1).getKiller() != null
                && getParticipant(1).getKiller().equals(getParticipant(0)))
            scores[0]++;

        checkWinCondition();
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (!event.getPlayer().equals(getParticipant(0)) && !event.getPlayer().equals(getParticipant(1)))
            return;

        if (event.getPlayer().equals(getParticipant(0))) end(getParticipant(1));
        else if (event.getPlayer().equals(getParticipant(1))) end(getParticipant(0));
    }

    public static class SkyDuelExecutor implements CommandExecutor {

        public static final String commandLabel = "duel";

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            return false;
        }
    }
}
