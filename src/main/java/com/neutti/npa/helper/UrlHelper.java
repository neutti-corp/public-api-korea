package com.neutti.npa.helper;

import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class UrlHelper {

    public URL generate(HostType type, String path, ParamVO param) throws MalformedURLException, UnsupportedEncodingException {
        StringBuilder urlBuilder = new StringBuilder();
        String targertUrl = "";
        switch (type){
            case DATA_GO:   /* 공공데이터 포털 */
                if(path.contains("OpenAPI_ToolInstallPackage")){
                    /* 국토교통부 */
                    targertUrl = "openapi.molit.go.kr";
                }else{
                    targertUrl = "apis.data.go.kr";
                }
                break;
            case DATA_GG:
                break;
            case DATA_SEOUL:
                break;
            case VWORLD:

                break;
        }
        setupUrlBuilder(urlBuilder, path, targertUrl, param);

        return new URL(urlBuilder.toString());
    }

    private void setupUrlBuilder (StringBuilder urlBuilder, String path, String targetUrl, ParamVO param) throws UnsupportedEncodingException {
        if(path.startsWith("http://" + targetUrl)){
            path = path.replace("http://" + targetUrl,"");
        }
        if(path.startsWith("https://" + targetUrl)){
            path = path.replace("https://" + targetUrl,"");
        }
        urlBuilder.append("http://").append(targetUrl);
        if(!path.startsWith("/")) urlBuilder.append("/");
        urlBuilder.append(path).append("?");
        if(URLDecoder.decode(param.getServiceKey(),"utf-8").equals(param.getServiceKey())){
            urlBuilder.append("serviceKey").append("=").append(param.getServiceKey()).append("&");
        }else{
            urlBuilder.append("serviceKey").append("=").append(URLEncoder.encode(param.getServiceKey(),"utf-8")).append("&");
        }
        urlBuilder.append("pageNo").append("=").append(param.getPageNo()).append("&");
        urlBuilder.append("numOfRows").append("=").append(param.getNumOfRows()).append("&");

        Map<String, Object> etcParam = param.getEtcParam();
        for(Object key : etcParam.keySet() ){
            urlBuilder.append(key).append("=").append(etcParam.get(key)).append("&");
        }

    }
}
