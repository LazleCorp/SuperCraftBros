package org.mcsg.double0negative.supercraftbros;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.mcsg.double0negative.supercraftbros.event.BreakBlock;
import org.mcsg.double0negative.supercraftbros.event.ClickSignEvent;
import org.mcsg.double0negative.supercraftbros.event.InventoryEvents;
import org.mcsg.double0negative.supercraftbros.event.PlayerClassEvents;
import org.mcsg.double0negative.supercraftbros.event.PlayerDamage;
import org.mcsg.double0negative.supercraftbros.event.PlayerJoin;
import org.mcsg.double0negative.supercraftbros.event.PlayerLeave;
import org.mcsg.double0negative.supercraftbros.event.PlayerTeleport;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import java.io.IOException;
import org.mcstats.*;

public class SuperCraftBros extends JavaPlugin{

	@SuppressWarnings("deprecation")
	public void onEnable(){
		try {
				Metrics metrics = new Metrics(this);
				metrics.start();
		} catch (IOException e) {
		}
		SettingsManager.getInstance().setup(this);
		GameManager.getInstance().setup(this);
		
		this.getServer().getPluginManager().registerEvents(new BreakBlock(), this);
		this.getServer().getPluginManager().registerEvents(new ClickSignEvent(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerClassEvents(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryEvents(), this);

		this.getCommand("scb").setExecutor(new CommandHandler(this));
		
		for(Player p:Bukkit.getOnlinePlayers()){
			p.teleport(SettingsManager.getInstance().getLobbySpawn());
			p.getInventory().clear();
			p.getInventory().setArmorContents(new ItemStack[4]);
			p.updateInventory();
			for(PotionEffectType e: PotionEffectType.values()){
				if(e != null && p.hasPotionEffect(e))
					p.removePotionEffect(e);
			}
		}

	}


	public void onDisable(){
		for(Game g:GameManager.getInstance().getGames()){
			g.disable();
		}

	}


	public WorldEditPlugin getWorldEdit() {
		Plugin worldEdit = getServer().getPluginManager().getPlugin("WorldEdit");
		if (worldEdit instanceof WorldEditPlugin) {
			return (WorldEditPlugin) worldEdit;
		} else {
			return null;
		}
	}
}
