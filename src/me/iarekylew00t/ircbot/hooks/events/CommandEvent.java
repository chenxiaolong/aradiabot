package me.iarekylew00t.ircbot.hooks.events;

import org.pircbotx.Channel;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericChannelUserEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import me.iarekylew00t.ircbot.command.Command;
import me.iarekylew00t.ircbot.hooks.types.GenericCommandEvent;

/**
 * Called whenever a command is sent to a channel.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CommandEvent<T extends PircBotX> extends Event<T> implements GenericChannelUserEvent<T>, GenericCommandEvent<T> {
	protected final MessageEvent<T> SOURCE;
	protected final User USER;
	protected final Channel CHANNEL;
	protected final Command COMMAND;
	protected final String[] ARGS;
	
	public CommandEvent(MessageEvent<T> event, User user, Channel channel, Command command, String[] args) {
		super(event.getBot());
		SOURCE = event;
		USER = user;
		CHANNEL = channel;
		COMMAND = command;
		ARGS = args;
	}

	@Override
	public User getUser() {
		return USER;
	}

	@Override
	public Channel getChannel() {
		return CHANNEL;
	}

	@Override
	public Command getCommand() {
		return COMMAND;
	}

	@Override
	public String[] getArgs() {
		return ARGS;
	}
	
	@Override
	public void respond(String response) {
		SOURCE.respond(response);
	}
}
