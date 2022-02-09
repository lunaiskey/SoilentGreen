package me.lunaiskey.soilentgreen;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;


public class VillagerDeath implements Listener {

    private final String prefix = SoilentGreen.getPrefix();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onVillagerDeathByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Villager) {
            Villager villager = (Villager) e.getEntity();
            int x = (int) villager.getLocation().getX();
            int y = (int) villager.getLocation().getY();
            int z = (int) villager.getLocation().getZ();

            if (e.getDamage() >= villager.getHealth()) {
                Bukkit.getScheduler().runTask(SoilentGreen.getInstance(),()-> {
                    if (villager.isDead()) {
                        onVillagerKill(villager,x,y,z,e.getDamager(),e.getCause());
                    }
                });
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onVillagerDeath(EntityDamageEvent e) {
        if (e.getEntity() instanceof Villager) {
            Villager villager = (Villager) e.getEntity();
            int x = (int) villager.getLocation().getX();
            int y = (int) villager.getLocation().getY();
            int z = (int) villager.getLocation().getZ();
            //Villager should be Killed.
            if (e.getDamage() >= villager.getHealth()) {
                //checks if the villager is actually dead, compat with other plugins that stop entity death for some reason
                Bukkit.getScheduler().runTask(SoilentGreen.getInstance(),()-> {
                    if (villager.isDead()) {
                        switch (e.getCause()) {
                            case ENTITY_ATTACK:
                            case ENTITY_EXPLOSION:
                            case ENTITY_SWEEP_ATTACK:
                                break;
                            default:
                                onVillagerKill(villager,x,y,z,null,e.getCause());
                        }
                    }
                });
            }
        }
    }

    private void onVillagerKill(Villager villager, int x, int y, int z, Entity lastDamager, EntityDamageEvent.DamageCause damage) {
        String killer = lastDamager != null ? lastDamager.getName() : "null";
        switch (damage) {
            case ENTITY_ATTACK:
            case ENTITY_EXPLOSION:
            case ENTITY_SWEEP_ATTACK:
                Bukkit.getLogger().info(prefix+ChatColor.GREEN+" Taco Meat ("+villager.getName()+") was harvested at "+ChatColor.YELLOW+x+", "+y+", "+z+ChatColor.GREEN+" by "+ChatColor.WHITE+killer+ChatColor.GREEN+"! Happy Munching!");
                break;
            case FIRE:
            case FIRE_TICK:
            case LAVA:
            case HOT_FLOOR:
                Bukkit.getLogger().info(prefix+ChatColor.GREEN+" Taco Meat ("+villager.getName()+") was harvested and cooked at "+ChatColor.YELLOW+x+", "+y+", "+z+ChatColor.GREEN+"! Happy Munching!");
                break;
            default:
                Bukkit.getLogger().info(prefix+ChatColor.GREEN+" Taco Meat ("+villager.getName()+") was harvested at "+ChatColor.YELLOW+x+ ", "+y+", "+z+ChatColor.GREEN+"! Happy Munching!");
        }
    }
}
