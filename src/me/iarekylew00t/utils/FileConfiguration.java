package me.iarekylew00t.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Standard flatfile configuration.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class FileConfiguration {
	private File FILE;
	private Properties PROPS = new Properties();
	private boolean FIRST_TIME_LOAD = false;
	
	public FileConfiguration(File file) {
		this.FILE = file;
		this.load();
	}

	public FileConfiguration(String file) {
		this(new File(file));
		this.load();
	}
	
	public String get(String prop) {
		return this.PROPS.getProperty(prop);
	}
	
	public String get(String prop, String defaultVal) {
		return this.PROPS.getProperty(prop, defaultVal);
	}
	
	public void set(String prop, String newValue) {
		this.PROPS.setProperty(prop, newValue);
	}
	
	public boolean firstTimeLoad() {
		return FIRST_TIME_LOAD;
	}
	
	public void update() {
		this.save();
		this.load();
	}
	
	public void load() {
		try {
			if (!this.FILE.exists()) {
				this.FILE.createNewFile();
				this.FIRST_TIME_LOAD = true;
			}
			this.PROPS.load(new FileInputStream(this.FILE));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void save() {
		try {
			this.PROPS.store(new FileOutputStream(this.FILE), FILE.getName() +  " Configuration File");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
