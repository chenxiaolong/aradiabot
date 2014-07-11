package me.iarekylew00t.ircbot.exceptions;

/**
 * Thrown when a command already exists.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class UnRegisteredCommandException extends Exception {
	private static final long serialVersionUID = 5284465514696144565L;

	public UnRegisteredCommandException() {
		super();
	}
	
	public UnRegisteredCommandException(String message) {
		super(message);
	}
	
	public UnRegisteredCommandException(Throwable cause) {
		super(cause);
	}
	
	public UnRegisteredCommandException(String message, Throwable cause) {
		super(message, cause);
	}
}
