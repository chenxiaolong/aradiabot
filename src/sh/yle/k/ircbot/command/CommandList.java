/**
 * Copyright (C) 2015 Kyle Colantonio <kyle10468@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 **/
package sh.yle.k.ircbot.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Global Command list for the IRC bot. This is an enum because
 * there should only ONE instance created.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public enum CommandList {
	INSTANCE;
	private static List<Command> commands = new ArrayList<Command>();
	
	public static Command get(int index) {
		return commands.get(index);
	}
	
	public static Command get(String command) {
		for (Command c : commands) {
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
		return commands.contains(command);
	}
	
	public static boolean contains(String command) {
		return (CommandList.get(command) != null);
	}
	
	public static boolean add(Command command) {
		return commands.add(command);
	}
	
	public static boolean remove(Command command) {
		return commands.remove(command);
	}
	
	public static Command remove(int index) {
		return commands.remove(index);
	}
	
	public static void sort() {
		Collections.sort(commands);
	}
	
	public static final List<Command> getList() {
		return Collections.unmodifiableList(commands);
	}
}
