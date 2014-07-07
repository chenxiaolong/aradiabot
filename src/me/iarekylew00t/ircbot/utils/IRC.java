package me.iarekylew00t.ircbot.utils;

import org.pircbotx.Channel;
import org.pircbotx.User;

/**
 * Utility class to help keep track of IRC permissions.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public final class IRC {
	public static final int BANNED		= -1,
							REGULAR 	= 0,
							VOICE		= 1,
							HALF_OP 	= 2,
							OP			= 3,
							SUPER_OP	= 4,
							OWNER		= 5,
							IRC_OP		= 6;
	
	public static int getPermissionLevel(Channel channel, User user) {
		if (user.isIrcop()) {
			return IRC_OP;
		} else if (channel.isOwner(user)) {
			return OWNER;
		} else if (channel.isSuperOp(user)) {
			return SUPER_OP;
		} else if (channel.isOp(user)) {
			return OP;
		} else if (channel.isHalfOp(user)) {
			return HALF_OP;
		} else if (channel.hasVoice(user)) {
			return VOICE;
		}
		return REGULAR;
	}					
}
