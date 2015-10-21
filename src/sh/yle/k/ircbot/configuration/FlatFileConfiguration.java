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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Generic flatfile configuration class.
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public class FlatFileConfiguration implements Configuration {
	private final File file;
	private Properties properties = new Properties();
	
	/**
	 * FileConfiguration Constructor that takes
	 * a File as input and then attempts to load
	 * the properties from said file.
	 **/
	public FlatFileConfiguration(File file) {
		this.file = file;
	}
	
	/**
	 * Default FileConfiguration Constructor
	 * that calls FileConfiguration(File).
	 **/
	public FlatFileConfiguration(String file) {
		this(new File(file));
	}
	
	/**
	 * Attempts to read the value of a property in memory
	 * after loading it off the file on disk. If the value
	 * has not been set, it will default to null.
	 * 
	 * Returns the property value as a String.
	 **/
	@Override
	public String get(String key) {
		String value = properties.getProperty(key);
		return (value.isEmpty() ? null : value);
	}
	
	/**
	 * Attempts to read the value of a property in memory
	 * after loading it off the file on disk. If the value
	 * has not been set, it will default to the value provided.
	 * 
	 * Returns the property value as a String.
	 **/
	@Override
	public String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	/**
	 * Set the value of a property in memory, later to be
	 * written to disk if required.
	 * 
	 * Returns itself for easy referencing when making
	 * multiple changes.
	 **/
	@Override
	public FlatFileConfiguration set(String key, String value) {
		properties.setProperty(key, value);
		return this;
	}
	
	/**
	 * Create a new, empty file one disk. This will do nothing
	 * if the file already exists.
	 * 
	 * Returns false if it fails to create the new file.
	 **/
	public boolean create() {
		try {
			file.createNewFile();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Attempts to read properties from a file.
	 * This process will override any properties currently
	 * loaded into memory and replace it with the values stored
	 * within the file.
	 * 
	 * Returns false if properties cannot be read usually
	 * because the file does not exist.
	 **/
	public boolean load() {
		try {
			properties.load(new FileInputStream(file));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Attemps to save the current configuration properties
	 * to a the specified file. This will overwrite any properties
	 * currently saved within the file.
	 * 
	 * Returns false if the properties cannot be written.
	 **/
	public boolean save() {
		try {
			properties.store(new FileOutputStream(file), file.getName() + " Configuration File");
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
