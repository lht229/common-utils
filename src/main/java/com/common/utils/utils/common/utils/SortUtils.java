package com.common.utils.utils.common.utils;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 排序辅助类的相关接口
 *
 *
 * 适用各种排序
 */
public class SortUtils
{
	/**
	 * 对数组 进行排序 默认为升序
	 *
	 * @param arr
	 * @author:chendy
	 */
	public static void sort(Object[] arr)
	{
		Arrays.sort(arr);
	}

	/**
	 * 对数组 进行排序 默认为升序
	 *
	 * @param arr
	 * @param isAsc
	 *        是否升序
	 */
	public static void sort(Object[] arr, boolean isAsc)
	{
		Arrays.sort(arr);
		if (!isAsc)
		{
			for (int n = 0; n < arr.length / 2; n++)
			{
				Object k = arr[n];
				arr[n] = arr[arr.length - 1 - n];
				arr[arr.length - 1 - n] = k;
				;
			}
		}
	}

	/**
	 * 对数组 进行排序 默认为升序
	 *
	 * @param arr
	 * @param isAsc
	 *        是否升序
	 */
	public static void sort(Object[] arr, Comparator<Object> comp)
	{
		Arrays.sort(arr, comp);
	}

	/**
	 * 对 list进行排序
	 *
	 * @param list
	 * @param key
	 *        排序比较字段
	 * @param isAsc
	 *        是否为升序
	 */
	public static void sort(List<Map> list, String key, boolean isAsc)
	{
		Collections.sort(list, new MapCompare(key, isAsc));
	}

	/**
	 * 对 list进行排序
	 *
	 * @param list
	 * @param key
	 *        排序比较字段
	 * @param isAsc
	 *        是否为升序
	 * @param list
	 *        是否为数字类型
	 */
	public static void sort(List<Map> list, String key, boolean isAsc, boolean isNum)
	{
		Collections.sort(list, new MapCompare(key, isAsc, isNum));
	}

	/**
	 * 对数组 进行排序 默认为升序
	 *
	 * @param arr
	 * @param isAsc
	 *        是否升序
	 */
	public static void sort(List<Map> list, Comparator<Object> compare)
	{
		Collections.sort(list, compare);
	}
}

/**
 * 比较的 接口
 */
class MapCompare implements Comparator<Map>
{
	private String key;
	private boolean isAsc = true;
	private boolean isNumber = false;

	/**
	 * 创建 默认的 升序排列
	 *
	 * @coustructor 方法.
	 */
	public MapCompare(String key)
	{
		this.key = key;
	}

	/**
	 * 创建 默认的 指定的 排列对象
	 *
	 * @coustructor 方法.
	 */
	public MapCompare(String key, boolean isAsc)
	{
		super();
		this.key = key;
		this.isAsc = isAsc;
	}

	/**
	 * 创建 默认的 指定的 排列对象 同时指定 其是否为 数字
	 *
	 * @coustructor 方法.
	 */
	public MapCompare(String key, boolean isAsc, boolean isNum)
	{
		super();
		this.key = key;
		this.isAsc = isAsc;
		this.isNumber = isNum;
	}

	@Override
	public int compare(Map map1, Map map2)
	{
		// 字符串排序
		if (!isNumber)
		{
			if (map1.get(key) == null && map2.get(key) != null)
			{
				return isAsc ? -1 : 1;
			} else if (map1.get(key) == null && map2.get(key) == null)
			{
				return 0;
			} else if (map2.get(key) == null)
			{
				return isAsc ? 1 : -1;
			}
			int result = map1.get(key).toString().compareTo(map2.get(key).toString());
			return isAsc ? result : -1 * result;
		} else
		{
			// 数字排序
			if (map1.get(key) == null && map2.get(key) != null)
			{
				return isAsc ? -1 : 1;
			} else if (map1.get(key) == null && map2.get(key) == null)
			{
				return 0;
			} else if (map2.get(key) == null)
			{
				return isAsc ? 1 : -1;
			}
			double madi = Double.parseDouble(map1.get(key).toString())
				- Double.parseDouble(map2.get(key).toString());
			int result = madi > 0 ? 1 : (madi == 0 ? 0 : -1);
			return isAsc ? result : -1 * result;
		}
	}
}