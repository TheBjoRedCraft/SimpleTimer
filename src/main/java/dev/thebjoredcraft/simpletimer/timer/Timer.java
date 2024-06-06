package dev.thebjoredcraft.simpletimer.timer;

import dev.thebjoredcraft.simpletimer.SimpleTimer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {
    private boolean down;
    private boolean paused;
    private int seconds;
    private int minutes;
    private int hours;
    private int days;
    private String format;
    private String output;
    private final JavaPlugin plugin;
    private BukkitRunnable task;

    public Timer(JavaPlugin plugin) {
        this.down = false;
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
        this.days = 0;
        this.format = null;//TODO CONFIG
        this.output = "";
        this.plugin = plugin;
        this.paused = true;
    }

    public void start() {
        if (task != null) {
            task.cancel();
        }
        setPaused(false);

        task = new BukkitRunnable() {
            @Override
            public void run() {
                if(!isPaused()) {
                    if (down) {
                        decrement();
                    } else {
                        increment();
                    }
                }else{
                    setOutput(SimpleTimer.getInstance().getConfig().getString("paused-message", ""));
                }
                for(Player player : Bukkit.getOnlinePlayers()){
                    player.sendActionBar(MiniMessage.miniMessage().deserialize(getOutput()));
                }
            }
        };
        task.runTaskTimer(plugin, 0L, 20L);
    }


    public void stop() {
        if (task != null) {
            task.cancel();
        }
    }
    public void setTime(int totalSeconds) {
        this.days = totalSeconds / 86400;
        totalSeconds %= 86400;
        this.hours = totalSeconds / 3600;
        totalSeconds %= 3600;
        this.minutes = totalSeconds / 60;
        this.seconds = totalSeconds % 60;
    }

    private void increment() {
        seconds++;
        if (seconds >= 60) {
            seconds = 0;
            minutes++;
            if (minutes >= 60) {
                minutes = 0;
                hours++;
                if (hours >= 24) {
                    hours = 0;
                    days++;
                }
            }
        }
        this.setOutput(formatTime());
    }

    private void decrement() {
        if (seconds > 0 || minutes > 0 || hours > 0 || days > 0) {
            seconds--;
            if (seconds < 0) {
                seconds = 59;
                minutes--;
                if (minutes < 0) {
                    minutes = 59;
                    hours--;
                    if (hours < 0) {
                        hours = 23;
                        days--;
                    }
                }
            }
        }
        this.setOutput(formatTime());
    }

    private String formatTime() {
        String timeFormat = SimpleTimer.getInstance().getConfig().getString("time-format", "");

        return timeFormat
                .replace("%d", String.valueOf(days)
                .replace("%h", String.valueOf(hours))
                .replace("%m", String.valueOf(minutes))
                .replace("%s", String.valueOf(seconds)));
    }

    public boolean isDown() {
        return down;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public String getFormat() {
        return format;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public BukkitRunnable getTask() {
        return task;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void reset(){
        this.down = false;
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
        this.days = 0;
        this.format = null;//TODO CONFIG
        this.output = "";
        this.paused = true;
    }
}