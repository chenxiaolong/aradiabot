package me.iarekylew00t.utils;

/**
 * Basic configuration interface.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public interface Configuration {
	public String get(String prop);
	public String get(String prop, String defaultVal);
	public void set(String prop, String newValue);
	public boolean firstTimeLoad();
	public void update();
	public void reload();
	public void load();
	public void save();
}
