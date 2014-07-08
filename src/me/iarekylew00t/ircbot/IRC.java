package me.iarekylew00t.ircbot;

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
		if (user.isIrcop())
			return IRC_OP;
		else if (channel.isOwner(user))
			return OWNER;
		else if (channel.isSuperOp(user))
			return SUPER_OP;
		else if (channel.isOp(user))
			return OP;
		else if (channel.isHalfOp(user))
			return HALF_OP;
		else if (channel.hasVoice(user))
			return VOICE;
		
		return REGULAR;
	}	
	
	public static String permissionToString(int perm) {
		if (perm == IRC.IRC_OP)
			return "IRC OP";
		else if (perm == IRC.OWNER)
			return "OWNER";
		else if (perm == IRC.SUPER_OP)
			return "SUPER OP";
		else if (perm == IRC.OP)
			return "OP";
		else if (perm == IRC.HALF_OP)
			return "HALF OP";
		else if (perm == IRC.VOICE)
			return "VOICE";
		else if (perm == IRC.REGULAR)
			return "REGULAR";
		else if (perm == IRC.BANNED)
			return "BANNED"; 
		else
			throw new IllegalArgumentException("Invalid permission level given. Use an integer from -1-6");
	}
}
