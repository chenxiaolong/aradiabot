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

import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 * Utility class to keep track of IRC permissions and events.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public final class IRC {
	public static final int EXIT_SUCCESS = 0, EXIT_FAILURE = 1;
	
	/**
	 * IRC Permission levels
	 **/
	public enum Permission {
		BANNED(-1),
		REGULAR(0),
		VOICE(1),
		HALF_OP(2),
		OP(3),
		SUPER_OP(4),
		OWNER(5),
		IRC_OP(6);
		
		private final int value;
		Permission(int value) {
			this.value = value;
		}
		
		public int value() {
			return value;
		}
	}

	/**
	 * IRC Event types
	 **/
	public enum Event {
		ACTION,
		BAN,
		CHANNEL_INFO,
		CONNECT,
		COMMAND,
		DISCONNECT,
		FINGER,
		HALF_OP,
		INCOMING_CHAT_REQUEST,
		INCOMING_FILE_TRANSFER,
		INVITE,
		JOIN,
		KICK,
		MESSAGE,
		MODE,
		MOTD,
		NICK_ALREADY_IN_USE,
		NICK_CHANGE,
		NOTICE,
		OP,
		OWNER,
		PART,
		PING,
		PRIVATE,
		QUIT,
		REMOVE_CHANNEL_BAN,
		REMOVE_CHANNEL_EVENT,
		REMOVE_CHANNEL_KEY,
		REMOVE_CHANNEL_LIMIT,
		REMOVE_INVITE_ONLY,
		REMOVE_MODERATED,
		REMOVE_NO_EXTERNAL_MESSAGES,
		REMOVE_PRIVATE,
		REMOVE_SECRET,
		REMOVE_TOPIC_PROTECTION,
		SERVER_PING,
		SERVER_RESPONSE,
		SET_CHANNEL_BAN,
		SET_CHANNEL_KEY,
		SET_CHANNEL_LIMIT,
		SET_INVITE_ONLY,
		SET_MODERATED,
		SET_NO_EXTERNAL_MESSAGES,
		SET_PRIVATE,
		SET_SECRET,
		SET_TOPIC_PROTECTION,
		SOCKET_CONNECT,
		SUPER_OP,
		TIME,
		UNKNOWN,
		USER_LIST,
		USER_MODE,
		VERSION,
		VOICE,
		WHO_IS;
	}
	
	/**
	 * Determines the current Permission level of a user
	 * within a given channel.
	 * 
	 * Returns the Permission level of the user. Defaults to REGULAR.
	 **/
	public static final Permission getUserPermissionLevel(Channel channel, User user) {
		if (user.isIrcop()) {
			return Permission.IRC_OP;
		} else if (channel.isOwner(user)) {
			return Permission.OWNER;
		} else if (channel.isSuperOp(user)) {
			return Permission.SUPER_OP;
		} else if (channel.isOp(user)) {
			return Permission.OP;
		} else if (channel.isHalfOp(user)) {
			return Permission.HALF_OP;
		} else if (channel.hasVoice(user)) {
			return Permission.VOICE;
		} else {
			return Permission.REGULAR;
		}
	}
	
	/**
	 * Converts a String into its corresponding Permission type.
	 * Throws an IllegalArgumentException if an unknown permission is given.
	 **/
	public static final Permission str2perm(String perm) {
		perm = perm.replaceAll(" ", "_"); //Replace all spaces with an underscore
		for (Permission p : Permission.values()) {
			if (p.toString().equalsIgnoreCase(perm)) {
				return p;
			}
		}
		throw new IllegalArgumentException("'" + perm + "' is not a valid Permission level.");
	}

	/**
	 * Converts a Integer into its corresponding Permission type.
	 * Throws an IllegalArgumentException if an unknown permission is given.
	 **/
	public static final Permission int2perm(int perm) {
		for (Permission p : Permission.values()) {
			if (p.value() == perm) {
				return p;
			}
		}
		throw new IllegalArgumentException("'" + perm + "' is not a valid Permission level.");
	}

	/**
	 * Converts a String into its corresponding Event type.
	 * Throws an IllegalArgumentException if an unknown event is given.
	 **/
	public static final Event str2event(String event) {
		event = event.replaceAll(" ", "_"); //Replace all spaces with an underscore
		for (Event e : Event.values()) {
			if (e.toString().equalsIgnoreCase(event)) {
				return e;
			}
		}
		throw new IllegalArgumentException("'" + event + "' is not a valid Event type.");
	}
}
