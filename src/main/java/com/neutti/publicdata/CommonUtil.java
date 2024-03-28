package com.neutti.publicdata;

import java.util.Arrays;
import java.util.HashMap;

public class CommonUtil {

    public static HashMap<String, Object>[] convertKeysToCamelCase(HashMap<String, Object>[] maps) {
        return Arrays.stream(maps).map(map -> {
            HashMap<String, Object> newMap = new HashMap<>();
            map.forEach((key, value) -> newMap.put(toCamelCase(key), value));
            return newMap;
        }).toArray(HashMap[]::new);
    }

    private static String toCamelCase(String key) {
        String[] parts = key.split("_");
        StringBuilder camelCaseString = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            camelCaseString.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
        }
        String rtrnString = camelCaseString.toString().substring(0, 1).toLowerCase() + camelCaseString.toString().substring(1);
        return rtrnString;
    }
}
