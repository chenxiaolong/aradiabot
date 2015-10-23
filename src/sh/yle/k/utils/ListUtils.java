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
package sh.yle.k.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

/**
 * Utility class to help work with Lists.
 * 
 * @author Kyle Colantonio <kyle10468@gmail.com>
 */
public final class ListUtils {

	public static final <T> List<List<T>> split(List<T> list, int size) {
		Validate.notNull(list, "Input list cannot be null");
		Validate.isTrue(size > 0, "Minimum list size must be > 0");
		
		List<List<T>> lists = new ArrayList<List<T>>();
		for (int i = 0; i < list.size(); i += size) {
			lists.add(list.subList(i, Math.min(i + size, list.size())));
		}
		return lists;
	}
}
