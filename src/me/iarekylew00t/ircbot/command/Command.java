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
	
	public Command(String name, String desc, String usage, List<String> aliases, int perm) {
		this.NAME = name;
		this.DESC = desc;
		this.USAGE = usage;
		this.ALIASES = new ArrayList<String>(aliases);
		this.PERM = perm;
	}
	
	public boolean isRegistered() {
		return false;
	}
	
	public String getName() {
		return this.NAME;
	}
	
	public String getDescription() {
		return this.DESC;
	}
	
	public String getUsage() {
		return this.USAGE;
	}
	
	public List<String> getAliases() {
		return this.ALIASES;
	}
	
	public int getPermission() {
		return this.PERM;
	}
	
	public void setDescription(String desc) {
		this.DESC = desc;
	}
	
	public void setUsage(String usage) {
		this.USAGE = usage;
	}
	
	public void setAliases(List<String> aliases) {
		this.ALIASES = new ArrayList<String>(aliases);
	}
	
	public void setPermission(int perm) {
		this.PERM = perm;
	}
	
	@Override
	public String toString() {
		return this.NAME;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof String) {
			return this.NAME.equals((String) o);
		} else if (o instanceof Command) {
			return this.NAME.equals(((Command) o).getName());
		}
		return false;
	}

	@Override
	public int compareTo(Object o) {
		return this.NAME.compareTo(((Command) o).getName());
	}
}
