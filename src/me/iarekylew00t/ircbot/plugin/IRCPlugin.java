package me.iarekylew00t.ircbot.plugin;

import java.util.ArrayList;
import java.util.List;

import me.iarekylew00t.ircbot.Aradiabot;
import me.iarekylew00t.ircbot.command.Command;
import me.iarekylew00t.ircbot.command.CommandList;
import me.iarekylew00t.ircbot.hooks.PluginBase;

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
	private final String NAME, VER;
	private boolean ENABLED;
	private List<Command> CMDS;
	private Logger LOG;
	
	public IRCPlugin(String name, String version) {
		this.NAME = name;
		this.VER = version;
		CMDS = new ArrayList<Command>();
		init();
	}
	
	public void init() {
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
		if (Aradiabot.debug()) {
			LOG.setLevel(Level.DEBUG);
		} else {
			LOG.setLevel(Level.INFO);
		}
		LOG.setAdditive(false);

		LOG.info("Enabling " + this.NAME + " v" + this.VER);
		if (this.onEnable()) {
			this.ENABLED = true;
			PluginList.add(this);
		} else {
			LOG.error("Disabling "+ this.NAME + " v" + this.VER + "; Error occured.");
			this.onDisable();
			this.ENABLED = false;
			PluginList.remove(this);
			Aradiabot.getBot().removePlugin(this);
		}
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
	
	public boolean onEnable() {
		return true;
	}

	public boolean onDisable() {
		return true;
	}
	
	public String getName() {
		return this.NAME;
	}
	
	public String getVersion() {
		return this.VER;
	}
	
	public List<Command> getCommands() {
		return this.CMDS;
	}
	
	public boolean isEnabled() {
		return this.ENABLED;
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