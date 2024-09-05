package top.mores.hunt;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.mores.hunt.Utils.ItemUtil;

public class HuntCommand implements CommandExecutor {

    ItemUtil itemUtil=new ItemUtil();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player sender) {
            if (strings.length == 1 && strings[0].equals("reload")) {
                if (sender.isOp()) {
                    Hunt.getInstance().reloadConfig();
                    Hunt.getInstance().reloadData();
                    sender.sendMessage("配置文件已重新加载");
                } else {
                    sender.sendMessage(ChatColor.RED + "你没有执行该命令的权限");
                }
            } else if (strings.length == 1 && strings[0].equals("create")) {
                itemUtil.createItems(sender);
                sender.sendMessage("创建测试");
            }
        }
        return true;
    }
}
