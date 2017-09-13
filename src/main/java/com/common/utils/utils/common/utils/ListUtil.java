package com.common.utils.utils.common.utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *
 * 针对 LIST的一些常用 操作 变换
 *
 * @author 陈登宇 chendy@si-tech.com.cn
 * @version 1.0
 * @update 修改日期 修改描述
 */
public class ListUtil
{
	/**
	 * 将 List 中 符合 keyName = value 的记录，找到第一条，并过滤出来
	 *
	 * @param list
	 * @param keyName
	 * @param value
	 * @return
	 */
	public static Map<String, Object> pop(List list, String keyName, String value)
	{
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (Object o : list)
		{
			Map m = (Map) o;
			Object val = m.get(keyName);
			if (val != null && value.equals(val.toString()))
			{
				return m;
			}
		}
		return null;
	}

	/**
	 * 将一个 List<Map> 转换为MAP key-value 分别为list里map的两个值 如：
	 * [{key1:123,key2:224},{key1:135,key2:234}] 调用此方法 listToMap(List,
	 * key1,key2) 得到的结果为:{123:244,135:234}
	 *
	 * @param list
	 * @param keyName
	 * @param valueName
	 * @return
	 * @author:陈登宇
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static Map<String, String> listToMap(List list, String keyName, String valueName)
	{
		HashMap<String, String> map = new HashMap<String, String>(list.size() + 10);
		if (list == null)
		{
			return map;
		}
		for (Object o : list)
		{
			Map m = (Map) o;
			Object key = m.get(keyName);
			Object value = m.get(valueName);
			if (key != null)
			{
				map.put(key.toString(), value == null ? null : value.toString());
			}
		}
		return map;
	}

	/**
	 * 将一个 List<Map> 转换为 将其中的部分记录 过滤出来 key-value 为过滤的条件 如：
	 * [{key1:123,key2:224},{key1:135,key2:234},{key1:123,key2:234}] 调用此方法
	 * listFilter(List, key1,123)
	 * 得到的结果为:[{key1:123,key2:224},{key1:123,key2:234}]
	 *
	 * @param list
	 * @param keyName
	 * @param value
	 * @return
	 * @author:陈登宇
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static List<Map<String, Object>> listFilter(List list, String keyName, String value)
	{
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (Object o : list)
		{
			Map m = (Map) o;
			Object val = m.get(keyName);
			if (val != null && value.equals(val.toString()))
			{
				listMap.add(m);
			}
		}
		return listMap;
	}

	/**
	 * 将两个LIST合并，按key值合并 比如 查询得到的第一个list为：
	 * [{item_id:100,item_name:产品1},{item_id:101,item_name:产品2}] 第二个list:
	 * [{item_id:100,operate_date:2014年},{item_id:101,operate_date:2015年}]
	 *
	 * 合并的key = item_id
	 *
	 * 将list2合并到list1后的数据为：
	 * [{item_id:100,item_name:产品1,operate_date:2014年},{item_id
	 * :101,item_name:产品2,operate_date:2015年}]
	 *
	 * @param list1
	 * @param list2
	 * @param compareKey
	 * @return
	 * @author:陈登宇
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static void listAddList(List list1, List list2, String compareKey)
	{
		Map<String, Integer> map = new HashMap<String, Integer>(list2.size() + list2.size() / 5);
		for (int n = 0; n < list2.size(); n++)
		{
			Map m1 = (Map) list2.get(n);
			try
			{
				map.put(m1.get(compareKey).toString(), n);
			} catch (Exception e)
			{
			}
		}

		for (Map m : (List<Map>) list1)
		{
			Object value = m.get(compareKey);
			if (value != null)
			{
				Integer index = map.get(value.toString());
				if (index != null)
				{
					m.putAll((Map) list2.get(index));
				}
			}
		}
	}

	/**
	 * 将两个LIST合并，按key1和key2值合并（上面方法针对 两个list合并的key_name相同，如果不同则使用此方法） 比如
	 * 查询得到的第一个list为：
	 * [{item_id1:100,item_name:产品1},{item_id1:101,item_name:产品2}] 第二个list:
	 * [{item_id2:100,operate_date:2014年},{item_id2:101,operate_date:2015年}]
	 *
	 * 合并的key1 = item_id1 ,key2 = item_id2
	 *
	 * 将list2合并到list1后的数据为：
	 * [{item_id1:100,item_name:产品1,item_id2:100,operate_date
	 * :2014年},{item_id1:101,item_name:产品2,item_id2:101,operate_date:2015年}]
	 *
	 * @param list1
	 * @param list2
	 * @param compareKey
	 * @return
	 * @author:陈登宇
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static void listAddList(List list1, String compareKey1, List list2, String compareKey2)
	{
		Map<String, Integer> map = new HashMap<String, Integer>(list2.size() + list2.size() / 5);
		for (int n = 0; n < list2.size(); n++)
		{
			Map m1 = (Map) list2.get(n);
			try
			{
				map.put(m1.get(compareKey2).toString(), n);
			} catch (Exception e)
			{
			}
		}

		for (Map m : (List<Map>) list1)
		{
			Object value = m.get(compareKey1);
			if (value != null)
			{
				Integer index = map.get(value.toString());
				if (index != null)
				{
					m.putAll((Map) list2.get(index));
				}
			}
		}

	}

	/**
	 * 将两个LIST合并，按key值合并 将第二个list按key值作为第一个list的子项合并进去 比如 查询得到的第一个list为：
	 * [{item_id:100,item_name:产品1},{item_id:101,item_name:产品2}] 第二个list:
	 * [{item_id
	 * :100,operate_date:2014年},{item_id:101,operate_date:2015年},{item_id
	 * :101,operate_date:2016年}]
	 *
	 * 合并的key = item_id ，childKey='child'
	 *
	 * 将list2合并到list1后的数据为：
	 * [{item_id:100,item_name:产品1,child:[{item_id:100,operate_date:2014年]}},
	 * {item_id
	 * :101,item_name:产品2,child:[{item_id:101,operate_date:2015年]},{item_id
	 * :101,operate_date:2016年]}]
	 *
	 * @param list1
	 * @param list2
	 * @param compareKey
	 * @return
	 * @author:陈登宇
	 * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public static void listAddChildList(List list1, List list2, String compareKey, String childKey)
	{
		Map<String, String> map = new HashMap<String, String>(list2.size() + list2.size() / 5);
		for (int n = 0; n < list2.size(); n++)
		{
			Map m1 = (Map) list2.get(n);
			try
			{
				String key = m1.get(compareKey).toString();
				if (map.containsKey(key))
				{
					map.put(key, map.get(key) + "," + n);
				} else
				{
					map.put(key, n + "");
				}
			} catch (Exception e)
			{
			}
		}

		for (Map m : (List<Map>) list1)
		{
			Object value = m.get(compareKey);
			List<Map> list = new ArrayList<Map>();
			if (value != null)
			{
				String indexs = map.get(value.toString());
				if (indexs != null)
				{
					String[] indexArr = indexs.split(",");
					for (String ind : indexArr)
					{
						list.add(((List<Map>) list2).get(Integer.parseInt(ind)));
					}
				}
			}
			m.put(childKey, list);
		}
		// for(Map m:(List<Map>)list1)
		// {
		// Object value = m.get(compareKey);
		// List<Map> list = new ArrayList<Map>();
		// if(value != null)
		// {
		// for(Map m2:(List<Map>)list2)
		// {
		// if(m2.get(compareKey) != null &&
		// value.toString().equals(m2.get(compareKey).toString()))
		// {
		//
		// list.add(m2);
		// }
		// }
		// }
		// m.put(childKey, list);
		// }
	}

	/**
	 * 将List 中的某一个 字段 拿出来统一进行拼接
	 *
	 * 如 list =
	 * [{item_id:100,operate_date:2014年},{item_id:101,operate_date:2015年
	 * },{item_id:102,operate_date:2016年}] item_id拼接，分隔符为[,]的话得到的结果为 100,101,102
	 *
	 * 如果拼写SQL需要单引号的情况下，分隔符可以写为[','] 得到的结果为 100','101','102 拿到后 只需要两边加个单引号就可以了
	 *
	 * @param list
	 * @param field
	 * @param split
	 * @return
	 */
	public static String concat(List<Map> list, String field, String split)
	{
		StringBuffer sbf = new StringBuffer();
		for (Map m : list)
		{
			if (m.get(field) != null && m.get(field).toString().length() > 0)
			{
				sbf.append(split + m.get(field).toString());
			}
		}
		if (sbf.length() > 0)
		{
			return sbf.substring(split.length());
		}
		return "";
	}

	/**
	 * 将List 中的某一个 字段 拿出来统一进行拼接
	 *
	 * 如 list = ["100","101","102"] item_id拼接，分隔符为[,]的话得到的结果为 100,101,102
	 *
	 * 如果拼写SQL需要单引号的情况下，分隔符可以写为[','] 得到的结果为 100','101','102 拿到后 只需要两边加个单引号就可以了
	 *
	 * @param list
	 * @param field
	 * @param split
	 * @return
	 */
	public static String concat(List list, String split)
	{
		StringBuffer sbf = new StringBuffer();
		for (Object str : list)
		{
			if (str.getClass().isArray())
			{
				sbf.append(split + Arrays.toString((Object[]) str));
			} else
			{
				sbf.append(split + str.toString());
			}
		}
		if (sbf.length() > 0)
		{
			return sbf.substring(split.length());
		}
		return "";
	}

	/**
	 * 将List 中的某一个 字段 拿出来统一进行拼接 用于sql查询的in
	 *
	 * 如 list =
	 * [{item_id:100,operate_date:2014年},{item_id:101,operate_date:2015年
	 * },{item_id:102,operate_date:2016年}] item_id拼接，得到的结果为 '100','101','102'
	 *
	 * 可以方便的执行SQLin
	 *
	 * @param list
	 * @param field
	 * @param split
	 * @return
	 */
	public static String concatSQLIN(List<Map> list, String field)
	{
		StringBuffer sbf = new StringBuffer();
		for (Map m : list)
		{
			if (m.get(field) != null && m.get(field).toString().length() > 0)
			{
				sbf.append("','" + m.get(field).toString());
			}
		}
		if (sbf.length() > 0)
		{
			return "'" + sbf.substring(3) + "'";
		}
		return "-1";
	}

	/**
	 * 将String[] 中的某一个 字段 拿出来统一进行拼接
	 *
	 * 如 arr = [2014年,2015年,2016年] item_id拼接，分隔符为[,]的话得到的结果为 2014年,2015年,2016年
	 *
	 * 如果拼写SQL需要单引号的情况下，分隔符可以写为[','] 得到的结果为 2014年','2015年','2016年 拿到后
	 * 只需要两边加个单引号就可以了
	 *
	 * @param list
	 * @param field
	 * @param split
	 * @return
	 */
	public static String concat(String[] arr, String split)
	{
		StringBuffer sbf = new StringBuffer();
		for (String s : arr)
		{
			if (s != null && s.length() > 0)
			{
				sbf.append(split + s);
			}
		}
		if (sbf.length() > 0)
		{
			return sbf.substring(split.length());
		}
		return "";
	}

	/**
	 * 将String[] 中的某一个 字段 拿出来统一进行拼接
	 *
	 * 如 arr = [2014年,2015年,2016年] item_id拼接， 得到的结果为 '2014年','2015年','2016年'
	 *
	 * 可以 直接放入in查询
	 *
	 * @param list
	 * @param field
	 * @param split
	 * @return
	 */
	public static String concatSQLIN(String[] arr)
	{
		StringBuffer sbf = new StringBuffer();
		for (String s : arr)
		{
			if (s != null && s.length() > 0)
			{
				sbf.append(",'" + s + "'");
			}
		}
		if (sbf.length() > 0)
		{
			return sbf.substring(1);
		}
		return "-1";
	}

	/**
	 * 将 list<Map> 进行排序
	 *
	 * @param list
	 * @param key
	 * @return
	 */
	public static void sort(List<Map> list, String key, boolean isAsc)
	{
		SortUtils.sort(list, key, isAsc);
	}

	/**
	 * 将 list<Map> 进行排序
	 *
	 * @param list
	 * @param key
	 * @return
	 */
	public static void sort(List<Map> list, String key, boolean isAsc, boolean isNum)
	{
		SortUtils.sort(list, key, isAsc, isNum);
	}

	/**
     * 分割List
     * @param list 待分割的list
     * @param pageSize 每段list的大小
     * @return List<<List<T>>
     */
     public static <T> List<List<T>> splitList(List<T> list, int pageSize) {

        int listSize = list.size();                                                           //list的大小
        int page = (listSize + (pageSize-1))/ pageSize;                      //页数

        List<List<T>> listArray = new ArrayList<List<T>>();              //创建list数组 ,用来保存分割后的list
        for(int i=0;i<page;i++) {                                                         //按照数组大小遍历
            List<T> subList = new ArrayList<T>();                               //数组每一位放入一个分割后的list
            for(int j=0;j<listSize;j++) {                                                 //遍历待分割的list
                int pageIndex = ( (j + 1) + (pageSize-1) ) / pageSize;   //当前记录的页码(第几页)
                if(pageIndex == (i + 1)) {                                               //当前记录的页码等于要放入的页码时
                    subList.add(list.get(j));                                               //放入list中的元素到分割后的list(subList)
                }

                if( (j + 1) == ((j + 1) * pageSize) ) {                               //当放满一页时退出当前循环
                    break;
                }
            }
            listArray.add(subList);                                                         //将分割后的list放入对应的数组的位中
        }
        return listArray;
    }

     /**
      * 分割List
      * @param list 待分割的list
      * @param pageSize 每段list的大小
      * @return List<<List<T>>
      */
     public static <T> List<List<T>> splitList2(List<T> list, int pageSize) {
         List<List<T>> listArray = new ArrayList<List<T>>();

         ArrayList<T> al = new ArrayList<T>();
         for(T x : list){
             al.add(x);
             if (pageSize == al.size()){
                 listArray.add(al);
                 al = new ArrayList<T>();
             }
         }

         if (0 != al.size())
             listArray.add(al);

         return listArray;
     }

//     public static void main(String[] args) {
//    	 List<String> strlist = new ArrayList<String>();
//         for(int i=0;i<120;i++) {
//             strlist.add("aa" + (i+1));
//         }
//         List<List<String>> list = splitList2(strlist, 17);
//
//         int index = 1;
//         for(List<String> strlist2: list) {
//             System.out.println(index++);
//             System.out.println("----------------------------------");
//             for(String str: strlist2) {
//                 System.out.print(str + "\t");
//             }
//             System.out.println("END");
//             System.out.println();
//         }
//	}


}
