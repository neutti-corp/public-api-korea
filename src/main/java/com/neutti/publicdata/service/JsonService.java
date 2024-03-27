package com.neutti.publicdata.service;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.neutti.publicdata.CommonUtil;
import com.neutti.publicdata.vo.HospitalVO;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Map;

public class JsonService {
    public HashMap<String, Object>[] retrieveData(String paramUrl, String paramServiceKey) throws Exception {

        URL url = new URL(paramUrl + "?serviceKey=" + paramServiceKey + "&resultType=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        HashMap<String, Object>[] rtrnMap;
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

        HashMap<String, Object>[] mapArray = new Gson().fromJson(itemsArray, HashMap[].class);


        return CommonUtil.convertKeysToCamelCase(mapArray);

    }


}
