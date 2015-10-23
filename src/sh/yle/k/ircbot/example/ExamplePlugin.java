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
package sh.yle.k.ircbot.example;

import org.pircbotx.Channel;
import org.pircbotx.User;

import sh.yle.k.ircbot.IRC;
import sh.yle.k.ircbot.command.Command;
import sh.yle.k.ircbot.plugin.IRCPlugin;

public class ExamplePlugin extends IRCPlugin {
	private static final String NAME = "ExamplePlugin";
	private static final String VERSION = "1.0.0";
	private static final String AUTHOR = "IAreKyleW00t";
	private static final String DESCRIPTION = "An example IRCPlugin.";
	
	public ExamplePlugin() {
		super(NAME, VERSION, AUTHOR, DESCRIPTION);
	}
	
	@Override
	public boolean onEnable() {
		this.addCommand(new Command("example", "An example command in an example plugin.", "$example", new String[] {"ex"}, IRC.Permission.REGULAR));
		return true;
	}
	
	@Override
	public void onCommand(User user, Channel channel, Command command, String[] args) {
		if (command.getName().equals("example")) {
			if (args.length == 0) {
				channel.send().message("If you see this, that means the example plugin is working! :)");
				this.getLogger().info("You can also see we log some text to the console. Neat!");
			} else {
				channel.send().message("Whoops! Looks like you used too many arguments. Usage: " + command.getUsage());
			}
		}
	}
}
