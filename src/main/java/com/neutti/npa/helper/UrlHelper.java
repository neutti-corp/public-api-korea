package com.neutti.npa.helper;

import com.neutti.npa.NHostType;
import com.neutti.npa.NParamVO;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class UrlHelper {

    public URL generate(NHostType type, String path, NParamVO param) throws MalformedURLException, UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder();
        String targertUrl = "";
        String keyName = "serviceKey";
        switch (type){
            case DATA_GO:   /* 공공데이터 포털 */
                if(path.contains("OpenAPI_ToolInstallPackage")){
                    /* 국토교통부 */
                    targertUrl = "openapi.molit.go.kr";
                }else{
                    targertUrl = "apis.data.go.kr";
                }
                break;
            case EXIM:
                targertUrl = "www.koreaexim.go.kr";
                keyName = "authkey";
                break;
            case SEX_OFFENDER:
                targertUrl = "api.sexoffender.go.kr";
                keyName = null;
                break;
            case KOBIS:
                targertUrl = "www.kobis.or.kr";
                keyName = "key";
                break;
            case MOLIT:
                targertUrl = "openapi.molit.go.kr";
                break;
            case DATA_GG:
                break;
            case DATA_SEOUL:
                break;
            case VWORLD:

                break;
        }
        setupUrlBuilder(urlBuilder, path, targertUrl, param, keyName);

        return new URL(urlBuilder.toString());
    }

    private void setupUrlBuilder (StringBuilder urlBuilder, String path, String targetUrl, NParamVO param, String keyName) throws UnsupportedEncodingException {

        if(path.startsWith("http://" + targetUrl)){
            path = path.replace("http://" + targetUrl,"");
        }
        if(path.startsWith("https://" + targetUrl)){
            path = path.replace("https://" + targetUrl,"");
        }
        urlBuilder.append("http://").append(targetUrl);
        if(!path.startsWith("/")) urlBuilder.append("/");
        urlBuilder.append(path).append("?");
        if(keyName != null){
            if(URLDecoder.decode(param.getServiceKey(),"utf-8").equals(param.getServiceKey())){
                urlBuilder.append(keyName).append("=").append(param.getServiceKey()).append("&");
            }else{
                urlBuilder.append(keyName).append("=").append(URLEncoder.encode(param.getServiceKey(),"utf-8")).append("&");
            }
        }

        urlBuilder.append("pageNo").append("=").append(param.getPageNo()).append("&");
        urlBuilder.append("numOfRows").append("=").append(param.getNumOfRows()).append("&");

        Map<String, Object> etcParam = param.getEtcParam();
        for(Object key : etcParam.keySet() ){
            Object value = etcParam.get(key);
            if(value instanceof String){
                urlBuilder.append(key).append("=").append(URLEncoder.encode(String.valueOf(value), "utf-8")).append("&");
            }else{
                urlBuilder.append(key).append("=").append(value).append("&");
            }
        }

    }
}
