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

import java.io.IOException;
import java.nio.charset.Charset;

import org.pircbotx.Configuration;
import org.pircbotx.exception.IrcException;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import sh.yle.k.ircbot.configuration.FlatFileConfiguration;

/**
 * Main class used to start the entire IRC bot.
 * Initial configuration takes place here.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public class Main {
	private static FlatFileConfiguration config = new FlatFileConfiguration("config.ini");
	private static final Logger log = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	
	/**
	 * Main method. Used to load the configuration and start the bot.
	 * Exceptions are thrown when unrecoverable errors occur.
	 **/
	public static void main(String[] args) throws Exception {
		initLogger();
		log.info("Starting " + Aradiabot.NAME + " v" + IRCBot.VERSION + " ...");
		
		/* Load Configuration */
		if (!config.load()) {
			config.create();
			config.set("nickname", "Aradiabot")
				  .set("login", "PircBotX")
				  .set("password", "")
				  .set("server", "irc.esper.net")
				  .set("port", "6667")
				  .set("serverPassword", "")
				  .set("channels", "")
				  .set("charset", "UTF-8")
				  .set("debug", "false")
				  .save();
			log.warn("First time load detected. Please edit 'config.ini' and restart the bot.");
			System.exit(IRC.EXIT_FAILURE);
		}
		
		/* Set Configuration for bot */
		Configuration<IRCBot> bConfig = new Configuration.Builder<IRCBot>()
				.setEncoding(Charset.forName(config.get("charset", "UTF-8")))
				.setAutoNickChange(true)
				.setAutoReconnect(true)
				.setMessageDelay(0)
				.setLogin(config.get("login", "PircBotX"))
				.setName(config.get("nickname", "Aradiabot"))
				.setNickservPassword(config.get("password"))
				.setServer(config.get("server", "irc.esper.net"), Integer.parseInt(config.get("port", "6667")), config.get("serverPassword"))
				.addAutoJoinChannel(config.get("channels"))
				.buildConfiguration();
		
		/* Create and start the new bot */
		IRCBot bot = new IRCBot(bConfig);
		bot.debug(Boolean.parseBoolean(config.get("debug", "false")));
		Aradiabot.setBot(bot); //Save singleton bot
		try {
			bot.startBot();
		} catch (IOException e) { //Connection error
			log.error("Could not connect to " + config.get("server") + ". Please check your config and internet connection.");
			System.exit(IRC.EXIT_FAILURE);
		} catch (IrcException e) { //IRC config error
			log.error("Could not start bot. Please check your config and internet connection.");
			System.exit(IRC.EXIT_FAILURE);
		} catch (Exception e) { //Unknown error
			log.error("Unexpected error occured when starting bot. Please check your config and internet connection.\n"
					+ "If this issue continues, please report it https://github.com/IAreKyleW00t/aradiabot/issues.");
			System.exit(IRC.EXIT_FAILURE);
		}
		
		/* Goodbye! */
		System.exit(IRC.EXIT_SUCCESS);
	}
	
	/**
	 * Small method used to configure Logger settings.
	 **/
	private static void initLogger() {
		/* Clear context so we can use our own */
		LoggerContext context = log.getLoggerContext();
		context.reset();
		
		/* Set the pattern of the log output */
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(context);
		encoder.setPattern("[%d{HH:mm:ss} %level] %msg%n");
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

		/* Update ROOT Logger */
		log.addAppender(console);
		log.addAppender(file);
		log.setLevel(Boolean.parseBoolean(config.get("debug", "false")) ? Level.DEBUG : Level.INFO);
	}
}
