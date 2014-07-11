package me.iarekylew00t.ircbot.exceptions;

/**
 * Thrown when a command already exists.
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public class RegisteredCommandException extends Exception {
	private static final long serialVersionUID = 373425231903629552L;

	public RegisteredCommandException() {
		super();
	}
	
	public RegisteredCommandException(String message) {
		super(message);
	}
	
	public RegisteredCommandException(Throwable cause) {
		super(cause);
	}
	
	public RegisteredCommandException(String message, Throwable cause) {
		super(message, cause);
	}
}
