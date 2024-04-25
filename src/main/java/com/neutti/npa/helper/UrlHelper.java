package com.neutti.npa.helper;

import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

public class UrlHelper {

    public URL generate(HostType type, String path, ParamVO param) throws MalformedURLException, UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder();
        switch (type){
            case DATA_GO:
                if(path.startsWith("http://apis.data.go.kr")){
                    path = path.replace("http://apis.data.go.kr","");
                }
                if(path.startsWith("https://apis.data.go.kr")){
                    path = path.replace("https://apis.data.go.kr","");
                }
                urlBuilder.append("http://apis.data.go.kr");
                if(!path.startsWith("/")) urlBuilder.append("/");
                urlBuilder.append(path).append("?");
                urlBuilder.append("serviceKey").append("=").append(URLEncoder.encode(param.getServiceKey(),"utf-8")).append("&");
                urlBuilder.append("pageNo").append("=").append(param.getPageNo()).append("&");
                urlBuilder.append("numOfRows").append("=").append(param.getNumOfRows()).append("&");
        }
        Map<String, Object> etcParam = param.getEtcParam();
        for(Object key : etcParam.keySet() ){
            urlBuilder.append(key).append("=").append(etcParam.get(key)).append("&");
        }
        return new URL(urlBuilder.toString());
    }
}
