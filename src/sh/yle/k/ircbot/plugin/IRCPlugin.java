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
package sh.yle.k.ircbot.plugin;

import java.io.File;

import org.apache.commons.lang3.Validate;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import sh.yle.k.ircbot.Aradiabot;
import sh.yle.k.ircbot.IRC;
import sh.yle.k.ircbot.IRCBot;
import sh.yle.k.ircbot.command.Command;
import sh.yle.k.ircbot.command.CommandList;
import sh.yle.k.ircbot.hooks.ListenerBase;

/**
 * IRCPlugin class that is used to add new features into the IRCBot.
 * Plugins, when loaded, will automatically add information into the bot
 * and begin listening for all events. Users will typically use onCommand(),
 * but onMessage(), onAction(), etc. can be listened for as well.
 * 
 * Any errors that occur here will likely be fatal and terminate the program.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public abstract class IRCPlugin extends ListenerBase<IRCBot> {
	private final String name, version, author, description;
	private final CommandList commands = new CommandList();
	private final File dataDirectory;
	private Logger log;
	
	/**
	 * IRCPlugin Constructor that will save keep track of corresponding
	 * plugin information. These values are final and should not
	 * be modified after being set.
	 **/
	public IRCPlugin(final String name, final String version, final String author, final String description) {
		this.name = name;
		this.version = version;
		this.author = author;
		this.description = description;
		this.dataDirectory = new File(Aradiabot.getPluginDir(), name);
		this.log = (Logger) LoggerFactory.getLogger(name);
		
		/* Initialize the plugin */
		this.initPlugin();
	}
	
	/**
	 * Initializes the IRCPlugin by setting up the plugin logger
	 * and then calling onEnable(). Errors that occur here will
	 * likely be fatal and terminate the program.
	 **/
	private void initPlugin() {
		/* Load a new Context from our Original */
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		
		/* Set the pattern of the log output */
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(context);
		encoder.setPattern("[%d{HH:mm:ss} %level] [" + name + "] %msg%n");
		encoder.start();
		
		/* Console output */
		ConsoleAppender<ILoggingEvent> console = new ConsoleAppender<ILoggingEvent>();
		console.setName("STDOUT");
		console.setContext(context);
		console.setLayout(encoder.getLayout());
		console.start();
		
		/* File output */
		FileAppender<ILoggingEvent> file = new FileAppender<ILoggingEvent>();
		file.setName("FILE");
		file.setFile("log.log");
		file.setEncoder(encoder);
		file.setContext(context);
		file.start();

		/* Update Plugin Logger */
		log.addAppender(console);
		log.addAppender(file);
		log.setLevel(Aradiabot.DEBUG ? Level.DEBUG : Level.INFO);
		log.setAdditive(false);

		/* Attempt to enable the plugin by calling onEnable().
		 * If this fails, then a fatal error is thrown and the
		 * program will terminate along with the error message. */
		try {
			log.info("Enabled " + name + " v" + version + " ...");
			if (this.onEnable()) {
				this.setEnabled(true);
			} else {
				log.error("Failed to enable " + name + " v" + version + "; returned false. Check your configuration and try again.");
				System.exit(IRC.EXIT_FAILURE);
			}
		} catch (Exception e) {
			log.error(e.toString());
			log.error("Fatal error occured; terminating.");
			System.exit(IRC.EXIT_FAILURE);
		}
	}
	
	/**
	 * Called automatically when the plugin is created. If
	 * an Exception occurs, it will terminate the program.
	 * 
	 * Returns false if the plugin author indicates that
	 * something is wrong. This is fatal and should only happen
	 * in irrecoverable scenarios.
	 **/
	public boolean onEnable() throws Exception {
		return true;
	}
	
	/**
	 * Called automatically when the plugin is removed. If
	 * an Exception occurs, it will still continue to disable
	 * the remaining plugins before terminating. Usually called
	 * during the Aradiabot.shutdown() process.
	 * 
	 * Returns false if the plugin author indicates that
	 * something is wrong. While this is not fatal, it should
	 * only happen in irrecoverable scenarios.
	 **/
	public boolean onDisable() throws Exception {
		return true;
	}
	
	/**
	 * Adds a new Command to both the global and local
	 * CommandList. If the command already exists, the program
	 * will terminate to prevent collision.
	 **/
	public final void addCommand(Command command) {
		Validate.isTrue(!Aradiabot.getCommands().contains(command), "'%s' already exists in the CommandList", command.getName());
		Aradiabot.getCommands().add(command);
		commands.add(command);
	}
	
	/**
	 * Removes an existing Command from both the global and local
	 * CommandList. If the command does not exists, the program
	 * will terminate.
	 **/
	public final void removeCommand(Command command) {
		Validate.isTrue(Aradiabot.getCommands().contains(command), "'%s' does not exist in the CommandList", command.getName());
		Aradiabot.getCommands().remove(command);
		commands.remove(command);
	}
	
	/**
	 * Returns the name of the IRCPlugin.
	 **/
	public final String getName() {
		return name;
	}
	
	/**
	 * Returns the IRCPlugin version number.
	 **/
	public final String getVersion() {
		return version;
	}
	
	/**
	 * Returns the IRCPlugin author.
	 **/
	public final String getAuthor() {
		return author;
	}

	/**
	 * Returns the IRCPlugin description.
	 **/
	public final String getDescription() {
		return description;
	}
	
	/**
	 * Returns the list of Commands used locally
	 * in the IRCPlugin.
	 **/
	public final CommandList getCommands() {
		return commands;
	}
	
	/**
	 * Returns the IRCPlugin data folder.
	 * ie: ./plugins/{name}/
	 **/
	public final File getDataFolder() {
		return dataDirectory;
	}
	
	/**
	 * Returns the IRCPlugin Logger.
	 **/
	public final Logger getLogger() {
		return log;
	}
	
	/**
	 * Returns the singleton bot that is
	 * used by Aradiabot.
	 **/
	public final IRCBot getBot() {
		return Aradiabot.getBot();
	}
	
	/**
	 * Returns the basic String representation
	 * of the IRCPlugin.
	 **/
	@Override
	public String toString() {
		return name + " (" + version + ")";
	}
}
