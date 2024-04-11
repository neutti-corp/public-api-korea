package com.neutti.publicdata.service;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.neutti.publicdata.helper.NHelper;
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

    public HashMap<String, Object>[] retrieveJsonToMapData(String url, HashMap<String, Object> param) throws Exception {
        HashMap<String, Object>[] mapArray = null;

        String getUrl = url + "?";

        if(param != null){
            List<String> keyList = new ArrayList<>(param.keySet());

            for (String key : keyList) {
                getUrl += "&" + key + "=" + URLEncoder.encode(param.get(key).toString(), "UTF-8");
            }
        }

        mapArray = NHelper.getHashMapArrayDataFromUrlJson(getUrl);

//        if(param.getIsCamelCase()){
//            mapArray = NHelper.convertKeysToCamelCase(mapArray);
//        }

        return mapArray;

    }

    public HashMap<String, Object>[] retrieveXmlToMapData(String url, HashMap<String, Object> param) throws Exception {
        HashMap<String, Object>[] mapArray = null;

        String getUrl = url + "?";

        if(param != null){
            List<String> keyList = new ArrayList<>(param.keySet());

            for (String key : keyList) {
                getUrl += "&" + key + "=" + URLEncoder.encode(param.get(key).toString(), "UTF-8");
            }
        }

        mapArray = NHelper.getHashMapArrayDataFromUrlXml(getUrl);

//        if(param.getIsCamelCase()){
//            mapArray = NHelper.convertKeysToCamelCase(mapArray);
//        }

        return mapArray;

    }


}
