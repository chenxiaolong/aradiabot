package me.iarekylew00t.ircbot.command;

import java.util.ArrayList;
import java.util.List;

import me.iarekylew00t.ircbot.IRC;

/**
 * Creates and holds all command information including: name,
 * description, usage, aliases, and permissions.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class Command implements Comparable {
	private String NAME, DESC, USAGE;
	private List<String> ALIASES;
	private int PERM;
	
	public Command(String name) {
		this(name, "", "$" + name, new ArrayList<String>(), IRC.REGULAR);
	}
	
	public Command(String name, String desc, String usage) {
		this(name, desc, usage, new ArrayList<String>(), IRC.REGULAR);
	}
	
	public Command(String name, String desc, String usage, List<String> aliases) {
		this(name, desc, usage, aliases, IRC.REGULAR);
	}
	
	public Command(String name, String desc, String usage, List<String> aliases, int perm) {
		NAME = name;
		DESC = desc;
		USAGE = usage;
		if (aliases == null) {
			ALIASES = new ArrayList<String>();
		} else {
			ALIASES = new ArrayList<String>(aliases);
		}
		PERM = perm;
	}
	
	public boolean isRegistered() {
		return false;
	}
	
	public String getName() {
		return NAME;
	}
	
	public String getDescription() {
		return DESC;
	}
	
	public String getUsage() {
		return USAGE;
	}
	
	public List<String> getAliases() {
		return ALIASES;
	}
	
	public int getPermission() {
		return PERM;
	}
	
	public void setDescription(String desc) {
		DESC = desc;
	}
	
	public void setUsage(String usage) {
		USAGE = usage;
	}
	
	public void setAliases(List<String> aliases) {
		ALIASES = new ArrayList<String>(aliases);
	}
	
	public void setPermission(int perm) {
		PERM = perm;
	}
	
	@Override
	public String toString() {
		return NAME;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof String) {
			return NAME.equals((String) o);
		} else if (o instanceof Command) {
			return NAME.equals(((Command) o).getName());
		}
		return false;
	}

	@Override
	public int compareTo(Object o) {
		return NAME.compareTo(((Command) o).getName());
	}
}
