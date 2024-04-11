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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

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

    public static HashMap<String, Object>[] getHashMapArrayDataFromUrlXml(String url) throws Exception {
        URL targetUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
        conn.setRequestMethod("GET");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document xml = dBuilder.parse(conn.getInputStream());
        xml.getDocumentElement().normalize();

        // Check for an error response first
        if (xml.getDocumentElement().getTagName().equals("OpenAPI_ServiceResponse")) {
            return handleErrorResponse(xml.getDocumentElement());
        }

        List<HashMap<String, Object>> mapsList = new ArrayList<>();
        findAndProcessListElements(xml.getDocumentElement(), mapsList);

        @SuppressWarnings("unchecked")
        HashMap<String, Object>[] mapArray = new HashMap[mapsList.size()];
        mapArray = mapsList.toArray(mapArray);

        return mapArray;
    }

    private static HashMap<String, Object>[] handleErrorResponse(Element errorElement) {
        // Extract error details
        String errMsg = errorElement.getElementsByTagName("errMsg").item(0).getTextContent();
        String returnAuthMsg = errorElement.getElementsByTagName("returnAuthMsg").item(0).getTextContent();
        String returnReasonCode = errorElement.getElementsByTagName("returnReasonCode").item(0).getTextContent();

        // Prepare an error message map
        HashMap<String, Object> errorMap = new HashMap<>();
        errorMap.put("Error Message", errMsg);
        errorMap.put("Authorization Message", returnAuthMsg);
        errorMap.put("Reason Code", returnReasonCode);

        @SuppressWarnings("unchecked")
        HashMap<String, Object>[] errorResponse = new HashMap[1];
        errorResponse[0] = errorMap;

        return errorResponse;
    }

    private static void findAndProcessListElements(Node node, List<HashMap<String, Object>> mapsList) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            // Assuming list-like structures are those where multiple child elements share the same tag name
            HashMap<String, Integer> tagCounts = new HashMap<>();
            NodeList children = element.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    tagCounts.put(child.getNodeName(), tagCounts.getOrDefault(child.getNodeName(), 0) + 1);
                }
            }

            for (String tag : tagCounts.keySet()) {
                if (tagCounts.get(tag) > 1) { // Found a repeating structure
                    NodeList repeatingElements = element.getElementsByTagName(tag);
                    for (int i = 0; i < repeatingElements.getLength(); i++) {
                        Node listItem = repeatingElements.item(i);
                        HashMap<String, Object> map = new HashMap<>();
                        NodeList listItemChildren = listItem.getChildNodes();
                        for (int j = 0; j < listItemChildren.getLength(); j++) {
                            Node childNode = listItemChildren.item(j);
                            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                                map.put(childNode.getNodeName(), childNode.getTextContent().trim());
                            }
                        }
                        if (!map.isEmpty()) {
                            mapsList.add(map);
                        }
                    }
                    break; // Assuming only one list-like structure per parent element
                }
            }

            // Recurse for non-repeating child elements (to find nested lists)
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE && tagCounts.getOrDefault(child.getNodeName(), 0) <= 1) {
                    findAndProcessListElements(child, mapsList);
                }
            }
        }
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

    public static String settingGetUrl(String url, HashMap<String, Object> param) throws Exception{
        StringBuilder getUrl = new StringBuilder(url);

        if (param != null && !param.isEmpty()) {
            getUrl.append("?");
            List<String> keyList = new ArrayList<>(param.keySet());

            // Use a flag to check if it's the first parameter
            boolean isFirstParam = true;
            for (String key : keyList) {
                if (!isFirstParam) {
                    // Append "&" only if it's not the first parameter
                    getUrl.append("&");
                }
                // Append the parameter and its encoded value
                getUrl.append(key).append("=").append(URLEncoder.encode(param.get(key).toString(), "UTF-8").replace("+", "%20"));
                isFirstParam = false;
            }
        }
        return getUrl.toString();
    }

}
