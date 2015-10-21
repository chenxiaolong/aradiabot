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
package sh.yle.k.ircbot.hooks.events;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.types.GenericChannelUserEvent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sh.yle.k.ircbot.IRCBot;
import sh.yle.k.ircbot.command.Command;
import sh.yle.k.ircbot.hooks.types.GenericCommandEvent;

/**
 * Command Event that is called whenever the CommandListener
 * detects that a command has been issued in a channel
 * the bot is connected to.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class CommandEvent<T extends IRCBot> extends Event<T> implements GenericChannelUserEvent<T>, GenericCommandEvent<T> {
	protected final MessageEvent<T> source;
	protected final User user;
	protected final Channel channel;
	protected final Command command;
	protected final String[] args;
	
	public CommandEvent(MessageEvent<T> source, User user, Channel channel, Command command, String[] args) {
		super(source.getBot());
		this.source = source;
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
