package top.mores.hunt.Utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

    public void createItems(Player player) {
        ItemStack item = new ItemStack(Material.PAPER);

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            NamespacedKey key = new NamespacedKey("alexsmobs", "bear_fur");
            meta.setCustomModelData(1);

            meta.setDisplayName("测试");
            item.setItemMeta(meta);
        }
        Inventory inventory = player.getInventory();
        inventory.setItem(inventory.firstEmpty(), item);
    }
}
