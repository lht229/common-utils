package com.common.utils.utils.common.reflect;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;

public class Reflect {

//	public static void main(String[] args) throws IllegalArgumentException,
//			IllegalAccessException {
//
//		People people = new People("liu", null, null,null,null);
//
//		try {
//			try {
//				reflectTest(people);
//			} catch (InvocationTargetException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IntrospectionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} catch (InstantiationException | IllegalAccessException
//				| NoSuchFieldException | SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		java.lang.reflect.Field[] fields = people.getClass().getDeclaredFields();
//		for (java.lang.reflect.Field f : fields) {
//			f.setAccessible(true);
//			System.out.println(f.getName() + ":" + f.get(people));
//		}
//	}

	public static void methodTest(Object obj) throws IntrospectionException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		Class<? extends Object> clazz = obj.getClass();
		Field[] fields = obj.getClass().getDeclaredFields();// 获得属性
		for (Field field : fields) {
			PropertyDescriptor pd = new PropertyDescriptor(field.getName(),
					clazz);
			Method getMethod = pd.getReadMethod();// 获得get方法
			Method wM = pd.getWriteMethod();// 获得set方法

			Object o = getMethod.invoke(obj);// 执行get方法返回一个Object

			if (o == null) {
				Class[] types = getMethod.getParameterTypes();
				getMethod.setAccessible(true);
				wM.invoke(obj, "");// 因为知道是int类型的属性，所以传个int过去就是了。。实际情况中需要判断下他的参数类型
			}

		}
	}

	public static void reflectTest(Object obj) throws InstantiationException,
			IllegalAccessException, NoSuchFieldException, SecurityException, IntrospectionException, IllegalArgumentException, InvocationTargetException {

		Field[] fields = obj.getClass().getDeclaredFields();// 获得属性
		Field[] fieldss = obj.getClass().getFields();// 获得属性

//		Class<? extends Object> clazz = obj.getClass();

		for (Field f : fields) {
			f.setAccessible(true);// 更改权限

//			PropertyDescriptor pd = new PropertyDescriptor(f.getName(),	clazz);
//			Method getMethod = pd.getReadMethod();// 获得get方法
//			Object o = getMethod.invoke(obj);// 执行get方法返回一个Object
			Type type = f.getType();// 得到此属性的类型
			if (null == f.get(obj)) {
				if (type.equals(byte.class) || type.equals(Byte.class)) {
					f.set(obj, 0);
		        }
		        if (type.equals(short.class) || type.equals(Short.class)) {
					f.set(obj, 0);
		        }
		        if (type.equals(int.class) || type.equals(Integer.class)) {
					f.set(obj, 0);
		        }
		        if (type.equals(long.class) || type.equals(Long.class)) {
					f.set(obj, 0L);
		        }
		        if (type.equals(float.class) || type.equals(Float.class)) {
					f.set(obj, 0.0f);
		        }
		        if (type.equals(double.class) || type.equals(Double.class)) {
					f.set(obj, 0.0d);
		        }
		        if (type.equals(char.class) || type.equals(Character.class)) {
					f.set(obj, '\u0000');
		        }
		        if (type.equals(BigDecimal.class) ) {
					f.set(obj, BigDecimal.ZERO);
		        }
		        if (type.equals(String.class) ) {
					f.set(obj, "");
		        }
			}
		}

	}
	public static void convert(Object object)
	{
		Field[] fileds = object.getClass().getDeclaredFields();
		for(Field field:fileds)
		{
			field.setAccessible(true);
			if((field.getType() == String.class))
			{
				try {
					if(field.get(object) == null)
					{
						field.set(object, field.getType().newInstance());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
