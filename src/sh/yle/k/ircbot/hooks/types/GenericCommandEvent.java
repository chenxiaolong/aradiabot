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
package sh.yle.k.ircbot.hooks.types;

import sh.yle.k.ircbot.IRCBot;
import sh.yle.k.ircbot.command.Command;

/**
 * Generic Command Event Interface.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public interface GenericCommandEvent<T extends IRCBot> {
	public Command getCommand();
	public String[] getArgs();
}
