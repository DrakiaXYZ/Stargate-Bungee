package net.TheDgtl.StargateBungee;

import net.md_5.bungee.Logger;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.plugin.JavaPlugin;
import net.md_5.bungee.plugin.PluginMessageEvent;
import net.md_5.bungee.plugin.PluginMessageEvent.Destination;

public class StargateBungee extends JavaPlugin {
	private Logger log;
	@Override
	public void onEnable() {
		log = Logger.$();
		
		// Register our global plugin channel
		BungeeCord.instance.registerPluginChannel("SGBungee");
		
		log.info("[SGB] Enabled v0.0.2");
	}
	
	@Override
	public void onPluginMessage(PluginMessageEvent event) {
		if (!event.getTag().equals("SGBungee")) return;
		if (event.getDestination() != Destination.CLIENT) return;
		
		// Split the message to get server/gate
		String[] data = event.getData().split("@#@");
		String server = data[0];
		String gate = data[1];
		
		// Switch server, and setup tag to kill old connection
		event.getConnection().connect(server);
		event.setTag("KillCon");
		
		// Send a message to Stargate on the new server to teleport the player
		event.getConnection().sendPluginMessage("SGBungee", gate.getBytes());
	}
}
