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

    public HashMap<String, Object>[] retrieveData(ParamVO param) throws Exception {
        String resultType;
        HashMap<String, Object>[] mapArray = null;

        String url = param.getUrl();
        String serviceKey = param.getServiceKey();
        Boolean isJson = param.getIsJson();
        int pageNo = param.getPageNo();
        int numOfRows = param.getNumOfRows();
        HashMap<String, Object> etcParam = param.getEtcParam();

        serviceKey = NHelper.ensureDecoded(serviceKey);

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

        mapArray = NHelper.getHashMapArrayDataFromUrl(getUrl, isJson);

        if(param.getIsCamelCase()){
            mapArray = NHelper.convertKeysToCamelCase(mapArray);
        }

        return mapArray;

    }

}
