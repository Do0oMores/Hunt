package top.mores.hunt.EventListener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.projectiles.ProjectileSource;
import top.mores.hunt.Hunt;
import top.mores.hunt.Vault.VaultHandle;

import java.util.HashMap;
import java.util.Map;

public class AnimalListener implements Listener {

    private final Map<Entity, Integer> hitCountMap = new HashMap<>();
    private final Map<Entity, Player> lastAttackerMap = new HashMap<>();
    VaultHandle vaultHandle = new VaultHandle();

    @EventHandler
    public void onAnimalDeath(EntityDeathEvent event) {
        Entity DeathEntity = event.getEntity();
        int hitCount = hitCountMap.getOrDefault(DeathEntity, 0);
        Player lastAttacker = lastAttackerMap.get(DeathEntity);
        String deathEntityName = DeathEntity.getName();
        if (lastAttacker != null && Hunt.getInstance().getEntityValueMap().containsKey(deathEntityName)) {
            String lv = Hunt.getInstance().getEntityLevelMap().get(deathEntityName);
            double value = vaultHandle.vaultCalculate(lv, hitCount);
            lastAttacker.sendMessage("你击杀了" + deathEntityName + "，共攻击了" + hitCount + "次。" + "级别" + lv + ",价值：" + value);
        }

        hitCountMap.remove(DeathEntity);
        lastAttackerMap.remove(DeathEntity);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity.isDead()) return;

        hitCountMap.put(entity, hitCountMap.getOrDefault(entity, 0) + 1);

        if (event.getDamager() instanceof Player) {
            lastAttackerMap.put(entity, (Player) event.getDamager());
        } else if (event.getDamager() instanceof Projectile projectile) {
            ProjectileSource shooter = projectile.getShooter();
            if (shooter instanceof Player) {
                lastAttackerMap.put(entity, (Player) shooter);
            }
        }
    }
}
