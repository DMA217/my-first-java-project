package org.example.mapper;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Map;

public class ReflectionMapper {

    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    field.setAccessible(true);
                    Object value = map.get(field.getName());
                    field.set(obj, convertValue(value, field.getType()));
                }
            }
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("Ошибка маппинга: " + e.getMessage(), e);
        }
    }

    private static Object convertValue(Object value, Class<?> targetType) {
        if (value == null) return null;
        if (targetType.isInstance(value)) return value;

        if (targetType == Integer.class || targetType == int.class) {
            return ((Number) value).intValue();
        }
        if (targetType == Double.class || targetType == double.class) {
            return ((Number) value).doubleValue();
        }
        if (targetType == Long.class || targetType == long.class) {
            return ((Number) value).longValue();
        }
        if (targetType == String.class) {
            return value.toString();
        }
        if (targetType == LocalDateTime.class && value instanceof String) {
            return LocalDateTime.parse((String) value);
        }
        return value;
    }
}