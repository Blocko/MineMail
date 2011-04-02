package minemail;

import org.bukkit.event.Event.*;
import org.bukkit.event.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MineMail extends JavaPlugin {
	
	private final MineMailPlayerListener playerListener = new MineMailPlayerListener(this);
	private final MineMailBlockListener blockListener = new MineMailBlockListener(this);
	
	public void onDisable(){
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!" );
	}
	
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
    	pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
    	pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Priority.Normal, this);
    	pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Priority.Normal, this);
    	pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Normal, this);
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
}
