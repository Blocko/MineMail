package minemail;

import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.util.config.Configuration;

public class MineMailPlayerListener extends PlayerListener{
	MineMail plugin;
	
	public MineMailPlayerListener(MineMail mineMail){
		plugin = mineMail;
	}
	
	@Override
	public void onPlayerInteract(PlayerInteractEvent event){
		if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(event.getClickedBlock().getType().equals(Material.CHEST)){
				BlockCoords coords = new BlockCoords(event.getClickedBlock());
				Configuration config = plugin.getConfiguration();
				config.load();
				try {
					if(event.getItem().getType().equals(Material.ARROW))
					{
						//Matt- we could probably make this more object-oriented. Maybe a ChestMail class?
						//Seeing if the chest is already set by accessing it using the coords, if it doesn't return a value then we use the exception catching to register it
						String configcoords = config.getString(coords.getCoords()); //This will return an exception if it can't get the coordinate key
						if(configcoords == null) {
							config.setProperty(coords.getCoords(),event.getPlayer().getDisplayName().toLowerCase());
							config.save();
							event.getPlayer().sendMessage("This chest is now registered as your mailbox.");
							event.setCancelled(true);
						} else {
							event.getPlayer().sendMessage("This chest is already registered.");
							event.setCancelled(true);
						}
					}
					
				} catch(Exception e) {
					//Do nothing
				}
				
				if(!event.getPlayer().getDisplayName().toLowerCase().equals(config.getString(coords.getCoords()))) {
					event.getPlayer().sendMessage("This ain't your mailbox, bro.");
					event.setCancelled(true);
				}
		}
			
			if(event.getClickedBlock().getType().equals(Material.SIGN)){
				Sign sign = (Sign) event.getClickedBlock().getState(); //Added proper casting
				String[] textLines = sign.getLines();
				if(textLines[0].equalsIgnoreCase("[MineMail]")){
					//Send the item to the mailbox of player textLines[1]
	    				if(event.getItem().getAmount() == 1){
	    					event.getPlayer().getInventory().remove(event.getItem());
	    				}
	    				else {
	    						event.getItem().setAmount(event.getItem().getAmount()-1);
	    				}
				}
			}
		}
	}
}
