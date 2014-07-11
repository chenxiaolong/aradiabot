package me.iarekylew00t.ircbot.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.iarekylew00t.ircbot.Aradiabot;
import me.iarekylew00t.ircbot.command.Command;
import me.iarekylew00t.ircbot.command.CommandList;
import me.iarekylew00t.ircbot.hooks.events.CommandEvent;
import me.iarekylew00t.ircbot.plugin.PluginBase;

import org.pircbotx.Utils;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Custom Listener that will dispatch {@link CommandEvent}'s 
 * when message begins with a "$" symbol and parse information accordingly.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class CommandListener extends PluginBase {

	@Override
	public void onMessage(MessageEvent e) throws Exception {
		String messageRaw = e.getMessage();
		if (messageRaw.equals("$")) {
			return;
		}
		
		if (messageRaw.startsWith("$")) {
			String[] arr = messageRaw.split(" ", 2);
			String cmd = arr[0].substring(1).toLowerCase();
			Aradiabot.getLogger().info(e.getUser().getNick() + " issused command: " + cmd);

			if (CommandList.contains(cmd)) {
				Command command = CommandList.get(cmd);
				if (arr.length <= 1) {
					Utils.dispatchEvent(e.getBot(), new CommandEvent(e, e.getUser(), e.getChannel(), command, new String[0]));
				} else {
					List<String> args = new ArrayList<String>();
					Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(arr[1]);
					while (m.find()) {
						args.add(m.group(1).replaceAll("\"", "").trim());
					}
					Utils.dispatchEvent(e.getBot(), new CommandEvent(e, e.getUser(), e.getChannel(), command, args.toArray(new String[args.size()])));
				}
			}
		}
	}
}
