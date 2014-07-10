package me.iarekylew00t.ircbot.plugin;

import java.util.ArrayList;
import java.util.List;

import me.iarekylew00t.ircbot.Aradiabot;
import me.iarekylew00t.ircbot.command.Command;
import me.iarekylew00t.ircbot.command.CommandList;

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
	private final List<Command> CMDS;
	private Logger LOG;
	
	public IRCPlugin(String name, String version, String author, String desc) {
		this.NAME = name;
		this.VER = version;
		this.AUTHOR = author;
		this.DESC = desc;
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

		LOG.info("Enabling " + this.NAME + " v" + this.VER);
		PluginList.add(this);
		if (this.onEnable()) {
			this.setEnabled(true);
		} else {
			LOG.error("Disabling "+ this.NAME + " v" + this.VER + "; Error occured.");
			this.onDisable();
			this.setEnabled(false);
		}
	}
	
	/**
	 * Called when the plugin is enabled. Returns true
	 * if no errors occur. False will indicate a problem and disable the plugin.
	 * @return
	 */
	public boolean onEnable() {
		return true;
	}
	
	/**
	 * Called when the plugin is disabled. Returns true
	 * if no errors occur. False will indicate a problem.
	 * @return
	 */
	public boolean onDisable() {
		return true;
	}
	
	public void addCommand(String name, String desc, String usage, List<String> aliases, int perm) {
		Command cmd = new Command(name, desc, usage, aliases, perm);
		CommandList.add(cmd);
		this.CMDS.add(cmd);
	}
	
	public void addCommand(String name, String desc, String usage) {
		Command cmd = new Command(name, desc, usage);
		CommandList.add(cmd);
		this.CMDS.add(cmd);
	}
	
	public void addCommand(String name) {
		Command cmd = new Command(name);
		CommandList.add(cmd);
		this.CMDS.add(cmd);
	}
	
	public void addCommand(Command cmd) {
		CommandList.add(cmd);
		this.CMDS.add(cmd);
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
	
	public List<Command> getCommands() {
		return this.CMDS;
	}
	
	public Logger getLogger() {
		return this.LOG;
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
