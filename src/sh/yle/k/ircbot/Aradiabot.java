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
package sh.yle.k.ircbot;

import java.io.File;

import org.pircbotx.Channel;
import org.pircbotx.User;
import ch.qos.logback.classic.Logger;
import sh.yle.k.ircbot.command.CommandList;

/**
 * Aradiabot singleteon used to control the main IRCBot
 * outside of an accessible scope.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public final class Aradiabot {
	public static final String NAME = "Aradiabot";
	public static final String VERSION = "1.0.0";
	public static final String API_VERSION = "2.0.1";
	private static IRCBot bot;
	
	public static final void setBot(IRCBot ircbot) {
		if (bot != null) {
			throw new UnsupportedOperationException("Cannot redefine singleton IRC bot.");
		}
		bot = ircbot;
		bot.getLogger().info("This bot is running " + NAME + " version " + VERSION + " (PircBotX API version " + API_VERSION + ")");
	}
	
	public static final IRCBot getBot() {
		return bot;
	}
	
	public static final int getBotId() {
		return bot.getBotId();
	}
	
	public static final String getServer() {
		return bot.getConfiguration().getServerHostname();
	}
	
	public static final int getPort() {
		return bot.getConfiguration().getServerPort();
	}
	
	public static final String getNick() {
		return bot.getNick();
	}
	
	public static final File getPluginDir() {
		return bot.getPluginDir();
	}
	
	public static final Logger getLogger() {
		return bot.getLogger();
	}
	
	public static final void shutdown() {
		bot.shutdown();
	}
	
	public static final CommandList getCommands() {
		return bot.getCommands();
	}
	
	public static final boolean isDebugging() {
		return bot.isDebugging();
	}
	
	public static final void debug(boolean value) {
		bot.debug(value);
	}

	public synchronized void sendToTarget(String target, String message, IRC.Event type) {
		bot.sendToTarget(target, message, type);
	}
	
	public synchronized void sendToUser(User user, String message, IRC.Event type) {
		bot.sendToUser(user, message, type);
	}
	
	public synchronized void sendToChannel(Channel channel, String message, IRC.Event type) {
		bot.sendToChannel(channel, message, type);
	}
	
	public synchronized void sendToAllUsers(String message, IRC.Event type) {
		bot.sendToAllUsers(message, type);
	}
	
	public synchronized void sendToAllChannels(String message, IRC.Event type) {
		bot.sendToAllChannels(message, type);
	}
}
