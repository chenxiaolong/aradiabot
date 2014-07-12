package me.iarekylew00t.utils;

import org.pircbotx.Colors;

/**
 * IRC Color uility class
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public final class ColorUtils {

	/**
	 * Replaces all string color codes with IRC colors.
	 * All color codes are similar to Minecraft's color codes.
	 */
	public static final String convert(String str) {
		return str.replace("&0", Colors.BLACK).replace("&1", Colors.DARK_BLUE).replace("&2", Colors.DARK_GREEN)
				.replace("&3", Colors.TEAL).replace("&4", Colors.BROWN).replace("&5", Colors.PURPLE)
				.replace("&6", Colors.OLIVE).replace("&7", Colors.LIGHT_GRAY).replace("&8", Colors.DARK_GRAY)
				.replace("&9", Colors.BLUE).replace("&a", Colors.GREEN).replace("&b", Colors.CYAN)
				.replace("&c", Colors.RED).replace("&d", Colors.MAGENTA).replace("&e", Colors.YELLOW)
				.replace("&f", Colors.WHITE).replace("&k", Colors.REVERSE).replace("&l", Colors.BOLD)
				.replace("&n", Colors.UNDERLINE).replace("&r", Colors.NORMAL);
	}
}
