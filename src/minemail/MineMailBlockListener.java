package minemail;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.util.config.Configuration;



public class MineMailBlockListener extends BlockListener{
	MineMail plugin;
	
	public MineMailBlockListener(MineMail mineMail){
		plugin = mineMail;
	}
	
	@Override
	public void onBlockDamage(BlockDamageEvent event){
		if(event.getBlock().getType().equals(Material.CHEST)){
			Configuration config = plugin.getConfiguration();
			config.load();
			BlockCoords coords = new BlockCoords(event.getBlock());
			String[] auth = config.getString(coords.getCoords()).split(",");
			boolean damageAllowed = false;
			for(String u : auth) {
				if(event.getPlayer().getDisplayName().toLowerCase().equals(u.toLowerCase())){
					damageAllowed = true;
				}
			}
		
			if(!damageAllowed){
				event.setCancelled(true);
				event.getPlayer().sendMessage("This mailbox belongs to another player");
			}
		}
	}
	
	@Override
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getBlock().getType().equals(Material.CHEST)) {
			BlockCoords coords = new BlockCoords(event.getBlock());
			Configuration config = plugin.getConfiguration();
			config.load();
			plugin.getServer().getPlayer(config.getString(coords.getCoords())).sendMessage("Your mailbox has been deregistered");
			config.removeProperty(coords.getCoords());
			config.save();
		}
	}
	
}