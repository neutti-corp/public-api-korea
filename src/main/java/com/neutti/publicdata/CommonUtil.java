package com.neutti.publicdata;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

    public static String ensureDecoded(String input) {

        String withPlaceholder = input.replace("+", "%2B");
        String decoded;
        try {
            // Try decoding
            decoded = URLDecoder.decode(withPlaceholder, StandardCharsets.UTF_8.toString());

            // If the decoded string is different from the original, the original was encoded
            if (!decoded.equals(input)) {
                return decoded; // The input was encoded, return decoded
            }
        } catch (Exception e) {
            // Handle potential exception, for example, if the decoding fails
            e.printStackTrace();
        }
        // If decoding did not change the string, it was not encoded
        return input; // The input was not encoded, return original
    }
}
