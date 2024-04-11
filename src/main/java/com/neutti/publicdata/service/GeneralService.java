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

        String getUrl = NHelper.settingGetUrl(url, param);

        mapArray = NHelper.getHashMapArrayDataFromUrlJson(getUrl);

        return mapArray;

    }

    public HashMap<String, Object>[] retrieveXmlToMapData(String url, HashMap<String, Object> param) throws Exception {
        HashMap<String, Object>[] mapArray = null;


        String getUrl = NHelper.settingGetUrl(url, param);

        mapArray = NHelper.getHashMapArrayDataFromUrlXml(getUrl);

        return mapArray;

    }


}
