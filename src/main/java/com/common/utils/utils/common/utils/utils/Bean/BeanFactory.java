package com.common.utils.utils.common.utils.utils.Bean;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

import com.common.utils.utils.common.utils.ValidUtil;

/**
 * 提供一些BeanUti需要的方法
 */
public class BeanFactory {


    /**
     * 存放BeanUtil解析过的JavaBean数据
     * 只获取简单的属性字段
     */
    protected static Map<String,Map<String,BeanStruct>> BEAN_SIMPLE_PROPERTIES = new Hashtable<String, Map<String, BeanStruct>>();


    /**
     * 存放BeanUtil解析过的JavaBean数据
     * 只获取简单的属性字段(忽略字段名字的大小写)
     */
    protected static Map<String,Map<String,BeanStruct>> BEAN_SIMPLE_PROPERTIESIGNORE = new Hashtable<String, Map<String, BeanStruct>>();


    static {
        //可以实现实现明确的JavaBean的配置
    }

    public static boolean isDeclaredField(String className, String pro) throws ClassNotFoundException {
        Class   classz = Class.forName(className);
        Field[] fields = classz.getFields();
        if (ValidUtil.isValid(fields)) {
            for (Field f : fields) {
                if (f.getName().equals(pro)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 将JavaBean进行解析并存在在static变量中
     *
     * @param obj
     */
    public static void add(Object obj) throws IntrospectionException, ClassNotFoundException {
        add(obj.getClass());
    }


    public static void add(Class clazz) throws IntrospectionException, ClassNotFoundException {
        String className = clazz.getName();
        if (!ValidUtil.isValid(BEAN_SIMPLE_PROPERTIES.get(className))) {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
            if (proDescrtptors != null && proDescrtptors.length > 0) {
                Map<String,BeanStruct> simpleProperties = new Hashtable<String, BeanStruct>();
                Map<String,BeanStruct> simplePropertiesIgnore = new Hashtable<String, BeanStruct>();
                for (PropertyDescriptor propDesc : proDescrtptors) {
                    String fieldName = propDesc.getName();
                    if (!"class".equals(fieldName)) {
                        Object type = propDesc.getPropertyType();
                        Method readMethod = propDesc.getReadMethod();
                        Method writeMethod = propDesc.getWriteMethod();
                        boolean isDeclared = isDeclaredField(className, fieldName);
                        simpleProperties.put(fieldName, new BeanStruct(fieldName, type, readMethod, writeMethod, isDeclared));
                        simplePropertiesIgnore.put(fieldName.toLowerCase(), new BeanStruct(fieldName, type, readMethod, writeMethod, isDeclared));
                    }
                }
                BEAN_SIMPLE_PROPERTIES.put(className, simpleProperties);
                BEAN_SIMPLE_PROPERTIESIGNORE.put(className, simplePropertiesIgnore);
            }
        }
    }

}
