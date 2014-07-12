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
		NAME = plugin.getName();
		VERSION = plugin.getVersion();
		FILE = new File("./plugins/" + plugin.getName() + "/" + config);
		plugin.getDataFolder().mkdir();
		LOG = plugin.getLogger();
		load();
	}
	
	public PluginConfiguration(IRCPlugin plugin) {
		this(plugin, "config.ini");
	}

	@Override
	public String get(String prop) {
		return PROPS.getProperty(prop);
	}

	@Override
	public String get(String prop, String defaultVal) {
		return PROPS.getProperty(prop, defaultVal);
	}

	@Override
	public void set(String prop, String newValue) {
		PROPS.setProperty(prop, newValue);
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
			if (!FILE.exists()) {
				FILE.createNewFile();
				FIRST_TIME_LOAD = true;
			}
			PROPS.load(new FileInputStream(FILE));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Could not load configuration file.");
		}
		
	}

	@Override
	public void save() {
		try {
			PROPS.store(new FileOutputStream(FILE), NAME + " v" + VERSION +  " Configuration File");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Could not save configuration file.");
		}
	}

}
