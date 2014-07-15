package me.iarekylew00t.ircbot;

import java.io.File;

import me.iarekylew00t.ircbot.exceptions.RegisteredPluginException;
import me.iarekylew00t.ircbot.exceptions.UnRegisteredPluginException;
import me.iarekylew00t.ircbot.listeners.CommandListener;
import me.iarekylew00t.ircbot.listeners.InternalCommandListener;
import me.iarekylew00t.ircbot.plugin.IRCPlugin;
import me.iarekylew00t.ircbot.plugin.PluginList;

import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * An improved version of {@link PircBotX}. This adds more
 * features and offers better compatibility with plugins.
 * <p>
 * Extend {@link PircBotX} if you wish to create your
 * own version of IRCBot.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class IRCBot extends PircBotX {
	public static final String VERSION = "2.1.0";
	public static final String NAME = "Aradiabot";
	public static final String PIRCBOTX_VERSION = "2.0.1";
	private static final File PLUGIN_DIR = new File("./plugins");
	private boolean DEBUG = false;
	private final Logger LOG = (Logger) LoggerFactory.getLogger(IRCBot.class);
	
	public IRCBot(Configuration<IRCBot> configuration) {
		super(configuration);
		Aradiabot.setBot(this);
		if (!PLUGIN_DIR.exists()) {
			PLUGIN_DIR.mkdir();
		}
		this.getConfiguration().getListenerManager().addListener(new CommandListener());
		this.getConfiguration().getListenerManager().addListener(new InternalCommandListener());
	}
	
	public void addPlugin(IRCPlugin pl) throws RegisteredPluginException {
		if (PluginList.contains(pl)) {
			throw new RegisteredPluginException("Plugin '" + pl.getName() + "' is already registered.");
		}
		this.getConfiguration().getListenerManager().addListener(pl);
		PluginList.add(pl);
	}
	
	public void removePlugin(IRCPlugin pl) throws UnRegisteredPluginException {
		if (!PluginList.contains(pl)) {
			throw new UnRegisteredPluginException("Plugin '" + pl.getName() + "' is not registered.");
		}
		this.getConfiguration().getListenerManager().removeListener(pl);
		PluginList.remove(pl);
	}
	
	public synchronized void sendRawMessage(String message) {
		this.sendRawMessage(message);
	}

	public synchronized void sendMessage(String target, String message) {
		this.sendIRC().message(target, message);
	}

	public synchronized void sendMessage(User user, String notice) {
		user.send().message(notice);
	}

	public synchronized void sendMessage(Channel channel, String message) {
		channel.send().message(message);
	}
	
	public synchronized void sendMessageToAll(String message) {
		for (Channel c : this.getUserChannelDao().getAllChannels()) {
			c.send().message(message);
		}
	}

	public synchronized void sendNotice(String target, String notice) {
		this.sendIRC().notice(target, notice);
	}

	public synchronized void sendNotice(User user, String notice) {
		user.send().notice(notice);
	}

	public synchronized void sendNotice(Channel channel, String notice) {
		channel.send().notice(notice);
	}
	
	public synchronized void sendNoticeToAll(String notice) {
		for (Channel c : this.getUserChannelDao().getAllChannels()) {
			c.send().notice(notice);
		}
	}

	public synchronized void sendAction(String target, String action) {
		this.sendIRC().action(target, action);
	}

	public synchronized void sendAction(User user, String action) {
		user.send().action(action);
	}

	public synchronized void sendAction(Channel channel, String action) {
		channel.send().action(action);
	}
	
	public synchronized void sendActionToAll(String action) {
		for (Channel c : this.getUserChannelDao().getAllChannels()) {
			c.send().action(action);
		}
	}
	
	public synchronized void kick(Channel channel, User user) {
		channel.send().kick(user, "No reason.");
	}
	
	public synchronized void kick(Channel channel, User user, String reason) {
		channel.send().kick(user, reason);
	}
	
	public synchronized void kickFromAll(User user) {
		for (Channel c : this.getUserChannelDao().getAllChannels()) {
			c.send().kick(user, "No reason.");
		}
	}
	
	public synchronized void kickFromAll(User user, String reason) {
		for (Channel c : this.getUserChannelDao().getAllChannels()) {
			c.send().kick(user, reason);
		}
	}
	
	public synchronized void ban(Channel channel, String hostmask) {
		channel.send().ban(hostmask);
	}
	
	public synchronized void ban(Channel channel, User user) {
		channel.send().ban(user.getHostmask());
	}
	
	public synchronized void banFromAll(User user) {
		for (Channel c : this.getUserChannelDao().getAllChannels()) {
			c.send().ban(user.getHostmask());
		}
	}
	
	public synchronized void banFromAll(String hostmask) {
		for (Channel c : this.getUserChannelDao().getAllChannels()) {
			c.send().ban(hostmask);
		}
	}
	
	public void shutdown() {
		LOG.info("Shutting down Aradiabot...");
		for (IRCPlugin pl : PluginList.getList()) {
			LOG.info("Disabling " + pl.getName() + " v" + pl.getVersion());
			try {
				pl.onDisable();
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("Error disabling " + pl.getName() + " v" + pl.getVersion());
			}
			this.getConfiguration().getListenerManager().removeListener(pl);
		}
		this.stopBotReconnect();
		this.shutdown(true);
		System.exit(0);
	}
	
	public void setDebug(boolean debug) {
		DEBUG = debug;
	}
	
	public boolean isDebugging() {
		return DEBUG;
	}
	
	public String getName() {
		return NAME;
	}
	
	public String getVersion() {
		return VERSION;
	}
	
	public String getAPIVersion() {
		return PIRCBOTX_VERSION;
	}
	
	public File getPluginDir() {
		return PLUGIN_DIR;
	}
	
	public Logger getLogger() {
		return LOG;
	}
}
