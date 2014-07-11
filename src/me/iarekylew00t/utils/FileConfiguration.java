package me.iarekylew00t.utils;

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
		this.FILE = file;
		this.load();
	}

	public FileConfiguration(String file) {
		this(new File(file));
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
		}
	}

	@Override
	public void save() {
		try {
			this.PROPS.store(new FileOutputStream(this.FILE), this.FILE.getName() +  " Configuration File");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
