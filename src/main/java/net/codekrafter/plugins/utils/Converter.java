/**
 * @author codekrafter
 *
 */
package net.codekrafter.plugins.utils;

import java.util.Collection;


/**
 * @author codekrafter
 *
 */
public class Converter
{
	public static Object[] toArray(Collection<Object> ol) {
		Object[] array = new Object[ol.size()+1];
		int i = 0;
		for(Object o : ol) {
			array[i] = o;
			i++;
		}
		return array;
	}
}
