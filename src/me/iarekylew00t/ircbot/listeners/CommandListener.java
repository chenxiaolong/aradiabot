package me.iarekylew00t.ircbot.listeners;

import me.iarekylew00t.ircbot.command.Command;
import me.iarekylew00t.ircbot.command.CommandList;
import me.iarekylew00t.ircbot.hooks.PluginBase;
import me.iarekylew00t.ircbot.hooks.events.CommandEvent;

import org.pircbotx.Utils;
import org.pircbotx.hooks.events.MessageEvent;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * Custom Listener that will dispatch {@link CommandEvent}'s 
 * when message begins with a "$" symbol and parse information accordingly.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class CommandListener extends PluginBase {

	@Override
	public void onMessage(MessageEvent e) throws Exception {
		String messageRaw = e.getMessage();
		if (messageRaw.startsWith("$")) {
			String[] arr = messageRaw.split(" ", 2);
			String cmd = arr[0].substring(1);
			LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME).info(e.getUser().getNick() + " issused command: " + cmd);

			if (CommandList.contains(cmd)) {
				Command command = CommandList.get(cmd);
				if (arr.length <= 1) {
					Utils.dispatchEvent(e.getBot(), new CommandEvent(e, e.getUser(), e.getChannel(), command, new String[0]));
				} else {
					Utils.dispatchEvent(e.getBot(), new CommandEvent(e, e.getUser(), e.getChannel(), command, arr[1].split(" ")));
				}
			} else {
				e.respond(cmd + " is not a valid command.");
			}
		}
	}
}
