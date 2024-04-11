package com.neutti.publicdata.helper;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class NHelper {

    public static HashMap[] convertKeysToCamelCase(HashMap<String, Object>[] maps) {
        return Arrays.stream(maps).map(map -> {
            HashMap<String, Object> newMap = new HashMap<>();
            map.forEach((key, value) -> newMap.put(toCamelCase(key), value));
            return newMap;
        }).toArray(HashMap[]::new);
    }

    private static String toCamelCase(String key) {
        String[] parts = key.split("_");
        String rtrnString;
        if(parts.length == 1){
            rtrnString = key;
        }else{
            StringBuilder camelCaseString = new StringBuilder();
            for (int i = 0; i < parts.length; i++) {
                camelCaseString.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
            }
            rtrnString = camelCaseString.toString().substring(0, 1).toLowerCase() + camelCaseString.toString().substring(1);
        }


        return rtrnString;
    }

    public static HashMap[] convertKeysToSnakeCase(HashMap<String, Object>[] maps) {
        return Arrays.stream(maps).map(map -> {
            HashMap<String, Object> newMap = new HashMap<>();
            map.forEach((key, value) -> newMap.put(camelToSnake(key), value));
            return newMap;
        }).toArray(HashMap[]::new);
    }


    private static String camelToSnake(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (Character.isUpperCase(ch)) {
                if (i > 0) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }

        return result.toString();
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

    /**
     * url => hashMap 데이터 얻기(json, xml)
     * @param url
     * @return
     * @throws Exception
     */
    public static HashMap<String, Object>[] getHashMapArrayDataFromUrlJson(String url) throws Exception{
        URL targetUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
        conn.setRequestMethod("GET");
        HashMap<String, Object>[] mapArray = null;

        JsonArray itemsArray;

        try (InputStreamReader reader = new InputStreamReader(conn.getInputStream());
             JsonReader jsonReader = new JsonReader(reader)) {
            jsonReader.setLenient(true);
            JsonElement jsonElement = JsonParser.parseReader(jsonReader);

            if (jsonElement.isJsonArray()) { // Check if the root element is an array
                itemsArray = jsonElement.getAsJsonArray();
                mapArray = new Gson().fromJson(itemsArray, HashMap[].class);
            } else if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                itemsArray = findFirstArray(jsonObject);

                if(itemsArray == null){
                    mapArray = new HashMap[1];
                    mapArray[0] = retrieveErrorCodeMap(jsonObject);
                }else{
                    mapArray = new Gson().fromJson(itemsArray, HashMap[].class);
                }
            } else {
                throw new IllegalStateException("Expected JSON array or object but found neither");
            }


        } catch (IllegalStateException e){
            e.printStackTrace();
        }

        return mapArray;

    }

    public static HashMap<String, Object>[] getHashMapArrayDataFromUrlXml(String url) throws Exception{
        URL targetUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
        conn.setRequestMethod("GET");
        HashMap<String, Object>[] mapArray = null;
        try (InputStreamReader reader = new InputStreamReader(conn.getInputStream());) {
            StringBuilder xmlData = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                xmlData.append(line);
            }

            String xmlString = xmlData.toString();
            Document xml = convertStringToDocument(xmlString);

            // Dynamically find the first 'array-like' node
            NodeList firstSignificantNodeList = findFirstSignificantNodeList(xml);

            if(firstSignificantNodeList != null){
                mapArray = new HashMap[firstSignificantNodeList.getLength()];
                for (int i = 0; i < firstSignificantNodeList.getLength(); i++) {
                    Node child = firstSignificantNodeList.item(i);
                    NodeList grandChilds = child.getChildNodes();
                    HashMap<String, Object> values = new HashMap<>();
                    for (int k = 0; k < grandChilds.getLength(); k++) {
                        Node grandChild = grandChilds.item(k);
                        String key = grandChild.getNodeName().trim();
                        String value = grandChild.getTextContent().trim();
                        values.put(key, value);
                    }
                    mapArray[i] = values;
                }
            }else{
                Element root = xml.getDocumentElement();

                // Prepare the HashMap
                HashMap<String, Object> map = new HashMap<>();
                mapArray = new HashMap[1];
                // Get all child nodes of the root element
                NodeList children = root.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    if (children.item(i) instanceof Element) {
                        Element child = (Element) children.item(i);

                        // Use the tag name as the key and the text content as the value
                        map.put(child.getTagName(), child.getTextContent());
                    }
                }
                mapArray[0]=map;

            }


        }
        return mapArray;
    }

    public static String fetchAndEncodeBase64(String urlString) {
        try {
            URL url = new URL(urlString);

            try (InputStream inputStream = url.openStream();
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                byte[] dataBytes = outputStream.toByteArray();

                String base64Encoded = Base64.getEncoder().encodeToString(dataBytes);

                return base64Encoded;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private static HashMap<String, Object> retrieveErrorCodeMap(JsonObject  jobject) {
        HashMap<String, Object> rtrnMap = null;

        for (String key : jobject.keySet()) {
            JsonElement nestedElement = jobject.get(key);
            if (nestedElement.isJsonObject()) {
                JsonObject nestedObject = nestedElement.getAsJsonObject();

                String resultMsg = nestedObject.has("resultMsg") ? nestedObject.get("resultMsg").getAsString() : null;
                String resultCode = nestedObject.has("resultCode") ? nestedObject.get("resultCode").getAsString() : null;

                rtrnMap = new HashMap<String, Object>();
                rtrnMap.put("resultMsg", resultMsg);
                rtrnMap.put("resultCode", resultCode);
                break;
            }
        }

        return rtrnMap;
    }


    private static JsonArray findFirstArray(JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        } else if (jsonElement.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                JsonArray foundArray = findFirstArray(entry.getValue());
                if (foundArray != null) {
                    return foundArray;
                }
            }
        }
        return null; // No array found in this path
    }

    private static Document convertStringToDocument(String xmlStr) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new java.io.ByteArrayInputStream(xmlStr.getBytes()));
    }

    private static NodeList findFirstSignificantNodeList(Document document) {
        // Logic to find the first node with multiple child elements
        // This is a simplified example; you might need more robust logic depending on your XML structure
        NodeList allNodes = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < allNodes.getLength(); i++) {
            Node node = allNodes.item(i);
            if (node.hasChildNodes() && node.getChildNodes().getLength() > 1) {
                return node.getChildNodes();
            }
        }
        return null; // No significant node found
    }

}
