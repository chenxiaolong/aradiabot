package me.iarekylew00t.ircbot;

import java.io.File;
import java.util.Collection;
import java.util.List;

import me.iarekylew00t.ircbot.command.Command;
import me.iarekylew00t.ircbot.command.CommandList;
import me.iarekylew00t.ircbot.plugin.IRCPlugin;
import me.iarekylew00t.ircbot.plugin.PluginList;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.UserLevel;

import com.google.common.collect.ImmutableSortedSet;

import ch.qos.logback.classic.Logger;

/**
 * Represents Aradiabot's core. Used for bot singleton handling.
 * Directly controls the initial {@link IRCBot} instance.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public enum Aradiabot {
	INSTANCE;
	private static IRCBot BOT;
	
	public static final void setBot(IRCBot bot) {
		if (BOT != null) {
			throw new UnsupportedOperationException("Cannot redefine singleton bot");
		}
		BOT = bot;
		bot.getLogger().info("This bot is running " + getName() + " version " + getVersion() + " (PircBotX API version " + getAPIVersion() + ")");
	}
	
	public static final IRCBot getBot() {
		return BOT;
	}

	public static final String getName() {
		return BOT.getName();
	}
	public static final String getNick() {
		return BOT.getNick();
	}
	
	public static final String getVersion() {
		return BOT.getVersion();
	}
	
	public static final String getAPIVersion() {
		return BOT.getAPIVersion();
	}
	
	public static final File getPluginDir() {
		return BOT.getPluginDir();
	}
	
	public static final int getBotId() {
		return BOT.getBotId();
	}
	
	public static final boolean isDebugging() {
		return BOT.isDebugging();
	}
	
	public static final void setDebug(boolean val) {
		BOT.setDebug(val);
	}
	
	public static final Collection<? extends User> getAllUsers() {
		return BOT.getUserChannelDao().getAllUsers();
	}
	
	public static final Collection<? extends User> getUsers(Channel channel) {
		return BOT.getUserChannelDao().getUsers(channel);
	}
	
	public static final User getUser(String user) {
		for (User u : BOT.getUserChannelDao().getAllUsers()) {
			if (u.getNick().equals(user)) {
				return u;
			}
		}
		return null;
	}

	public static final Collection<? extends Channel> getAllChannels() {
		return BOT.getUserChannelDao().getAllChannels();
	}
	
	public static final Collection<? extends Channel> getChannels(User user) {
		return BOT.getUserChannelDao().getChannels(user);
	}
	
	public static final Channel getChannel(String channel) {
		for (Channel c : BOT.getUserChannelDao().getAllChannels()) {
			if (c.getName().equals(channel)) {
				return c;
			}
		}
		return null;
	}
	
	public static final String getChannelMode(Channel channel) {
		return channel.getMode();
	}
	
	public static final ImmutableSortedSet<UserLevel> getUserLevels(Channel channel, User user) {
		return channel.getUserLevels(user);
	}
	
	public static final String getServer() {
		return BOT.getConfiguration().getServerHostname();
	}
	
	public static final int getPort() {
		return BOT.getConfiguration().getServerPort();
	}
	
	public static final List<Command> getCommands() {
		return CommandList.getList();
	}
	
	public static final List<IRCPlugin> getPlugins() {
		return PluginList.getList();
	}
	
	public static final Logger getLogger() {
		return BOT.getLogger();
	}
	
	public static final void shutdown() {
		BOT.shutdown();
	}

	public static final void kick(Channel channel, User user) {
		BOT.kick(channel, user);
	}
	
	public static final void kick(Channel channel, User user, String reason) {
		BOT.kick(channel, user, reason);
	}
	
	public static final void kickFromAll(User user) {
		BOT.kickFromAll(user);
	}
	
	public static final void kickFromAll(User user, String reason) {
		BOT.kickFromAll(user, reason);
	}
	
}
