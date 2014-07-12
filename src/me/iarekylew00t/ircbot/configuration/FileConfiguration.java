package me.iarekylew00t.ircbot.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Standard flatfile configuration.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class FileConfiguration implements Configuration {
	private File FILE;
	private Properties PROPS = new Properties();
	private boolean FIRST_TIME_LOAD = false;
	
	public FileConfiguration(File file) {
		FILE = file;
		load();
	}

	public FileConfiguration(String file) {
		this(new File(file));
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
		}
	}

	@Override
	public void save() {
		try {
			PROPS.store(new FileOutputStream(FILE), FILE.getName() +  " Configuration File");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
