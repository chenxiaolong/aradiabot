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

import org.apache.commons.lang3.Validate;

import sh.yle.k.ircbot.IRC;

/**
 * Command object used to hold all information related
 * to commands. All data is final once set and should not
 * be changed once created.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public class Command {
	private final String name, description, usage;
	private final String[] aliases;
	private final IRC.Permission permission;
	
	/**
	 * Command Constructor that will save keep track of corresponding
	 * command information. These values are final and should not
	 * be modified after being set.
	 **/
	public Command(final String name, final String description, final String usage, final String[] aliases, final IRC.Permission permission) {
		Validate.matchesPattern(name, "^[a-zA-Z]+.*", "'%s' cannot start with a number", name);
		this.name = name;
		this.description = description;
		this.usage = usage;
		this.aliases = aliases;
		this.permission = permission;
	}
	
	/**
	 * Returns the name of the command. This is what the
	 * actual command is.
	 **/
	public final String getName() {
		return name;
	}
	
	/**
	 * Returns a basic description of the command.
	 **/
	public final String getDescription() {
		return description;
	}
	
	/**
	 * Returns the usage of the command.
	 * ie: command <required> [optional]
	 **/
	public final String getUsage() {
		return usage;
	}
	
	/**
	 * Returns an Array of aliases for the command.
	 * These will normally never be reference since
	 * aliases will be handled internally.
	 **/
	public final String[] getAliases() {
		return aliases;
	}
	
	/**
	 * Returns the minimum required permission that is
	 * need to successfully execute the command. Insufficient
	 * permissions will be handled internally.
	 **/
	public final IRC.Permission getPermission() {
		return permission;
	}
	
	/**
	 * Returns the String representation of the Command
	 * which will always be the name.
	 **/
	@Override
	public String toString() {
		return name;
	}
}
