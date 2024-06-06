package dev.thebjoredcraft.simpletimer.timer;

import dev.thebjoredcraft.simpletimer.SimpleTimer;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TimerCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            Timer timer = SimpleTimer.getTimer();

            if(args.length == 2 && args[0].equalsIgnoreCase("start")){
                try {
                    timer.setTime(Integer.parseInt(args[1]));
                    timer.start();

                    player.sendMessage(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + "Du hast den Timer gestartet!"));
                    Bukkit.broadcast(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + SimpleTimer.getInstance().getConfig().getString("broadcast.start", "")));
                }catch (NumberFormatException e){
                    player.sendMessage(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + "Bitte benutze eine kleinere Zahl :D"));
                }
            }else if(args.length == 1 && args[0].equalsIgnoreCase("pause")){
                timer.setPaused(true);

                player.sendMessage(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + "Du hast den Timer pausiert!"));
                Bukkit.broadcast(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + SimpleTimer.getInstance().getConfig().getString("broadcast.pause", "")));
            }else if(args.length == 1 && args[0].equalsIgnoreCase("resume")){
                timer.setPaused(false);

                player.sendMessage(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + "Du hast den Timer fortgesetzt!"));
                Bukkit.broadcast(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + SimpleTimer.getInstance().getConfig().getString("broadcast.resume", "")));
            }else if(args.length == 1 && args[0].equalsIgnoreCase("countdown")){
                timer.setDown(true);

                player.sendMessage(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + "Du hast den Timer auf Minus-Zählen gestellt!"));
                Bukkit.broadcast(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + SimpleTimer.getInstance().getConfig().getString("broadcast.countdown", "")));
            }else if(args.length == 1 && args[0].equalsIgnoreCase("counter")){
                timer.setDown(false);

                player.sendMessage(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + "Du hast den Timer auf Plus-Zählen gestellt!"));
                Bukkit.broadcast(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + SimpleTimer.getInstance().getConfig().getString("broadcast.counter", "")));
            }else if(args.length == 1 && args[0].equalsIgnoreCase("reset")){
                timer.reset();

                player.sendMessage(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + "Du hast den Timer resettet!"));
                Bukkit.broadcast(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + SimpleTimer.getInstance().getConfig().getString("broadcast.reset", "")));
            }else if(args.length == 1 && args[0].equalsIgnoreCase("stop")){
                timer.stop();

                player.sendMessage(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + "Du hast den Timer gestoppt!"));
                Bukkit.broadcast(MiniMessage.miniMessage().deserialize(SimpleTimer.getPrefix() + SimpleTimer.getInstance().getConfig().getString("broadcast.stop", "")));
            }
        }
        return false;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("start", "pause", "resume", "countdown", "counter", "reset", "stop"), new ArrayList<>());
        }
        Collections.sort(completions);
        return completions;
    }
}