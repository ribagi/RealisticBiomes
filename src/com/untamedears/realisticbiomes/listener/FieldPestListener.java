package com.untamedears.realisticbiomes.listener;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffectType;

import com.untamedears.realisticbiomes.GrowthConfig;
import com.untamedears.realisticbiomes.RealisticBiomes;

public class FieldPestListener implements Listener {
	private RealisticBiomes plugin;
	private Map<Object, GrowthConfig> growthConfigs;
	
	public FieldPestListener(RealisticBiomes plugin, Map<Object, GrowthConfig> growthConfigs) {
		this.plugin = plugin;
		this.growthConfigs = growthConfigs;
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		// right click on a growing crop with a stick: get information about that crop
		Block block = event.getBlock();
		Material material = block.getType();
		
		GrowthConfig growthConfig = growthConfigs.get(material);
		if (growthConfig != null && growthConfig.testPest()) {
			// Spawn a relatively harmless field pest
			
			Silverfish pest = (Silverfish) event.getBlock().getWorld().spawnEntity(block.getLocation(), EntityType.SILVERFISH);
			pest.setHealth(1.0);
			pest.addPotionEffect(PotionEffectType.SLOW.createEffect(20 * 60 * 15, 1));
			pest.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(20 * 60 * 15, 1));
			if (event.getPlayer() != null) {
				pest.setTarget(event.getPlayer());
			}
		}
	}
}
