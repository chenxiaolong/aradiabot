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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;
import org.pircbotx.Utils;
import org.pircbotx.hooks.events.MessageEvent;

import sh.yle.k.ircbot.Aradiabot;
import sh.yle.k.ircbot.IRCBot;
import sh.yle.k.ircbot.command.Command;
import sh.yle.k.ircbot.hooks.ListenerBase;
import sh.yle.k.ircbot.hooks.events.CommandEvent;

/**
 * An internal Command Listener that will check all
 * MessageEvents to determine if they are valid and
 * issue global CommandEvents.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public class CommandListener<T extends IRCBot> extends ListenerBase<T> {
	
	/**
	 * Everytime a message is sent, the IRC bot will determine if the message
	 * is a Command, and if so, it will parse the command and then dispatch
	 * a new CommandEvent that can be globally listened for in other Listeners.
	 * 
	 * If the message is not a command, or not a valid command, it will be
	 * skipped and treated like a normal message.
	 **/
	@Override
	public void onMessage(MessageEvent<T> event) throws Exception {
		/* Save basic event information */
		String raw = event.getMessage();
		User user = event.getUser();
		Channel channel = event.getChannel();
		
		/* Only if commands start with $ but aren't empty
		 * ie: "$command" would be valid, but "$" would not. */
		if (raw.matches("^\\$[a-zA-Z]+.*")) {
			String[] arr = raw.split(" ", 2); //Split raw input
			String cmdStr = arr[0].substring(1).toLowerCase(); //Remove leading '$'
			
			/* Log command */
			Aradiabot.getLogger().info(user.getNick() + " issued command: " + cmdStr);

			/* Only work with valid commands */
			Command command = Aradiabot.getCommands().find(cmdStr);
			if (command != null) {
				if (arr.length <= 1) {
					/* Dispatch a new CommandEvent with no arguments */
					Utils.dispatchEvent(event.getBot(), new CommandEvent<T>(event, user, channel, command, new String[0]));
				} else {
					List<String> args = new ArrayList<String>();
					
					/* Split the String by quotes and spaces.
					 * ie: this "is an" example
					 * would be args = {this, is an, example} */
					Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(arr[1]);
					while (matcher.find()) {
						args.add(matcher.group().replaceAll("\"", "").trim());
					}
					
					/* Dispatch a new CommandEvent with our parsed arguments */
					Utils.dispatchEvent(event.getBot(), new CommandEvent<T>(event, user, channel, command, args.toArray(new String[args.size()])));
				}
			} else { //Tell the user the command was invalid.
				channel.send().message(Colors.RED + Colors.BOLD + "'" + cmdStr + "' is not a valid command. Use $commands for a list of all available commands.");
			}
		}
	}
}
