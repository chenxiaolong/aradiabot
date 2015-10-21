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
package sh.yle.k.ircbot.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Memory-based configuration. All settings are sorted in memory
 * and will be permenantly cleared when the program terminates.
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public class MemoryConfiguration implements Configuration {
	private Map<String, String> properties;
	
	/**
	 * MemoryConfiguration Constructor that creates a
	 * new HashMap for sorting memory-based properties.
	 **/
	public MemoryConfiguration() {
		properties = new HashMap<String, String>();
	}

	/**
	 * Attempts to read the value of a property in memory.
	 * If the value has not been set, it will default to null.
	 * 
	 * Returns the property value as a String.
	 **/
	@Override
	public String get(String key) {
		return properties.get(key);
	}
	
	/**
	 * Attempts to read the value of a property in memory.
	 * If the value has not been set, it will default to
	 * the value provided.
	 * 
	 * Returns the property value as a String.
	 **/
	@Override
	public String get(String key, String defaultValue) {
		String value = properties.get(key);
		return (value == null ? defaultValue : value);
	}
	
	/**
	 * Set the value of a property in memory.
	 * 
	 * Returns itself for easy referencing when making
	 * multiple changes.
	 **/
	@Override
	public MemoryConfiguration set(String key, String value) {
		properties.put(key, value);
		return this;
	}
}
