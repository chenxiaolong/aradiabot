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

/**
 * IRC plugin configuration based off the flatfile configuration.
 * This config will automatically point to plugin subdirectories.
 * @author Kyle Colantonio <kyle10468@gmail.com>
 **/
public class PluginConfiguration extends FlatFileConfiguration {

	/**
	 * PluginConfiguration Constructor that calls its super class
	 * with the newly appended plugin directory information as a File.
	 **/
	public PluginConfiguration(String name, File file) {
		super(new File("./plugins/" + name + "/" + file.getName()));
	}
	
	/**
	 * PluginConfiguration Constructor that calls its super class
	 * with the newly appended plugin directory information as a String.
	 **/
	public PluginConfiguration(String name, String file) {
		super("./plugins/" + name + "/" + file);
	}
}
