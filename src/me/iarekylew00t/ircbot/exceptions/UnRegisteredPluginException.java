package me.iarekylew00t.ircbot.exceptions;

/**
 * Thrown when a plugin already exists.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class UnRegisteredPluginException extends Exception {
	private static final long serialVersionUID = 3182034347484694351L;

	public UnRegisteredPluginException() {
		super();
	}
	
	public UnRegisteredPluginException(String message) {
		super(message);
	}
	
	public UnRegisteredPluginException(Throwable cause) {
		super(cause);
	}
	
	public UnRegisteredPluginException(String message, Throwable cause) {
		super(message, cause);
	}
}
