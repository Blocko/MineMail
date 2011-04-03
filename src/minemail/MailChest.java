package minemail;

import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

public class MailChest {
	
	private Block chestblock;
	private Configuration config;
	private BlockCoords coords;
	
	public MailChest(Block b, Configuration c) {
		chestblock = b;
		config = c;
		config.load();
		coords = new BlockCoords(chestblock);
	}
	
	public void setOwner(Player player) {
		config.setProperty(coords.getCoords(),player.getDisplayName().toLowerCase());
		config.save();
	}
	
	public String getOwnerName() {
		String playername = config.getString(coords.getCoords());
		return playername;
	}
	
	public void deregister() {
		config.removeProperty(coords.getCoords());
	}

}
