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
	protected final MessageEvent<T> source;
	protected final User user;
	protected final Channel channel;
	protected final Command command;
	protected final String[] args;
	
	public CommandEvent(MessageEvent<T> event, User user, Channel channel, Command command, String[] args) {
		super(event.getBot());
		this.source = event;
		this.user = user;
		this.channel = channel;
		this.command = command;
		this.args = args;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public Channel getChannel() {
		return channel;
	}

	@Override
	public Command getCommand() {
		return command;
	}

	@Override
	public String[] getArgs() {
		return args;
	}
	
	@Override
	public void respond(String response) {
		source.respond(response);
	}
}
