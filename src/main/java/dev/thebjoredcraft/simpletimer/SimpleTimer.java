package dev.thebjoredcraft.simpletimer;

import dev.thebjoredcraft.simpletimer.timer.Timer;
import dev.thebjoredcraft.simpletimer.timer.TimerCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleTimer extends JavaPlugin {
    private static SimpleTimer instance;
    private static Timer timer;
    private static final String prefix = "<gray>>> <color:#00ff48>S</color><color:#a2ff00>T</color> <gray>| <color:#3b92d1>";

    @Override
    public void onLoad() {
        instance = this;

    }

    @Override
    public void onEnable() {
        timer = new Timer(this);

        saveDefaultConfig();

        getCommand("simpletimer").setExecutor(new TimerCommand());
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        saveDefaultConfig();
        // Plugin shutdown logic
    }

    public static SimpleTimer getInstance() {
        return instance;
    }

    public static Timer getTimer() {
        return timer;
    }

    public static String getPrefix() {
        return prefix;
    }
}
