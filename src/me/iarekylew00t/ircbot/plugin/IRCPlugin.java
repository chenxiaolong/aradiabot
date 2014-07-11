package me.iarekylew00t.ircbot.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iarekylew00t.ircbot.Aradiabot;
import me.iarekylew00t.ircbot.IRCBot;
import me.iarekylew00t.ircbot.command.Command;
import me.iarekylew00t.ircbot.command.CommandList;
import me.iarekylew00t.ircbot.exceptions.RegisteredCommandException;
import me.iarekylew00t.ircbot.exceptions.UnRegisteredCommandException;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;

/**
 * Represents an IRC Plugin for the bot.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public abstract class IRCPlugin extends PluginBase implements Comparable {
	private final String NAME, VER, AUTHOR, DESC;
	private final File PLUGIN_DIR;
	private final List<Command> CMDS;
	private Logger LOG;
	
	public IRCPlugin(String name, String version, String author, String desc) {
		this.NAME = name;
		this.VER = version;
		this.AUTHOR = author;
		this.DESC = desc;
		this.PLUGIN_DIR = new File(Aradiabot.getPluginDir(), name);
		this.CMDS = new ArrayList<Command>();
		init();
	}
	
	private void init() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		PatternLayoutEncoder ple = new PatternLayoutEncoder();
		
		ple.setPattern("[%d{HH:mm:ss} %level] [" + this.NAME + "] %msg%n");
		ple.setContext(lc);
		ple.start();
		
		ConsoleAppender<ILoggingEvent> ca = new ConsoleAppender<ILoggingEvent>();
		ca.setName("STDOUT");
		ca.setContext(lc);
		ca.setLayout(ple.getLayout());
		ca.start();
		
		FileAppender<ILoggingEvent> fa = new FileAppender<ILoggingEvent>();
		fa.setName("FILE");
		fa.setFile("log.log");
		fa.setEncoder(ple);
		fa.setContext(lc);
		fa.start();
		
		LOG = (Logger) LoggerFactory.getLogger(this.NAME);
		LOG.addAppender(ca);
		LOG.addAppender(fa);
		if (Aradiabot.isDebugging()) {
			LOG.setLevel(Level.DEBUG);
		} else {
			LOG.setLevel(Level.INFO);
		}
		LOG.setAdditive(false);
		
		try {
			LOG.info("Enabling " + this.NAME + " v" + this.VER);
			if (this.onEnable()) {
				this.setEnabled(true);
				return;
			} else {
				throw new Exception("Error occured when enabling plugin");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("Disabling "+ this.NAME + " v" + this.VER);
			this.setEnabled(false);
			try {
				this.onDisable();
				return;
			} catch (Exception e1) {
				e1.printStackTrace();
				LOG.error("Error disabling plugin: " + e1.getMessage());
			}
		}
	}
	
	/**
	 * Called when the plugin is enabled. Returns true
	 * if no errors occur. False will indicate a problem and disable the plugin.
	 * @return
	 */
	public boolean onEnable() throws Exception {
		return true;
	}
	
	/**
	 * Called when the plugin is disabled. Returns true
	 * if no errors occur. False will indicate a problem.
	 * @return
	 */
	public boolean onDisable() throws Exception {
		return true;
	}
	
	public void addCommand(String name, String desc, String usage, List<String> aliases, int perm) throws RegisteredCommandException {
		Command cmd = new Command(name, desc, usage, aliases, perm);
		if (CommandList.contains(cmd)) {
			throw new RegisteredCommandException("Command '" + name + "' already registered.");
		}
		CommandList.add(cmd);
		this.CMDS.add(cmd);
	}
	
	public void addCommand(String name, String desc, String usage) throws RegisteredCommandException {
		Command cmd = new Command(name, desc, usage);
		if (CommandList.contains(cmd)) {
			throw new RegisteredCommandException("Command '" + name + "' already registered.");
		}
		CommandList.add(cmd);
		this.CMDS.add(cmd);
	}
	
	public void addCommand(String name) throws RegisteredCommandException {
		Command cmd = new Command(name);
		if (CommandList.contains(cmd)) {
			throw new RegisteredCommandException("Command '" + name + "' already registered.");
		}
		CommandList.add(cmd);
		this.CMDS.add(cmd);
	}
	
	public void addCommand(Command cmd) throws RegisteredCommandException {
		if (CommandList.contains(cmd)) {
			throw new RegisteredCommandException("Command '" + cmd.getName() + "' already registered.");
		}
		CommandList.add(cmd);
		this.CMDS.add(cmd);
	}
	
	public void removeCommand(Command cmd) throws UnRegisteredCommandException {
		if (!CommandList.contains(cmd)) {
			throw new UnRegisteredCommandException("Command '" + cmd.getName() + "' is not a registered.");
		}
		CommandList.remove(cmd);
		this.CMDS.remove(cmd);
	}
	
	public String getName() {
		return this.NAME;
	}

	public String getVersion() {
		return this.VER;
	}
	
	public String getAuthor() {
		return this.AUTHOR;
	}
	
	public String getDescription() {
		return this.DESC;
	}
	
	public File getDataFolder() {
		return this.PLUGIN_DIR;
	}
	
	public List<Command> getCommands() {
		return this.CMDS;
	}
	
	public Logger getLogger() {
		return this.LOG;
	}
	
	public IRCBot getBot() {
		return Aradiabot.getBot();
	}
	
	@Override
	public String toString() {
		return this.NAME + " (" + this.VER + ")";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof String) {
			return this.NAME.equals((String) o);
		} else if (o instanceof IRCPlugin) {
			return this.NAME.equals(((IRCPlugin) o).getName());
		}
		return false;
	}
	
	@Override
	public int compareTo(Object o) {
		return this.NAME.compareTo(((IRCPlugin) o).getName());
	}
}
