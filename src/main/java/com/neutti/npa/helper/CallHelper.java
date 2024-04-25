package com.neutti.npa.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class CallHelper {

    public <T> ResponseVO<T> load(HostType type, String path, ParamVO param, TypeReference<T> typeRef){
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
                JavaType ___type;
                if(typeRef == null){
                    TypeReference __type = new TypeReference<ResponseVO<T>>() {};
                    ___type = xmlMapper.getTypeFactory().constructType(__type);
                }else{
                    JavaType __type = xmlMapper.getTypeFactory().constructType(typeRef.getType());
                    ___type = xmlMapper.getTypeFactory().constructParametricType(ResponseVO.class,__type);
                }
                if(log.isDebugEnabled()){
                    String r = IOUtils.toString(conn.getInputStream());
                    log.debug("result origin string : " + r);
                    result = xmlMapper.readValue(r, ___type);
                }else{
                    result = xmlMapper.readValue(conn.getInputStream(), ___type);
                }
                return result;
            } else {
                result = new ResponseVO<>();
                return result;
            }
        }catch (Exception e){
            log.error(e.getMessage());
            result = new ResponseVO<>();
            return result;
        }finally {
            assert conn != null;
            conn.disconnect();
        }
    }
}
