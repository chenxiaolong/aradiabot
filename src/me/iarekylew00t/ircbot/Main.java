package me.iarekylew00t.ircbot;

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
import me.iarekylew00t.ircbot.configuration.FileConfiguration;

/**
 * Main class for the Aradiabot. Modify any initial settings here.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class Main {
	private static FileConfiguration CONFIG = new FileConfiguration("CONFIG.ini");
	private static Logger LOG = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	
	public static void main(String[] args) throws Exception {
		init();
		LOG.info("Starting Aradiabot v" + IRCBot.VERSION + "...");	
		if (CONFIG.firstTimeLoad() == true) {
			CONFIG.set("nickname", "IRCBot");
			CONFIG.set("login", "PircBotX");
			CONFIG.set("password", "");
			CONFIG.set("server", "irc.esper.net");
			CONFIG.set("port", "6667");
			CONFIG.set("serverPassword", "");
			CONFIG.set("channels", "");
			CONFIG.set("charset", "UTF-8");
			CONFIG.set("debug", "false");
			CONFIG.save();
			LOG.warn("First time load detected. Please edit \"config.ini\" and restart the bot. (Exit code: 1)");
			System.exit(1);
			return;
		}
		
		Configuration<IRCBot> botConfig = new Configuration.Builder<IRCBot>()
			.setEncoding(Charset.forName(CONFIG.get("charset", "UTF-8")))
			.setAutoNickChange(true)
			.setAutoReconnect(true)
			.setMessageDelay(0)
			.setLogin(CONFIG.get("login"))
			.setName(CONFIG.get("nickname"))
			.setNickservPassword(CONFIG.get("password").isEmpty() ? null : CONFIG.get("password"))
			.setServer(CONFIG.get("server"), Integer.parseInt(CONFIG.get("port", "6667")), CONFIG.get("serverPassword"))
			.addAutoJoinChannel(CONFIG.get("channels"))
			.buildConfiguration();
		IRCBot bot = new IRCBot(botConfig);
		bot.setDebug(Boolean.parseBoolean(CONFIG.get("debug", "false")));
		/* Plugins go here*/
		try {
			bot.startBot();
		} catch (IOException e) {
			LOG.error("Could not connect to " + CONFIG.get("server") + "; please check your CONFIG and connection. (Exit code: 2)");
			System.exit(2);
			return;
		} catch (IrcException e) {
			LOG.error("Could not load bot; please check your CONFIG and connection. (Exit code: 3)");
			System.exit(3);
			return;
		}
	}
	
	/**
	 * Programatically configures Logback initially.
	 */
	private static void init() {
		LoggerContext context = LOG.getLoggerContext();
		context.reset();
		
		PatternLayoutEncoder encoder = new PatternLayoutEncoder();
		encoder.setContext(context);
		encoder.setPattern("[%d{HH:mm:ss} %level] %msg%n");
		encoder.start();
		
		ConsoleAppender<ILoggingEvent> cAppender = new ConsoleAppender<ILoggingEvent>();
		cAppender.setName("STDOUT");
		cAppender.setContext(context);
		cAppender.setLayout(encoder.getLayout());
		cAppender.start();
		
		FileAppender<ILoggingEvent> fAppender = new FileAppender<ILoggingEvent>();
		fAppender.setName("FILE");
		fAppender.setFile("log.log");
		fAppender.setEncoder(encoder);
		fAppender.setContext(context);
		fAppender.start();
		
		LOG.addAppender(cAppender);
		LOG.addAppender(fAppender);
		LOG.setLevel(Boolean.parseBoolean(CONFIG.get("debug", "false")) ? Level.DEBUG : Level.INFO);
	}
}
