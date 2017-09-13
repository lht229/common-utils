/**
 * 上海蓝鸟集团
 * 上海蓝鸟科技股份有限公司
 * 华东工程中心（无锡）
 * 2015版权所有
 */
package com.common.utils.j2se;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Goofy
 * @Date 2015年11月22日 上午11:09:45
 */
public class ArrayUtils {

	/**
	 * 判断数组是否为空
	 * @param array
	 * @return
	 */
	public static <T> boolean isEmpty(T[] array){
		if(array==null||array.length==0)return true;
		return false;
	}

	/**
	 * 获取数组长度
	 * @param array
	 * @return
	 */
	public static <T> int length(T[] array){
		if(array==null||array.length==0)return 0;
		return array.length;
	}

	/**
	 * 判断数组是否含有某个元素
	 * @param array
	 * @param t
	 * @return
	 */
	public static <T> boolean contains(T[] array,T t){
		if(length(array)==0)return false;
		for(T _t:array){
			if(_t==t)return true;
		}
		return false;
	}

	/**
	 * 数组转List
	 * @param array
	 * @return
	 */
	public static <T> List<T> asList(T[] array){
		return Arrays.asList(array);
	}

	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static int[] sort(int[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static char[] sort(char[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static double[] sort(double[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static float[] sort(float[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array 待排序数组
	 * @param caseInsensitive 是否大小写敏感
	 * @return
	 */
	public static String[] sort(String[] array,boolean caseInsensitive){
		if(caseInsensitive){
			Arrays.sort(array);
		}else{
			Arrays.sort(array,String.CASE_INSENSITIVE_ORDER);
		}
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static byte[] sort(byte[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static short[] sort(short[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static Object[] sort(Object[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static Integer[] sort(Integer[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static Character[] sort(Character[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static Double[] sort(Double[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static Float[] sort(Float[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static Byte[] sort(Byte[] array){
		Arrays.sort(array);
		return array;
	}
	/**
	 * 排序 min-max
	 * @param array
	 * @return
	 */
	public static Short[] sort(Short[] array){
		Arrays.sort(array);
		return array;
	}

	public static <T> T[] sort(T[] array,Comparator<T> c){
		Arrays.sort(array,c);
		return array;
	}

	/**
	 * 数组转String
	 * @param array
	 * @return
	 */
	public static <T> String toString(T[] array){
		StringBuffer sb=new StringBuffer("");
		if(isEmpty(array))return sb.toString();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=0;
		for(T t:array){
			if((t instanceof Integer)||(t instanceof Long)||(t instanceof Short)||(t instanceof Boolean)||(t instanceof Byte)||(t instanceof String)||(t instanceof Character)||(t instanceof Float)||(t instanceof Double)||(t instanceof Date)){
				if(t instanceof Date){
					sb.append((i++==0)?sdf.format(t):(","+sdf.format(t)));
				}else{
					sb.append((i++==0)?t.toString():(","+t.toString()));
				}
			}else{
				try {
					throw new Exception("Array.toString()方法仅支持Integer,Short,Long,Boolean,Byte,String,Character,Float,Double,Date");
				} catch (Exception e) {
					e.printStackTrace();
					return sb.toString();
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		Date[] d={new Date(),new Date(),new Date(),new Date(),new Date(),new Date(),new Date()};
		System.out.println(toString(d));
	}

}
