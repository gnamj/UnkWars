package gnamj.unkwars.games;

import gnamj.unkwars.Util;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Random;

public class SkyDuel extends Game {

    private static final String worldName = "SkyDuel";
    private static final int winningScore = 5;

    public SkyDuel(@NotNull Player player1, @NotNull Player player2,
                   @NotNull Location location1, @NotNull Location location2) {
        super(player1, player2);
    }

    @Override
    protected void initialize() {
        generateMap();
        teleportPlayers();
    }

    private void generateMap() {
        generateWorld();
        generateField();
    }

    private void generateWorld() {
        Bukkit.getWorlds().remove(Bukkit.getWorld(worldName));
        Util.deleteDirectory(Bukkit.getWorld(worldName).getWorldFolder());
        Bukkit.getWorlds().remove(Bukkit.getWorld(worldName));

        class EmptyGenerator extends ChunkGenerator {

            @Override
            public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
                ChunkData data = createChunkData(world);
                data.setRegion(0, 15, 0, 15, 1, 1, Material.AIR);
                return data;
            }
        }

        WorldCreator creator = new WorldCreator(worldName)
                .seed(0)
                .type(WorldType.FLAT)
                .generator(new EmptyGenerator())
                .generateStructures(false);

        World world = creator.createWorld();
        world.setSpawnLocation(new Location(world, 0, 0, 0));
        world.setGameRuleValue("logAdminCommands", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("doMobLoot", "false");
        world.setGameRuleValue("spawnRadius", "0");
        world.setPVP(true);
        world.setDifficulty(Difficulty.HARD);
        Bukkit.getWorlds().add(world);
    }

    private void generateField() {

    }

    private void teleportPlayers() {

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

        public static final HashMap<HashMap<@NotNull Player, @NotNull Location>, @NotNull Player>
                duelRequests = new HashMap<>();

        public static final String commandLabel = "duel";

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) return false;

            if (Game.currentGame != null) return false;

            if (args.length != 1) return false;

            Player player = (Player) sender;
            Player challengedPlayer = Bukkit.getPlayer(args[0]);

            if (challengedPlayer == null) return false;

            if (player.getName().equals(challengedPlayer.getName())) return false;
            if (!challengedPlayer.isOnline()) return false;

            if (duelRequests.containsValue(player)) {
                for (HashMap<Player, Location> request : duelRequests.keySet()) {
                    if (request.containsKey(challengedPlayer)) {
                        new SkyDuel(challengedPlayer, player, request.get(challengedPlayer), player.getLocation());
                        return true;
                    }
                }
            }

            HashMap<Player, @NotNull Location> request = new HashMap<>();
            request.put(player, player.getLocation());
            duelRequests.put(request, challengedPlayer);
            return true;
        }
    }
}
