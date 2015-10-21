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
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * Main IRC bot class that overrides the original PircBotX class.
 * Used to add extra features that are required and to improve
 * functionality of the bot.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public class IRCBot extends PircBotX {
	public static final String NAME = "Aradiabot";
	public static final String VERSION = "1.0.0";
	public static final String API_VERSION = "2.0.1";
	private final File plugins = new File("./plugins");
	private boolean debug = false;
	private final Logger log = (Logger)LoggerFactory.getLogger(IRCBot.class);

	/**
	 * IRCBot Constructor based on the PircBotX Constructor.
	 * Additionally created the plugin directory if needed and
	 * loads internal event listeners.
	 **/
	public IRCBot(Configuration<? extends IRCBot> configuration) {
		super(configuration);
		Aradiabot.setBot(this); //Save our singleton bot
		
		/* Check if plugin directory exists */
		if (!plugins.exists()) {
			plugins.mkdirs();
		}
		
		/* Add internal Event Listeners */
		//TODO
	}
	
	/**
	 * Unloads all plugins and permanently disconnects the
	 * bot from all servers.
	 **/
	public void shutdown() {
		log.info("Shutting down " + IRCBot.NAME + " ...");
		/* Disable all loaded plugins */
		//TODO
		this.stopBotReconnect();
		this.shutdown(true);
		System.exit(IRC.EXIT_SUCCESS);
	}
	
	/**
	 * Synchronized method to send a raw line to the server
	 * when possible.
	 **/
	public synchronized void sendRawLine(String message) {
		this.sendRaw().rawLine(message);
	}
	
	/**
	 * Synchronized method to send a raw line to the server
	 * immediately.
	 **/
	public synchronized void sendRawLineNow(String message) {
		this.sendRaw().rawLineNow(message);
	}
	
	/**
	 * Synchronized method used to send an ACTION, MESSAGE, or NOTICE to
	 * a specified target.
	 **/
	public synchronized void sendToTarget(String target, String message, IRC.Event type) {
		switch (type) {
		case ACTION:
			this.sendIRC().action(target, message);
			break;
		case MESSAGE:
			this.sendIRC().message(target, message);
			break;
		case NOTICE:
			this.sendIRC().notice(target, message);
			break;
		default:
			throw new IllegalArgumentException("ACTION, MESSAGE, and NOTICE are the only acceptable message types.");
		}
	}

	/**
	 * Synchronized method used to send an ACTION, MESSAGE, or NOTICE to
	 * a specified user.
	 **/
	public synchronized void sendToUser(User user, String message, IRC.Event type) {
		switch (type) {
		case ACTION:
			user.send().action(message);
			break;
		case MESSAGE:
			user.send().message(message);
			break;
		case NOTICE:
			user.send().notice(message);
			break;
		default:
			throw new IllegalArgumentException("ACTION, MESSAGE, and NOTICE are the only acceptable message types.");
		}
	}

	/**
	 * Synchronized method used to send an ACTION, MESSAGE, or NOTICE to
	 * a specified channel.
	 **/
	public synchronized void sendToChannel(Channel channel, String message, IRC.Event type) {
		switch (type) {
		case ACTION:
			channel.send().action(message);
			break;
		case MESSAGE:
			channel.send().message(message);
			break;
		case NOTICE:
			channel.send().notice(message);
			break;
		default:
			throw new IllegalArgumentException("ACTION, MESSAGE, and NOTICE are the only acceptable message types.");
		}
	}

	/**
	 * Synchronized method used to send an ACTION, MESSAGE, or NOTICE to
	 * all users currently connected to the IRC bot.
	 **/
	public synchronized void sendToAllUsers(String message, IRC.Event type) {
		switch (type) {
		case ACTION:
			for (User user : this.getUserChannelDao().getAllUsers()) {
				user.send().action(message);
			}
			break;
		case MESSAGE:
			for (User user : this.getUserChannelDao().getAllUsers()) {
				user.send().message(message);
			}
			break;
		case NOTICE:
			for (User user : this.getUserChannelDao().getAllUsers()) {
				user.send().notice(message);
			}
			break;
		default:
			throw new IllegalArgumentException("ACTION, MESSAGE, and NOTICE are the only acceptable message types.");
		}
	}

	/**
	 * Synchronized method used to send an ACTION, MESSAGE, or NOTICE to
	 * all channels the IRC bot is currently connected to.
	 **/
	public synchronized void sendToAllChannels(String message, IRC.Event type) {
		switch (type) {
		case ACTION:
			for (Channel channel : this.getUserChannelDao().getAllChannels()) {
				channel.send().action(message);
			}
			break;
		case MESSAGE:
			for (Channel channel : this.getUserChannelDao().getAllChannels()) {
				channel.send().message(message);
			}
			break;
		case NOTICE:
			for (Channel channel : this.getUserChannelDao().getAllChannels()) {
				channel.send().notice(message);
			}
			break;
		default:
			throw new IllegalArgumentException("ACTION, MESSAGE, and NOTICE are the only acceptable message types.");
		}
	}

	/**
	 * Synchronized method to kick a user from a single channel.
	 * This requires the bot to have OPERATOR status within the channel. 
	 **/
	public synchronized void kick(Channel channel, User user, String reason) {
		channel.send().kick(user, reason);
	}

	/**
	 * Synchronized method to kick a user from all channels.
	 * This requires the bot to have OPERATOR status in all channels
	 * it is connected to. Channels without permission will not perform
	 * the task. 
	 **/
	public synchronized void kickFromAll(User user, String reason) {
		for (Channel c : this.getUserChannelDao().getAllChannels()) {
			c.send().kick(user, reason);
		}
	}

	/**
	 * Synchronized method to ban a hostmask from a single channel.
	 * This requires the bot to have OPERATOR status within the channel. 
	 **/
	public synchronized void ban(Channel channel, String hostmask) {
		channel.send().ban(hostmask);
	}

	/**
	 * Synchronized method to ban a hostmask from all channels.
	 * This requires the bot to have OPERATOR status in all channels
	 * it is connected to. Channels without permission will not perform
	 * the task. 
	 **/
	public synchronized void banFromAll(String hostmask) {
		for (Channel c : this.getUserChannelDao().getAllChannels()) {
			c.send().ban(hostmask);
		}
	}

	/**
	 * Synchronized method to unban a hostmask from a single channel.
	 * This requires the bot to have OPERATOR status within the channel. 
	 **/
	public synchronized void unban(Channel channel, String hostmask) {
		channel.send().unBan(hostmask);
	}

	/**
	 * Synchronized method to unban a hostmask from all channels.
	 * This requires the bot to have OPERATOR status in all channels
	 * it is connected to. Channels without permission will not perform
	 * the task. 
	 **/
	public synchronized void unbanFromAll(String hostmask) {
		for (Channel c : this.getUserChannelDao().getAllChannels()) {
			c.send().unBan(hostmask);
		}
	}
	
	/**
	 * Returns the name of the IRC bot.
	 **/
	public final String getName() {
		return NAME;
	}
	
	/**
	 * Returns the internal version of the IRC bot.
	 **/
	public final String getVersion() {
		return VERSION;
	}
	
	/**
	 * Retruns the PircBotX API version.
	 **/
	public final String getAPIVersion() {
		return API_VERSION;
	}
	
	/**
	 * Returns the plugin directory File.
	 **/
	public final File getPluginDir() {
		return plugins;
	}

	/**
	 * Determines whether the IRC bot has debugging
	 * enabled or not.
	 **/
	public boolean isDebugging() {
		return debug;
	}
	
	/**
	 * Changes the IRC bot's debugging value.
	 **/
	public void debug(boolean value) {
		debug = value;
	}
	
	/**
	 * Returns the IRC bot Logger.
	 **/
	public Logger getLogger() {
		return log;
	}
}
