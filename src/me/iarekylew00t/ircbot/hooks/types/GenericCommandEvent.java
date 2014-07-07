package me.iarekylew00t.ircbot.hooks.types;

import me.iarekylew00t.ircbot.command.Command;

import org.pircbotx.PircBotX;

/**
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public interface GenericCommandEvent<T extends PircBotX> {
	public Command getCommand();
	public String[] getArgs();
}
