package edu.westga.dsdm.mvd.util;

import java.util.ArrayList;

/**
 * Class ArrayUtil
 * 
 * Provides static utility functions to work with arrays
 * 
 * @author CS3152
 * @version Fall 2022
 */
public class ArrayUtil {

	/**
	 * Creates and returns an array with the elements in the specified list. The
	 * length of the array is the size of the list. The order of the elements is
	 * preserved.
	 * 
	 * @param list a list of integers
	 * @return integer array with the elements of the specified list
	 */
	public static int[] returnAsIntArray(ArrayList<Integer> list) {
		int[] array = new int[list.size()];
		int index = 0;
		for (int value : list) {
			array[index] = value;
			index++;
		}
		return array;
	}

	/**
	 * Creates and returns an array with the elements in the specified list. The
	 * length of the array is the size of the list. The order of the elements is
	 * preserved.
	 * 
	 * @param list a list of arrays
	 * @return two-dimensional array with the elements of the specified list
	 */
	public static int[][] returnAsArrayOfIntArrays(ArrayList<int[]> list) {
		int[][] arrayOfArrays = new int[list.size()][];
		int index = 0;
		for (int[] array : list) {
			arrayOfArrays[index] = array;
			index++;
		}
		return arrayOfArrays;
	}
}
