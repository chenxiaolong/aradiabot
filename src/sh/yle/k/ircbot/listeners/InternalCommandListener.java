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

import java.util.List;

import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import com.google.common.base.Joiner;

import sh.yle.k.ircbot.Aradiabot;
import sh.yle.k.ircbot.IRC;
import sh.yle.k.ircbot.IRCBot;
import sh.yle.k.ircbot.command.Command;
import sh.yle.k.ircbot.hooks.ListenerBase;
import sh.yle.k.ircbot.plugin.IRCPlugin;
import sh.yle.k.utils.ListUtils;

/**
 * An custom Listener that will add and listen for built-in commands
 * such as help, source, etc. These are not included via a plugin, but
 * hardcoded in. 
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public class InternalCommandListener<T extends IRCBot> extends ListenerBase<T> {
	
	/**
	 * InternalCommandListener Constructor.
	 * This is purely used to add internal commands to
	 * the global CommandList so they can be used later.
	 **/
	public InternalCommandListener() {
		Aradiabot.getCommands().add(new Command("version", "Displays the current version of Aradiabot.", "$version", new String[] {"ver"}, IRC.Permission.REGULAR));
		Aradiabot.getCommands().add(new Command("plugins", "Displays a list of all plugins. Green = enabled; Red = disabled.", "$plugins [page]", new String[] {"pl"}, IRC.Permission.REGULAR));
		Aradiabot.getCommands().add(new Command("commands", "Displays a list of all commands.", "$commands [page]", new String[] {"cmds"}, IRC.Permission.REGULAR));
		Aradiabot.getCommands().add(new Command("help", "Displays all the information of a command including the description, usage, aliases, and required permission. Arguments written with <this> are required, [these] are optional.", "$help [command]", new String[] {"h"}, IRC.Permission.REGULAR));
		Aradiabot.getCommands().add(new Command("alias", "Displays all the aliases of a command.", "$alias <command>", new String[0], IRC.Permission.REGULAR));
		Aradiabot.getCommands().add(new Command("shutdown", "Disables all plugins one by one and then stops the bot.", "$shutdown", new String[] {"stop", "exit"}, IRC.Permission.OP));
		Aradiabot.getCommands().add(new Command("source", "Provides a link to Aradiabot's source code.", "$source", new String[] {"src"}, IRC.Permission.REGULAR));
	}
	
	/**
	 * onCommand listener that will be triggered everytime
	 * a CommandEvent is sent to the IRCBot.
	 **/
	@Override
	public void onCommand(User user, Channel channel, Command command, String[] args) {
		if (command.getName().equals("version")) {
			channel.send().message(Aradiabot.NAME + " version " + Aradiabot.VERSION + " (PircBotX: " + Aradiabot.API_VERSION + ")");
		} else if (command.getName().equals("plugins")) {
			Aradiabot.getPlugins().sort(); //Sort IRCPlugins first
			List<List<IRCPlugin>> list = ListUtils.split(Aradiabot.getPlugins(), 25);
			int pages = list.size();
			int plugins = Aradiabot.getCommands().size();
			if (pages == 0) {
				channel.send().message(Colors.RED + Colors.BOLD + "No plugins installed.");
			} else {
				if (args.length == 0) {
					channel.send().message("Plugins (1/" + pages + ") [" + plugins + "]: " + Joiner.on(", ").join(list.get(0)));
				} else if (args.length == 1){
					try {
						int page = Integer.parseInt(args[0]);
						channel.send().message("Plugins (" + page + "/" + pages + ") [" + plugins + "]: " + Joiner.on(", ").join(list.get(page - 1)));
					} catch (NumberFormatException e) {
						channel.send().message(Colors.RED + Colors.BOLD + "Usage: " + command.getUsage());
					} catch (IndexOutOfBoundsException e) {
						channel.send().message(Colors.RED + Colors.BOLD + "Invalid page number; Please choose a number from 1-" + pages + ".");
					}
				} else {
					channel.send().message(Colors.RED + Colors.BOLD + "Too many arguments; Usage: " + command.getUsage());
				}
			}
		} else if (command.getName().equals("commands")) {
			Aradiabot.getCommands().sort(); //Sort Commands first
			List<List<Command>> list = ListUtils.split(Aradiabot.getCommands(), 50);
			int pages = list.size();
			int commands = Aradiabot.getCommands().size();
			if (pages == 0) {
				channel.send().message(Colors.RED + Colors.BOLD + "No commands installed.");
			} else {
				if (args.length == 0) {
					channel.send().message("Commands (1/" + pages + ") [" + commands + "]: " + Joiner.on(", ").join(list.get(0)));
				} else if (args.length == 1){
					try {
						int page = Integer.parseInt(args[0]);
						channel.send().message("Commands (" + page + "/" + pages + ") [" + commands + "]: " + Joiner.on(", ").join(list.get(page - 1)));
					} catch (NumberFormatException e) {
						channel.send().message(Colors.RED + Colors.BOLD + "Usage: " + command.getUsage());
					} catch (IndexOutOfBoundsException e) {
						channel.send().message(Colors.RED + Colors.BOLD + "Invalid page number; Please choose a number from 1-" + pages + ".");
					}
				} else {
					channel.send().message(Colors.RED + Colors.BOLD + "Too many arguments; Usage: " + command.getUsage());
				}
			}
		} else if (command.getName().equals("help")) {
			if (args.length == 0) {
				channel.send().message(command.getDescription() + " - Usage: " + command.getUsage());
			} else if (args.length == 1) {
				Command cmd = Aradiabot.getCommands().find(args[0]);
				if (cmd != null) {
					channel.send().message("[" + cmd.getPermission().toString() + "] " + cmd.getDescription() + " - Usage: " + cmd.getUsage());
				} else {
					channel.send().message(Colors.RED + Colors.BOLD + "'" + args[0] + "' is not a valid command. Use $commands for a list of all available commands.");
				}
			} else {
				channel.send().message(Colors.RED + Colors.BOLD + "Too many arguments; Usage: " + command.getUsage());
			}
		} else if (command.getName().equals("alias")) {
			if (args.length == 0) {
				channel.send().message(Colors.RED + Colors.BOLD + "Not enough arguments; Usage: " + command.getUsage());
			} else if (args.length == 1) {
				Command cmd = Aradiabot.getCommands().find(args[0]);
				if (cmd != null) {
					String[] aliases = cmd.getAliases();
					if (aliases.length == 0) {
						channel.send().message("No aliases for '" + cmd.getName() + "'.");
					} else {
						StringBuilder sb = new StringBuilder();
						for (String alias : aliases) {
							sb.append(alias + ", ");
						}
						sb.setLength(sb.length() - 2);
						
						channel.send().message("Aliases for '" + cmd.getName() + "': " + sb.toString());
					}
				} else {
					channel.send().message(Colors.RED + Colors.BOLD + "'" + args[0] + "' is not a valid command. Use $commands for a list of all available commands.");
				}
			} else {
				channel.send().message(Colors.RED + Colors.BOLD + "Too many arguments; Usage: " + command.getUsage());
			}
		} else if (command.getName().equals("shutdown")) {
			if (IRC.getUserPermissionLevel(channel, user).value() >= command.getPermission().value()) {
				channel.send().message(Colors.BOLD + "Shutting down " + Aradiabot.NAME + "...");
				Aradiabot.shutdown();
			} else {
				channel.send().message(Colors.RED + Colors.BOLD + "You do not have permission to do that. (Req: " + command.getPermission().toString() + ")");
			}
		} else if (command.getName().equals("source")) {
			channel.send().message(Aradiabot.NAME + ": https://github.com/IAreKyleW00t/aradiabot");
		}
	}
}
