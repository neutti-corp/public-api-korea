package com.neutti.publicdata.service;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.neutti.publicdata.CommonUtil;
import com.neutti.publicdata.vo.ParamVO;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralService {

    public HashMap<String, Object>[] retrieveData(ParamVO param) throws Exception {
        String resultType;
        HashMap<String, Object>[] mapArray = null;

        String url = param.getUrl();
        String serviceKey = param.getServiceKey();
        Boolean isJson = param.getIsJson();
        int pageNo = param.getPageNo();
        int numOfRows = param.getNumOfRows();
        HashMap<String, Object> etcParam = param.getEtcParam();

        serviceKey = CommonUtil.ensureDecoded(serviceKey);

        if(isJson){
            resultType = "json";
        }else {
            resultType = "xml";
        }
        String getUrl = url + "?serviceKey=" + URLEncoder.encode(serviceKey, "UTF-8") + "&resultType=" + resultType + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows;

        if(etcParam != null){
            List<String> keyList = new ArrayList<>(etcParam.keySet());

            for (String key : keyList) {
                getUrl += "&" + key + "=" + URLEncoder.encode(etcParam.get(key).toString(), "UTF-8");
            }
        }

        URL targetUrl = new URL(getUrl);
        HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
        conn.setRequestMethod("GET");

        if(isJson){
            JsonArray itemsArray = new JsonArray();

            try (InputStreamReader reader = new InputStreamReader(conn.getInputStream());
                 JsonReader jsonReader = new JsonReader(reader)) {
                jsonReader.setLenient(true);
                JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    JsonElement bodyElement = entry.getValue().getAsJsonObject().get("body");
                    if (bodyElement != null && bodyElement.isJsonObject()) {
                        JsonObject bodyObject = bodyElement.getAsJsonObject();
                        JsonElement itemsElement = bodyObject.get("items");
                        if (itemsElement != null && itemsElement.isJsonObject()) {
                            itemsArray = itemsElement.getAsJsonObject().get("item").getAsJsonArray();
                            break; // Assuming first matching 'item' array is what you need
                        }
                    }
                }
            } catch (IllegalStateException e){
                throw new Exception("You've exceeded API rate limits");
            }

            mapArray = new Gson().fromJson(itemsArray, HashMap[].class);

        } else {
            try (InputStreamReader reader = new InputStreamReader(conn.getInputStream());) {
                StringBuilder xmlData = new StringBuilder();
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    xmlData.append(line);
                }

                String xmlString = xmlData.toString();

                HashMap<String, Object> values = new HashMap<String, Object>();
                Document xml = convertStringToDocument(xmlString);

                NodeList childs = xml.getElementsByTagName("item");
                if(childs.getLength() == 0){
                    childs = xml.getElementsByTagName("row");
                }

                Node child;
                Node grandChild;
                mapArray =  new HashMap[childs.getLength()];
                for (int i = 0; i < childs.getLength(); i++) {
                    child = childs.item(i);
                    NodeList grandChilds = child.getChildNodes();
                    values = new HashMap<String, Object>();
                    for (int k = 0; k < grandChilds.getLength(); k++) {
                        grandChild = grandChilds.item(k);
                        String key = grandChild.getNodeName().replace("#text", "").replaceAll("(?m)^[ \t]*\r?\n", "");
                        String value = grandChild.getTextContent().replaceAll("(?m)^[ \t]*\r?\n", "");
                        if(!"".equals(key)){
                            values.put(key, value);
                        }

                    }

                    mapArray[i] = values;

                }

            }
        }

        if(param.getIsCamelCase()){
            mapArray = CommonUtil.convertKeysToCamelCase(mapArray);
        }

        return mapArray;

    }



    private static Document convertStringToDocument(String xmlStr) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(
                    xmlStr)));
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private NodeList getNodeListFromDocument(Document document){
        return document.getElementsByTagName("item");
    }
}
