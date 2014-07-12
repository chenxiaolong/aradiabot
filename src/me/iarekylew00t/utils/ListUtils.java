package me.iarekylew00t.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * List utility class
 * @author Kyle Colantonio <IAreKyleW00t kyle10468@gmail.com>
 */
public final class ListUtils {

	/**
	 * Splits a List into a list of smaller lists within the maximum size.
	 */
	public static final <T extends Object> List<List<T>> split(List<T> list, int size) {
		if (size == 0) {
			throw new IllegalArgumentException("List size must be > 0");
		}
		List<List<T>> lists = new ArrayList<List<T>>();
		for (int i = 0; i < list.size(); i += size) {
			lists.add(list.subList(i, Math.min(i + size, list.size())));
		}
		return lists;
	}
}
