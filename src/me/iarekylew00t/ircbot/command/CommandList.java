package me.iarekylew00t.ircbot.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Enum that will hold a list of all {@link Command}'s and
 * easily allow adding, removing, and getting of commands.
 * <p>
 * <b>Note:</b> This will not unregister commands when removed.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public enum CommandList {
	INSTANCE;
	private static List<Command> COMMANDS = new ArrayList<Command>();
	
	public static Command get(int command) {
		return COMMANDS.get(command);
	}
	
	public static Command get(String command) {
		for (Command c : COMMANDS) {
			if (c.getName().equals(command)) {
				return c;
			} else {
				for (String a : c.getAliases()) {
					if (a.equals(command)) {
						return c;
					}
				}
			}
		}
		return null;
	}
	
	public static boolean contains(Command command) {
		return COMMANDS.contains(command);
	}
	
	public static boolean contains(String command) {
		for (Command c : COMMANDS) {
			if (c.getName().equals(command)) {
				return true;
			} else {
				for (String a : c.getAliases()) {
					if (a.equals(command)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void add(Command command) {
		COMMANDS.add(command);
	}
	
	public static void add(int index, Command command) {
		COMMANDS.add(index, command);
	}
	
	public static void addAll(Collection<? extends Command> commands) {
		COMMANDS.addAll(commands);
	}
	
	public static void addAll(int index, Collection<? extends Command> commands) {
		COMMANDS.addAll(index, commands);
	}
	
	public static void remove(Command command) {
		COMMANDS.remove(command);
	}
	
	public static void remove(int command) {
		COMMANDS.remove(command);
	}
	
	public static void removeAll(Collection<? extends Command> commands) {
		COMMANDS.removeAll(commands);
	}
	
	public static void retainAll(Collection<? extends Command> commands) {
		COMMANDS.retainAll(commands);
	}
	
	public static void sort() {
		Collections.sort(COMMANDS);
	}
	
	public static List<Command> getList() {
		return COMMANDS;
	}
}
