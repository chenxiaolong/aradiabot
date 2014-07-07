package me.iarekylew00t.ircbot.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Enum that will hold a list of all {@link IRCPlugin}'s and
 * easily allow adding, removing, and getting of plugins.
 * <p>
 * <b>Note:</b> This will not unregister plugins when removed.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public enum PluginList {
	INSTANCE;
	private static List<IRCPlugin> PLUGINS = new ArrayList<IRCPlugin>();
	
	public static IRCPlugin get(int plugin) {
		return PLUGINS.get(plugin);
	}
	
	public static IRCPlugin get(String plugin) {
		for (IRCPlugin c : PLUGINS) {
			if (c.getName().equals(plugin)) {
				return c;
			}
		}
		return null;
	}
	
	public static boolean contains(IRCPlugin plugin) {
		return PLUGINS.contains(plugin);
	}
	
	public static boolean contains(String plugin) {
		for (IRCPlugin c : PLUGINS) {
			if (c.getName().equals(plugin)) {
				return true;
			}
		}
		return false;
	}
	
	public static void add(IRCPlugin plugin) {
		PLUGINS.add(plugin);
	}
	
	public static void add(int index, IRCPlugin plugin) {
		PLUGINS.add(index, plugin);
	}
	
	public static void addAll(Collection<? extends IRCPlugin> plugins) {
		PLUGINS.addAll(plugins);
	}
	
	public static void addAll(int index, Collection<? extends IRCPlugin> plugins) {
		PLUGINS.addAll(index, plugins);
	}
	
	public static void remove(IRCPlugin plugin) {
		PLUGINS.remove(plugin);
	}
	
	public static void remove(int plugin) {
		PLUGINS.remove(plugin);
	}
	
	public static void removeAll(Collection<? extends IRCPlugin> plugins) {
		PLUGINS.removeAll(plugins);
	}
	
	public static void retainAll(Collection<? extends IRCPlugin> plugins) {
		PLUGINS.retainAll(plugins);
	}
	
	public static void sort() {
		Collections.sort(PLUGINS);
	}
	
	public static List<IRCPlugin> getList() {
		return PLUGINS;
	}
}
