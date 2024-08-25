package top.mores.hunt;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HuntCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            if (strings.length == 1 && strings[0].equals("reload")) {
                Player sender = (Player) commandSender;
                if (sender.isOp()) {
                    Hunt.getInstance().reloadConfig();
                    Hunt.getInstance().reloadData();
                } else {
                    sender.sendMessage(ChatColor.RED + "你没有执行该命令的权限");
                }
            }
        }
        return true;
    }
}
