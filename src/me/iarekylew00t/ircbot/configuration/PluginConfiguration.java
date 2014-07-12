package me.iarekylew00t.ircbot.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import ch.qos.logback.classic.Logger;
import me.iarekylew00t.ircbot.plugin.IRCPlugin;

/**
 * Plugin configuration file.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class PluginConfiguration implements Configuration {
	private final String NAME, VERSION;
	private final File FILE;
	private Logger LOG;
	private Properties PROPS = new Properties();
	private boolean FIRST_TIME_LOAD = false;
	
	public PluginConfiguration(IRCPlugin plugin, String config) {
		this.NAME = plugin.getName();
		this.VERSION = plugin.getVersion();
		this.FILE = new File("./plugins/" + plugin.getName() + "/" + config);
		plugin.getDataFolder().mkdir();
		this.LOG = plugin.getLogger();
		this.load();
	}
	
	public PluginConfiguration(IRCPlugin plugin) {
		this(plugin, "config.ini");
	}

	@Override
	public String get(String prop) {
		return this.PROPS.getProperty(prop);
	}

	@Override
	public String get(String prop, String defaultVal) {
		return this.PROPS.getProperty(prop, defaultVal);
	}

	@Override
	public void set(String prop, String newValue) {
		this.PROPS.setProperty(prop, newValue);
	}

	@Override
	public boolean firstTimeLoad() {
		return FIRST_TIME_LOAD;
	}

	@Override
	public void update() {
		this.save();
		this.load();
	}
	
	@Override
	public void reload() {
		this.load();
		this.save();
	}

	@Override
	public void load() {
		try {
			if (!this.FILE.exists()) {
				this.FILE.createNewFile();
				this.FIRST_TIME_LOAD = true;
			}
			this.PROPS.load(new FileInputStream(this.FILE));
		} catch (Exception e) {
			e.printStackTrace();
			this.LOG.error("Could not load configuration file.");
		}
		
	}

	@Override
	public void save() {
		try {
			this.PROPS.store(new FileOutputStream(this.FILE), this.NAME + " v" + this.VERSION +  " Configuration File");
		} catch (Exception e) {
			e.printStackTrace();
			this.LOG.error("Could not save configuration file.");
		}
	}

}
