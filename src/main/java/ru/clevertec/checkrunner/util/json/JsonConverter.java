package ru.clevertec.checkrunner.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import static java.lang.reflect.Array.get;
import static java.lang.reflect.Array.getLength;

public class JsonConverter {

    public static String toJson(Object object) {
        StringBuilder json = new StringBuilder();

        json.append("{");

        Field[] fields = object.getClass().getDeclaredFields();

        try {
            for (Field field : fields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                if (fieldValue != null) {
                    json.append("\"").append(field.getName()).append("\":");

                    if (field.getType().equals(String.class) || field.getType().equals(Character.class)) {
                        json.append("\"").append(fieldValue).append("\",");
                    } else if (isNumberOrBooleanOrPrimitive(field.getType())) {
                        json.append(fieldValue).append(",");
                    } else if (field.getType().isArray() || Iterable.class.isAssignableFrom(field.getType())) {
                        json.append(toJsonArray(fieldValue)).append(",");
                    } else {
                        json.append(toJson(fieldValue)).append(",");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (json.lastIndexOf(",") == json.length() - 1) {
            json.deleteCharAt(json.length() - 1);
        }
        json.append("}");

        return json.toString();
    }

    private static String toJsonArray(Object object) {
        StringBuilder jsonArray = new StringBuilder();

        jsonArray.append("[");

        if (object.getClass().isArray()) {
            int arrayLength = getLength(object);
            for (int i = 0; i < arrayLength; i++) {
                Object value = get(object, i);
                appendElementToJsonArray(jsonArray, value);
            }
        } else if (Iterable.class.isAssignableFrom(object.getClass())) {
            Iterable iterableObject = (Iterable) object;
            for (Object value : iterableObject) {
                appendElementToJsonArray(jsonArray, value);
            }
        }

        if (jsonArray.lastIndexOf(",") == jsonArray.length() - 1) {
            jsonArray.deleteCharAt(jsonArray.length() - 1);
        }
        jsonArray.append("]");

        return jsonArray.toString();
    }

    private static void appendElementToJsonArray(StringBuilder jsonArray, Object value) {
        if (isNumberOrBooleanOrPrimitive(value.getClass())) {
            jsonArray.append(value).append(",");
        } else if (value.getClass().equals(String.class) || value.getClass().equals(Character.class)) {
            jsonArray.append("\"").append(value).append("\",");
        } else if (value.getClass().isArray() || Iterable.class.isAssignableFrom(value.getClass())) {
            jsonArray.append(toJsonArray(value)).append(",");
        } else {
            jsonArray.append(toJson(value)).append(",");
        }
    }

    public static <T> T fromJson(String json, Class<T> classType) {
        Map<String, Object> fieldsValues = null;
        try {
            fieldsValues = new ObjectMapper().readValue(json, HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return setObjectFields(fieldsValues, classType);
    }

    private static <T> T setObjectFields(Map<String, Object> fieldsValues, Class<T> classType) {
        T object = null;
        try {
            object = classType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Field[] fields = classType.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = fieldsValues.get(field.getName());
            if (value != null) {
                try {
                    if (field.getType().equals(Character.class)) {
                        field.set(object, value.toString().charAt(0));
                    } else if (isNumberOrBooleanOrPrimitive(field.getType()) || field.getType().equals(String.class)) {
                        field.set(object, value);
                    } else {
                        field.set(object, setObjectFields(fieldsValues, field.getType()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return object;
    }

    private static boolean isNumberOrBooleanOrPrimitive(Class<?> classType) {
        return Number.class.isAssignableFrom(classType) || classType.equals(Boolean.class) || classType.isPrimitive();
    }
}
