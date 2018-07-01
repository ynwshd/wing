package com.fr.util.base;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

/**
 * 扩展spring的BeanUtils，增加拷贝属性排除null值的功能(注：String为null不考虑)
 *
 * @author
 *
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

	public static void copyNotNullProperties(Object source, Object target, String[] ignoreProperties) throws BeansException {
		copyNotNullProperties(source, target, null, ignoreProperties);
	}

	public static void copyNotNullProperties(Object source, Object target, Class<?> editable) throws BeansException {
		copyNotNullProperties(source, target, editable, null);
	}

	public static void copyNotNullProperties(Object source, Object target) throws BeansException {
		copyNotNullProperties(source, target, null, null);
	}

	/**
	 * bean 属性复制 如有BigDeimal转换为String
	 *
	 * @param source
	 *            源对象
	 * @param target
	 *            目标对象
	 */
	public static void copyPropertiesConvertBigDeimalToString(Object source, Object target) {

		Field[] fields = source.getClass().getDeclaredFields();

		for (Field field : fields) {

			try {

				Field targetField = target.getClass().getDeclaredField(field.getName());
				Class<?> targetFieldType = targetField.getType();
				Class<?> sourceFieldType = field.getType();
				PropertyDescriptor pdSource = new PropertyDescriptor(field.getName(), source.getClass());
				PropertyDescriptor pdTarget = new PropertyDescriptor(targetField.getName(), target.getClass());
				Method methodSource = pdSource.getReadMethod();
				Method methodTarget = pdTarget.getWriteMethod();
				Object fieldVal = methodSource.invoke(source);

				if (sourceFieldType.isAssignableFrom(java.math.BigDecimal.class)
						&& targetFieldType.isAssignableFrom(java.lang.String.class)) {

					if (null == fieldVal) { // 为空的字段不做处理
						continue;
					}

					BigDecimal bigDecimalVal = (BigDecimal) fieldVal;

					String strVal = bigDecimalVal.toString();

					methodTarget.invoke(target, strVal);
				} else if (sourceFieldType.isAssignableFrom(java.sql.Timestamp.class)
						&& targetFieldType.isAssignableFrom(java.lang.String.class)) {
					if (null == fieldVal) { // 为空的字段不做处理
						continue;
					}

					Timestamp timestampVal = (Timestamp) fieldVal;

					String strVal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestampVal);

					methodTarget.invoke(target, strVal);
				} else {

					if (null == fieldVal || StringUtils.isEmpty(String.valueOf(fieldVal))) { // 为空的字段不做加密
						continue;
					}

					methodTarget.invoke(target, fieldVal);
				}

			} catch (NoSuchFieldException | SecurityException | IntrospectionException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException ex) {

			}
		}
	}

	private static void copyNotNullProperties(Object source, Object target, Class<?> editable, String[] ignoreProperties)
			throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName()
						+ "] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null && (ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);

						if (value == null) {
							continue;
						}

						if (value != null || readMethod.getReturnType().getName().equals("java.lang.String")) {// 这里判断以下value是否为空，当然这里也能进行一些特殊要求的处理
																												// 例如绑定时格式转换等等，如果是String类型，则不需要验证是否为空
							boolean isEmpty = false;
							if (value instanceof Set) {
								Set s = (Set) value;
								if (s == null || s.isEmpty()) {
									isEmpty = true;
								}
							} else if (value instanceof Map) {
								Map m = (Map) value;
								if (m == null || m.isEmpty()) {
									isEmpty = true;
								}
							} else if (value instanceof List) {
								List l = (List) value;
								if (l == null || l.size() < 1) {
									isEmpty = true;
								}
							} else if (value instanceof Collection) {
								Collection c = (Collection) value;
								if (c == null || c.size() < 1) {
									isEmpty = true;
								}
							}
							if (!isEmpty) {
								Method writeMethod = targetPd.getWriteMethod();
								if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
									writeMethod.setAccessible(true);
								}
								writeMethod.invoke(target, value);
							}
						}
					} catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}

}
