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

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A slightly modified CopyOnWriteArrayList that includes methods to
 * help improve Command storage. We need to use CopyOnWriteArrayList
 * in order to avoid concurrency issues.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public class CommandList extends CopyOnWriteArrayList<Command> {
	private static final long serialVersionUID = -6476761547212818778L;
	
	/**
	 * Attempts to search for a command within the List.
	 * This will also search against aliases.
	 * 
	 * Returns null if a command is not found.
	 **/
	public Command find(String command) {
		/* Search commands */
		for (Command cmd : this) {
			if (cmd.getName().equals(command)) {
				return cmd;
			} else {
				/* Search aliases */
				for (String alias : cmd.getAliases()) {
					if (alias.equals(command)) {
						return cmd;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Sorts the List using JDK sort(Comparator<T>). We
	 * compare Command names so they are sorted alphabetically.
	 **/
	public void sort() {
		this.sort(new Comparator<Command>() {
			@Override
			public int compare(Command cmd1, Command cmd2) {
				return cmd1.getName().compareTo(cmd2.getName());
			}
		});
	}
}
