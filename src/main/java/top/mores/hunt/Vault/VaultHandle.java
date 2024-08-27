package top.mores.hunt.Vault;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import top.mores.hunt.Animals.Level;

public class VaultHandle {
    private static Economy economy;
    Level level = new Level();

    public static boolean setupVault() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    public void addPlayerVault(Player player, double value) {
        if (economy == null) {
            System.out.println("未找到经济插件");
            return;
        }
        EconomyResponse response = economy.depositPlayer(player, value);
        if (response.transactionSuccess()) {
            player.sendMessage(ChatColor.GREEN + "已成功回收获得" + value + "金币");
        } else {
            System.out.println("添加经济失败");
        }
    }

    /**
     * 价值计算
     *
     * @param lv            等阶
     * @param NumberOfTimes 受到伤害次数
     * @return 价值
     */
    public double vaultCalculate(String lv, int NumberOfTimes) {
        return switch (lv) {
            case "普通" -> level.NORMAL_VALUE() * Magnification(NumberOfTimes);
            case "少见" -> level.SCARCE_VALUE() * Magnification(NumberOfTimes);
            case "珍稀" -> level.RARE_VALUE() * Magnification(NumberOfTimes);
            case "史诗" -> level.EPIC_VALUE() * Magnification(NumberOfTimes);
            case "传奇" -> level.LEGEND_VALUE() * Magnification(NumberOfTimes);
            case "神话" -> level.MYTH_VALUE() * Magnification(NumberOfTimes);
            default -> 0.0;
        };
    }

    /**
     * 倍率计算
     *
     * @param count 受到伤害次数
     * @return 倍率
     */
    public double Magnification(int count) {
        return switch (count) {
            case 1, 2 -> 1.5;
            case 3, 4, 5 -> 1.25;
            case 6, 7, 8, 9 -> 1.0;
            case 10, 11, 12, 13 -> 0.75;
            default -> count > 13 ? 0.5 : 0.0;
        };
    }
}
