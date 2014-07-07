package me.iarekylew00t.ircbot.listeners;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.pircbotx.Channel;
import org.pircbotx.User;

import me.iarekylew00t.ircbot.Aradiabot;
import me.iarekylew00t.ircbot.command.Command;
import me.iarekylew00t.ircbot.command.CommandList;
import me.iarekylew00t.ircbot.hooks.PluginBase;
import me.iarekylew00t.ircbot.utils.IRC;

/**
 * Handles all internal commands. Eg: help, version, etc.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class InternalCommandListener extends PluginBase {
	
	public InternalCommandListener() {
		CommandList.add(new Command("version", "Displays the current version of Aradiabot.", "$version", Arrays.asList("ver"), IRC.REGULAR));
		CommandList.add(new Command("plugins", "Displays the list of plugins. Green indicates a working plugin, red indicates an error.", "$plugins [page]", Arrays.asList("pl"), IRC.REGULAR));
		CommandList.add(new Command("commands", "Displays a list of commands. Commands you cannot use will not be shown.", "$commands [page]", Arrays.asList("cmd"), IRC.REGULAR));
		CommandList.add(new Command("help", "Displays the usage information for a command.", "$help <command>"));
		CommandList.add(new Command("shutdown", "Disables all plugins and stops the bot.", "$shutdown", Arrays.asList("stop", "exit"), IRC.OP));
	}

	@Override
	public void onCommand(User user, Channel channel, Command command, String[] args) {
		if (command.equals("version")) {
			channel.send().message(Aradiabot.getName() + " v" + Aradiabot.getVersion());
		} else if (command.equals("plugins")) {
			channel.send().message("Plugins (" +  Aradiabot.getPlugins().size() + "): " + StringUtils.join(Aradiabot.getPlugins()));
		} else if (command.equals("commands")) {
			channel.send().message("Commands (" +  Aradiabot.getCommands().size() + "): " + StringUtils.join(Aradiabot.getCommands()));
		} else if (command.equals("shutdown")) {
			Aradiabot.shutdown();
		}
	}
}
