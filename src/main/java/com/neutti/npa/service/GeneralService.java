package com.neutti.npa.service;

import com.neutti.npa.helper.NHelper;

import java.util.HashMap;

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
