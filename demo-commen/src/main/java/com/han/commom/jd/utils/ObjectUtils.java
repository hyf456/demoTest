package com.han.commom.jd.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class ObjectUtils {

    public static boolean isEmpty(Object bean) {
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(bean);
        for (PropertyDescriptor origDescriptor : origDescriptors) {
            String name = origDescriptor.getName();
            if ("class".equals(name)) {
                continue;
            }
            if (PropertyUtils.isReadable(bean, name)) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(bean, name);
                    if (propIsEmpty(value)) {
                        continue;
                    } else {
                        return false;
                    }
                } catch (IllegalArgumentException ie) {
                    ;
                } catch (Exception e) {
                    ;
                }
            }
        }
        return true;
    }

    public static boolean propIsEmpty(Object obj) {
        if (obj == null) {
	        return true;
        } else if (obj instanceof CharSequence) {
	        return ((CharSequence) obj).length() == 0;
        } else if (obj instanceof Collection) {
	        return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
	        return ((Map) obj).isEmpty();
        } else if (obj.getClass().isArray()) {
	        return Array.getLength(obj) == 0;
        }

        return false;
    }
}
