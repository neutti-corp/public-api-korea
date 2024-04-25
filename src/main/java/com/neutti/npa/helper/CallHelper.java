package com.neutti.npa.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.neutti.npa.external.B552657.AedInfo;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
@Slf4j
public class CallHelper {

    public <T> ResponseVO<T> load(HostType type, String path, ParamVO param) throws Exception{
        HttpURLConnection conn = null;
        ResponseVO<T> result = null;
        try{
            UrlHelper urlHelper = new UrlHelper();
            URL url = urlHelper.generate(type,path,param);
            log.debug("access url : " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                XmlMapper xmlMapper = new XmlMapper();
                xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
                xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true);
                String r = IOUtils.toString(conn.getInputStream());
                log.info(r);
                TypeReference<ResponseVO<T>> typeRef = new TypeReference<ResponseVO<T>>(){};
                //TypeReference<ResponseVO<AedInfo>> typeRef = new TypeReference<ResponseVO<AedInfo>>(){};
                //ResponseVO<typeRef>
                result = xmlMapper.readValue(r, typeRef);
                return result;
            } else {
                log.debug("x");
                result = new ResponseVO<>();
                return result;
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            conn.disconnect();
            return result;
        }
    }

}
