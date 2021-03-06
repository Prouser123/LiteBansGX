package me.noodles.gui.main.updatechecker;

import org.bukkit.event.player.*;

import me.noodles.gui.main.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class JoinExample implements Listener {
	
	public UpdateChecker checker;
	
    @EventHandler
    public void onJoin(final PlayerJoinEvent e) {
    	Player p = e.getPlayer();
    	if (p.hasPermission("litebansgui.update") && Main.plugin.getConfig().getBoolean("Update.ShowOnJoin")) {
    		this.checker = new UpdateChecker(Main.plugin);
    		if (this.checker.isConnected() && this.checker.hasUpdate()) {
    			p.sendMessage(ChatColor.GRAY + "=========================");
    			p.sendMessage(ChatColor.RED + "LiteBansGUI is outdated!");
    			p.sendMessage(ChatColor.GREEN + "Newest version: " + this.checker.getLatestVersion());
    			p.sendMessage(ChatColor.RED + "Your version: " + Main.plugin.getDescription().getVersion());
    			p.sendMessage(ChatColor.GRAY + "=========================");
    		}
    	}
    }
}
    