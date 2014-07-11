package me.iarekylew00t.ircbot.exceptions;

/**
 * Thrown when a plugin already exists.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class RegisteredPluginException extends Exception {
	private static final long serialVersionUID = -4840782624447521227L;

	public RegisteredPluginException() {
		super();
	}
	
	public RegisteredPluginException(String message) {
		super(message);
	}
	
	public RegisteredPluginException(Throwable cause) {
		super(cause);
	}
	
	public RegisteredPluginException(String message, Throwable cause) {
		super(message, cause);
	}
}
