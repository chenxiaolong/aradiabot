package me.iarekylew00t.ircbot.listeners;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.Colors;
import org.pircbotx.User;

import me.iarekylew00t.ircbot.Aradiabot;
import me.iarekylew00t.ircbot.IRC;
import me.iarekylew00t.ircbot.command.Command;
import me.iarekylew00t.ircbot.command.CommandList;
import me.iarekylew00t.ircbot.plugin.IRCPlugin;
import me.iarekylew00t.ircbot.plugin.PluginBase;
import me.iarekylew00t.utils.ListUtils;

/**
 * Handles all internal commands. Eg: help, version, etc.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class InternalCommandListener extends PluginBase {
	
	public InternalCommandListener() {
		CommandList.add(new Command("version", "Displays the current version of Aradiabot.", "$version", Arrays.asList("ver"), IRC.REGULAR));
		CommandList.add(new Command("plugins", "Displays the list of all plugins. Green indicates a working plugin, red indicates an error.", "$plugins [page]", Arrays.asList("pl"), IRC.REGULAR));
		CommandList.add(new Command("commands", "Displays a list of all commands.", "$commands [page]", Arrays.asList("cmd"), IRC.REGULAR));
		CommandList.add(new Command("help", "Displays all information of a given command including the description, usage, and the required permission. Arguments written with <this> are required, [these] are optional.", "$help [command]"));
		CommandList.add(new Command("alias", "Displays all aliases of a command.", "$alias <command>"));
		CommandList.add(new Command("shutdown", "Disables all plugins and stops the bot.", "$shutdown", Arrays.asList("stop", "exit"), IRC.OP));
		CommandList.add(new Command("source", "Provides a link to Aradiabot's source code.", "$source", Arrays.asList("src"), IRC.REGULAR));
	}

	@Override
	public void onCommand(User user, Channel channel, Command command, String[] args) throws Exception {
		if (command.getName().equalsIgnoreCase("version")) {
			channel.send().message(Aradiabot.getName() + " version " + Aradiabot.getVersion() + " (PircBotX: " + Aradiabot.getAPIVersion() + ")");
		} else if (command.getName().equalsIgnoreCase("plugins")) {
			List<List<IRCPlugin>> pl = ListUtils.split(Aradiabot.getPlugins(), 25);
			int size = Aradiabot.getPlugins().size();
			int pages = pl.size();
			String[] page = new String[pages];
			for (int i = 0; i < pages; i++) {
				List<IRCPlugin> p = pl.get(i);
				Collections.sort(p);
				page[i] = "";
				for (int j = 0; j < p.size(); j++) {
					if (p.get(j).isEnabled()) {
						page[i] += Colors.GREEN + p.get(j).getName() + Colors.NORMAL + ", ";
					} else {
						page[i] += Colors.RED + p.get(j).getName() + Colors.NORMAL + ", ";
					}
				}
				page[i] = StringUtils.removeEnd(page[i], ", ");
			}
			if (pages == 0) {
				channel.send().message("No plugins installed.");
				return;
			}
			if (args.length == 0) {
				channel.send().message("Plugins (1/" + pages + ") [" + size + "]: " + page[0]);
			} else if (args.length == 1) {
				int index = 1;
				try {
					index = Integer.parseInt(args[0]);
				} catch (Exception e) {
					channel.send().message(Colors.RED + Colors.BOLD + "Please enter a number from 1-" + pages + "; Usage: " + command.getUsage());
					return;
				}
				if (index > pages) {
					channel.send().message(Colors.RED + Colors.BOLD + "Please enter a number from 1-" + pages + "; Usage: " + command.getUsage());
					return;
				}
				channel.send().message("Plugins (" + index + "/" + pages + ") [" + size + "]: " + page[index - 1]);
			} else {
				channel.send().message(Colors.RED + Colors.BOLD + "Too many arguments; Usage: " + command.getUsage());
			}
		} else if (command.getName().equalsIgnoreCase("commands")) {
			CommandList.sort();
			List<List<Command>> cmds = ListUtils.split(Aradiabot.getCommands(), 50);
			int size = Aradiabot.getCommands().size();
			int pages = cmds.size();
			String[] page = new String[pages];
			for (int i = 0; i < pages; i++) {
				List<Command> c = cmds.get(i);
				page[i] = "";
				for (int j = 0; j < c.size(); j++) {
					page[i] += c.get(j).getName() + ", ";
				}
				page[i] = StringUtils.removeEnd(page[i], ", ");
			}
			if (pages == 0) {
				channel.send().message("No commands installed.");
				return;
			}
			if (args.length == 0) {
				channel.send().message("Commands (1/" + pages + ") [" + size + "]: " + page[0]);
			} else if (args.length == 1) {
				int index = 1;
				try {
					index = Integer.parseInt(args[0]);
				} catch (Exception e) {
					channel.send().message(Colors.RED + Colors.BOLD + "Please enter a number from 1-" + pages + "; Usage: " + command.getUsage());
					return;
				}
				if (index > pages) {
					channel.send().message(Colors.RED + Colors.BOLD + "Please enter a number from 1-" + pages + "; Usage: " + command.getUsage());
					return;
				}
				channel.send().message("Commands (" + index + "/" + pages + ") [" + size + "]: " + page[index - 1]);
			} else {
				channel.send().message(Colors.RED + Colors.BOLD + "Too many arguments; Usage: " + command.getUsage());
			}
		} else if (command.getName().equalsIgnoreCase("help")) {
			if (args.length == 1) {
				String cmd = args[0];
				if (CommandList.contains(cmd)) {
					Command c = CommandList.get(cmd);
					channel.send().message("[" + IRC.permissionToString(c.getPermission()) + "] " + c.getDescription() + " Usage: " + c.getUsage());
				} else {
					channel.send().message(Colors.RED + Colors.BOLD + "\"" + cmd + "\" is not a valid command. Use $commands for a list of all available commands.");
				}
			} else if (args.length == 0) {
				channel.send().message(command.getDescription() + " - Usage: " + command.getUsage());
			} else {
				channel.send().message(Colors.RED + Colors.BOLD + "Too many arguments; Usage: " + command.getUsage());
			}
		} else if (command.getName().equalsIgnoreCase("alias")) {
			if (args.length == 1) {
				String cmd = args[0];
				if (CommandList.contains(cmd)) {
					Command c = CommandList.get(cmd);
					List<String> a = c.getAliases();
					if (a.isEmpty()) {
						channel.send().message("No aliases for " + c.getName());
					}
					String aliases = "";
					for (int i = 0; i < a.size(); i++) {
						aliases += a.get(i) + ", ";
					}
					aliases = StringUtils.removeEnd(aliases, ", ");
					channel.send().message("Aliases for " + c.getName() + ": " + aliases);
				} else {
					channel.send().message(Colors.RED + Colors.BOLD + "\"" + cmd + "\" is not a valid command. Use $commands for a list of all valid commands.");
				}
			} else if (args.length == 0) {
				channel.send().message(Colors.RED + Colors.BOLD + "Not enough arguments; Usage: " + command.getUsage());
			} else {
				channel.send().message(Colors.RED + Colors.BOLD + "Too many arguments; Usage: " + command.getUsage());
			}
		} else if (command.getName().equalsIgnoreCase("shutdown")) {
			if (IRC.getPermissionLevel(channel, user) == command.getPermission()) {
				channel.send().message("Shutting down Aradiabot...");
				Aradiabot.shutdown();
			} else {
				channel.send().message(user, Colors.RED + Colors.BOLD + "You do not have permission to do that. (Req: " + IRC.permissionToString(command.getPermission()) + ")");
			}
		} else if (command.getName().equalsIgnoreCase("source")) {
			channel.send().message(Aradiabot.getName() + ": https://github.com/IAreKyleW00t/Aradiabot");
		}
	}
}
