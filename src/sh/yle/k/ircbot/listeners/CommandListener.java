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
package sh.yle.k.ircbot.listeners;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.events.MessageEvent;

import sh.yle.k.ircbot.Aradiabot;
import sh.yle.k.ircbot.IRC;
import sh.yle.k.ircbot.IRCBot;
import sh.yle.k.ircbot.hooks.ListenerBase;

public class CommandListener<T extends IRCBot> extends ListenerBase<T> {
	
	@Override
	public void onMessage(MessageEvent<T> event) throws Exception {
		String raw = event.getMessage();
		User user = event.getUser();
		Channel channel = event.getChannel();
		
		/* Only if commands start with IRC.COMMAND_CHARACTER but aren't empty
		 * ie: "$command" would be valid, but "$" would not. */
		if (raw.startsWith(IRC.COMMAND_CHARACTER) && raw.equals(IRC.COMMAND_CHARACTER)) {
			String[] array = raw.split(" ", 2); //Split raw input
			String commandString = array[0].substring(1).toLowerCase(); //Parse command
			Aradiabot.getLogger().info(user.getNick() + " issued command: " + commandString); //Log command
			
			/* Only work with valid commands */
		}
	}
}
