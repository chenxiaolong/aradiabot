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
package sh.yle.k.ircbot.plugin;

import java.util.ArrayList;
import java.util.Comparator;

import sh.yle.k.ircbot.IRCBot;

/**
 * A slightly modified ArrayList that includes methods to
 * help improve IRCPlugin storage.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public class PluginList extends ArrayList<IRCPlugin<IRCBot>> {
	private static final long serialVersionUID = 2541945757774840550L;

	/**
	 * Attempts to search for a plugin within the List.
	 * This will also search against aliases.
	 * 
	 * Returns null if a plugin is not found.
	 **/
	public IRCPlugin<IRCBot> find(String plugin) {
		/* Search commands */
		for (IRCPlugin<IRCBot> pl : this) {
			if (pl.getName().equals(plugin)) {
				return pl;
			}
		}
		return null;
	}
	
	/**
	 * Sorts the List using JDK sort(Comparator<T>). We
	 * compare IRCPlugin names so they are sorted alphabetically.
	 **/
	public void sort() {
		this.sort(new Comparator<IRCPlugin<IRCBot>>() {
			@Override
			public int compare(IRCPlugin<IRCBot> pl1, IRCPlugin<IRCBot> pl2) {
				return pl1.getName().compareTo(pl2.getName());
			}
		});
	}
}
