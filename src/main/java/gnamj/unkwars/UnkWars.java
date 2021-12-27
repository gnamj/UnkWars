package gnamj.unkwars;

import org.bukkit.plugin.java.JavaPlugin;

public final class UnkWars extends JavaPlugin {

    @Override
    public void onEnable() {
        confirmUpAndReadyMessage();
    }


    private void confirmUpAndReadyMessage() {
        System.out.println("Up and ready.");
    }
}
