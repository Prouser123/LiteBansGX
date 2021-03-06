package me.noodles.gui.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.noodles.gui.commands.Punish;
import me.noodles.gui.inv.ClickEvents;
import me.noodles.gui.main.updatechecker.JoinExample;
import me.noodles.gui.main.updatechecker.UpdateChecker;

import org.bstats.bukkit.Metrics;

public class Main extends JavaPlugin
{
	private UpdateChecker checker;
    public static Main plugin;

    public void onEnable() {
    	PluginDescriptionFile VarUtilType = this.getDescription();
		this.getLogger().info("LiteBansGUI V" + VarUtilType.getVersion() + " starting...");
		this.getLogger().info("LiteBansGUI V" + VarUtilType.getVersion() + " loading commands and config files...");
        this.createFiles();
        this.registerEvents();
        this.registerCommands();
        plugin = this;
        Metrics metrics = new Metrics(this);
        this.setEnabled(true);
		getLogger().info("LiteBansGUI V" + VarUtilType.getVersion() + " started!");
		this.getLogger().info("LiteBansGUI V" + VarUtilType.getVersion() + " checking for updates...");
        if (getConfig().getBoolean("Update.Enabled")) {
    		this.checker = new UpdateChecker(this);
    		
    		if (this.checker.isConnected()) {
    			if (this.checker.hasUpdate()) {
    				getServer().getConsoleSender().sendMessage("------------------------");
    				getServer().getConsoleSender().sendMessage("LiteBansGUI is outdated!");
    				getServer().getConsoleSender().sendMessage("Newest version: " + this.checker.getLatestVersion());
    				getServer().getConsoleSender().sendMessage("Your version: " + getDescription().getVersion());
    				getServer().getConsoleSender().sendMessage("Please Update Here: https://www.spigotmc.org/resources/52072");
    				getServer().getConsoleSender().sendMessage("------------------------");
    			} else {
    				getServer().getConsoleSender().sendMessage("------------------------");
    				getServer().getConsoleSender().sendMessage("LiteBansGUI is up to date!");
    				getServer().getConsoleSender().sendMessage("------------------------");
    			}
    		}
        } else {
        	getServer().getConsoleSender().sendMessage("The LiteBansGUI update checker has been disabled in the config.");
        }
	}


    
    
    public void registerEvents() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Punish(), this);
        pm.registerEvents(new ClickEvents(), this);
        pm.registerEvents(new JoinExample(), this);
    }
    public void registerCommands() {
    	this.getCommand("punish").setExecutor(new Punish());

    }
    
    private File configf, guiitems, banreason;
    private FileConfiguration config, guiitems1, banreason1;


    public FileConfiguration getguiitems1Config() {
        return this.guiitems1;
    }

    public FileConfiguration getbanreason1Config() {
        return this.banreason1;
    }
    
    
    private void createFiles() {
        configf = new File(getDataFolder(), "config.yml");
        guiitems = new File(getDataFolder(), "guiitems.yml");
        banreason = new File(getDataFolder(), "banreason.yml");

        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        if (!guiitems.exists()) {
            guiitems.getParentFile().mkdirs();
            saveResource("guiitems.yml", false);
         }
        if (!banreason.exists()) {
            banreason.getParentFile().mkdirs();
            saveResource("banreason.yml", false);
         }

        config = new YamlConfiguration();
        guiitems1 = new YamlConfiguration();
        banreason1 = new YamlConfiguration();
        try {
            config.load(configf);
            guiitems1.load(guiitems);
            banreason1.load(banreason);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    
}
