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
	
	public static final IRCBot getBot() {
		return BOT;
	}
	
	public static void setBot(IRCBot bot) {
		if (BOT != null) {
			throw new UnsupportedOperationException("Cannot redefine singleton IRCBot");
		}
		BOT = bot;
		bot.getLogger().info("This bot is running " + getName() + " version " + getVersion() + " (PircBotX API version " + getAPIVersion() + ")");
	}

	public static String getName() {
		return BOT.getName();
	}
	public static String getNick() {
		return BOT.getNick();
	}
	
	public static String getVersion() {
		return BOT.getVersion();
	}
	
	public static String getAPIVersion() {
		return BOT.getAPIVersion();
	}
	
	public static File getPluginDir() {
		return BOT.getPluginDir();
	}
	
	public static int getBotId() {
		return BOT.getBotId();
	}
	
	public static boolean isDebugging() {
		return BOT.isDebugging();
	}
	
	public static void setDebug(boolean val) {
		BOT.setDebug(val);
	}
	
	public static Collection<? extends User> getAllUsers() {
		return BOT.getUserChannelDao().getAllUsers();
	}
	
	public static Collection<? extends User> getUsers(Channel channel) {
		return BOT.getUserChannelDao().getUsers(channel);
	}
	
	public static User getUser(String user) {
		for (User u : BOT.getUserChannelDao().getAllUsers()) {
			if (u.getNick().equals(user)) {
				return u;
			}
		}
		return null;
	}

	public static Collection<? extends Channel> getAllChannels() {
		return BOT.getUserChannelDao().getAllChannels();
	}
	
	public static Collection<? extends Channel> getChannels(User user) {
		return BOT.getUserChannelDao().getChannels(user);
	}
	
	public static Channel getChannel(String channel) {
		for (Channel c : BOT.getUserChannelDao().getAllChannels()) {
			if (c.getName().equals(channel)) {
				return c;
			}
		}
		return null;
	}
	
	public static String getChannelMode(Channel channel) {
		return channel.getMode();
	}
	
	public static ImmutableSortedSet<UserLevel> getUserLevels(Channel channel, User user) {
		return channel.getUserLevels(user);
	}
	
	public static String getServer() {
		return BOT.getConfiguration().getServerHostname();
	}
	
	public static int getPort() {
		return BOT.getConfiguration().getServerPort();
	}
	
	public static List<Command> getCommands() {
		return CommandList.getList();
	}
	
	public static List<IRCPlugin> getPlugins() {
		return PluginList.getList();
	}
	
	public static Logger getLogger() {
		return BOT.getLogger();
	}
	
	public static void shutdown() {
		BOT.shutdown();
	}

	public static void kick(Channel channel, User user) {
		BOT.kick(channel, user);
	}
	
	public static void kick(Channel channel, User user, String reason) {
		BOT.kick(channel, user, reason);
	}
	
	public static void kickFromAll(User user) {
		BOT.kickFromAll(user);
	}
	
	public static void kickFromAll(User user, String reason) {
		BOT.kickFromAll(user, reason);
	}
	
}
