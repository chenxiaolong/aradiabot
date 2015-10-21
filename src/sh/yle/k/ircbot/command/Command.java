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

import sh.yle.k.ircbot.IRC;

public class Command implements Comparable<Command> {
	private final String name, description, usage;
	private final List<String> aliases;
	private final IRC.Permission permission;
	
	public Command(String name) {
		this(name, "", IRC.COMMAND_CHARACTER + name, null, IRC.Permission.REGULAR);
	}
	
	public Command(String name, String description, String usage) {
		this(name, description, usage, null, IRC.Permission.REGULAR);
	}
	
	public Command(String name, String description, String usage, List<String> aliases) {
		this(name, description, usage, aliases, IRC.Permission.REGULAR);
	}
	
	public Command(String name, String description, String usage, List<String> aliases, IRC.Permission permission) {
		this.name = name;
		this.description = description;
		this.usage = usage;
		if (aliases == null) {
			this.aliases = new ArrayList<String>();
		} else {
			this.aliases = new ArrayList<String>(aliases);
		}
		this.permission = permission;
	}
	
	public final String getName() {
		return name;
	}
	
	public final String getDescription() {
		return description;
	}
	
	public final String getUsage() {
		return usage;
	}
	
	public final List<String> getAliases() {
		return Collections.unmodifiableList(aliases);
	}
	
	public final IRC.Permission getPermission() {
		return permission;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof String) {
			String str = (String) o;
			return name.equals(str);
		} else if (o instanceof Command) {
			Command cmd = (Command) o;
			return name.equals(cmd.getName());
		} else {
			return name.equals(o.toString());
		}
	}
	
	@Override
	public int compareTo(Command command) {
		return name.compareTo(command.getName());
	}
}
