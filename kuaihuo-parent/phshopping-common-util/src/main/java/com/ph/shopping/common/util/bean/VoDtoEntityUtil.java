package com.ph.shopping.common.util.bean;

import com.ph.shopping.common.util.container.ParamVerifyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version 1.0.0
 * @项目：phshopping-parent
 * @描述： VoDtoEntity转换工具操作类
 * @作者： 熊克文
 * @创建时间： 2017/5/14
 * @Copyright by xkw
 */
public abstract class VoDtoEntityUtil extends BeanUtils {
    // 日志
    private final static Logger logger = LoggerFactory.getLogger(VoDtoEntityUtil.class);

    private VoDtoEntityUtil() {
        throw new NullPointerException();
    }

    /**
     * 将来源source对象的相同属性值通过set方法返回到新对象target
     */
    public static <T> T convert(Object source, Class<T> targetClass) {
        return convert(source, targetClass, (String[]) null);
    }

    /**
     * @param ignoreProperties 排除字段
     * 将来源source对象的相同属性值通过set方法返回到新对象target
     */
    public static <T> T convert(Object source, Class<T> targetClass, String... ignoreProperties) {
        try {
            T target = targetClass.newInstance();
            copyProperties(source, target,ignoreProperties);
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("反射实例化对象失败", e.getCause());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将来源source对象不为空的字段的相同属性值通过set方法返回到新对象target
     */
    public static void copyPropertiesNotNull(Object source, Object target) throws BeansException {
        Class<?> actualEditable = source.getClass();
        PropertyDescriptor[] sourcePds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = new ArrayList<>();
        int var8 = sourcePds.length;
        for (int var9 = 0; var9 < var8; ++var9) {
            PropertyDescriptor sourcePd = sourcePds[var9];
            if (sourcePd != null) {
                Method readMethod = sourcePd.getReadMethod();
                if (readMethod != null) {
                    try {
                        if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                            readMethod.setAccessible(true);
                        }
                        Object value = readMethod.invoke(source);
                        if (!ParamVerifyUtil.objIsNotNull(value)) {
                            String readMethodName = readMethod.getName();
                            ignoreList.add(readMethodName.substring(3, 4).toLowerCase() + readMethodName.substring(4));
                        }
                    } catch (Throwable var15) {
                        throw new FatalBeanException("Could not copy property '" + sourcePd.getName() + "' from source to source", var15);
                    }
                }
            }
        }
        copyProperties(source, target, ignoreList.toArray(new String[ignoreList.size()]));
    }

    /**
     * 将来源List<source>转换成targetClass类的List<targetClass>
     */
    public static <T> List<T> convertList(List<?> list, Class<T> targetClass) {
        return list.stream().map(o -> convert(o, targetClass)).collect(Collectors.toList());
    }
}
