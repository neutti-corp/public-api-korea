package com.neutti.npa.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.neutti.npa.NHostType;
import com.neutti.npa.NParamVO;
import com.neutti.npa.NResultVO;
import com.neutti.npa.vo.data_go.DataResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class CallHelper {
    public <T> DataResponseVO<T> load(NHostType type, String path, String requestMethod, NParamVO param, TypeReference<T> typeRef) {
        if(typeRef == null) {
            log.warn("DataTypeRef 이 선언이 안 되있을 경우 항목(Data) 객체는 Map 형태로 변환됩니다.");
        }
        if(requestMethod == null){
            requestMethod = "GET";
        }
        requestMethod = requestMethod.toUpperCase();
        HttpURLConnection conn = null;
        DataResponseVO<T> result = null;
        String responseString = "";
        try {
            UrlHelper urlHelper = new UrlHelper();
            URL url = urlHelper.generate(type, requestMethod, path, param);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            if(requestMethod.equalsIgnoreCase("GET")){
                conn.setInstanceFollowRedirects(false);
            }else{
                conn.setRequestProperty("Content-Type","application/json");
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setDefaultUseCaches(false);
                String jsonInString = new ObjectMapper()
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .writeValueAsString(param);
                IOUtils.copy(IOUtils.toInputStream(jsonInString, "UTF-8"), conn.getOutputStream());
            }
            int responseCode = conn.getResponseCode();
            if (responseCode >= 200 && responseCode <= 300) {
                responseString = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);
                String contentType = conn.getContentType();
                contentType.toLowerCase().contains("application/xml");
                boolean isJson = contentType != null && (contentType.contains("application/json") || contentType.contains("text/html"));
                boolean isXml = contentType != null && (contentType.contains("application/xml") || contentType.contains("text/xml"));
                ObjectMapper mapper;
                if (isJson) {
                    mapper = new ObjectMapper();
                } else if (isXml) {
                    mapper = new XmlMapper();
                } else {
                    throw new IllegalArgumentException("Unsupported content type: " + contentType);
                }
                JavaType _typeRef = null;
                if (typeRef == null) {
                    _typeRef = mapper.getTypeFactory().constructType(new TypeReference<DataResponseVO<T>>() {});
                } else {
                    JavaType _type = mapper.getTypeFactory().constructType(typeRef.getType());
                    _typeRef = mapper.getTypeFactory().constructParametricType(DataResponseVO.class, _type);
                }
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                result = mapper.readValue(responseString, _typeRef);
                result.setRequestUrl(url);
                result.setData(result.getBody().getItems());
                return result;
            } else {
                result = new DataResponseVO<>();
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return result;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    public <T> NResultVO<T> loadItem(NHostType type, String path, String requestMethod, NParamVO param, TypeReference<T> typeRef) {
        return loadItem(type, path, requestMethod, param, typeRef, null);
    }
    public <T> NResultVO<T> loadItem(NHostType type, String path, String requestMethod, NParamVO param, TypeReference<T> typeRef, Map requestProperty) {
        if(typeRef == null) {
            log.warn("DataTypeRef 이 선언이 안 되있을 경우 항목(Data) 객체는 Map 형태로 변환됩니다.");
        }
        if(requestMethod == null){
            requestMethod = "GET";
        }
        requestMethod = requestMethod.toUpperCase();
        HttpURLConnection conn = null;
        DataResponseVO<T> result = new DataResponseVO<>();
        String responseString = "";
        try {
            UrlHelper urlHelper = new UrlHelper();
            URL url = urlHelper.generate(type, requestMethod, path, param);
            result.setRequestUrl(url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            if(requestMethod.equalsIgnoreCase("GET")){
                //conn.setInstanceFollowRedirects(false);
            }else{
                conn.setRequestProperty("Content-Type","application/json");
                if(requestProperty != null){
                    Set iter = requestProperty.keySet();
                    for(Object key : iter){
                        conn.setRequestProperty((String) key, (String) requestProperty.get(key));
                    }
                }
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setDefaultUseCaches(false);
                String jsonInString = new ObjectMapper()
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                        .writeValueAsString(param.getEtcParam());
                IOUtils.copy(IOUtils.toInputStream(jsonInString, "UTF-8"), conn.getOutputStream());
            }
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
                String newUrl = conn.getHeaderField("Location");
                log.debug("Redirect to: " + newUrl);
                url = new URL(newUrl);  // Follow the redirect manually
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                responseCode = conn.getResponseCode(); // Get response code from the new connection
            }
            result.setResponseCode(responseCode);
            if (responseCode >= 200 && responseCode <= 300) {
                responseString = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);
                result.setResponseOriginalString(responseString);
                String contentType = conn.getContentType();
                boolean isJson = contentType != null && (contentType.contains("application/json") || contentType.contains("text/html"));
                boolean isXml = contentType != null && (contentType.contains("application/xml") || contentType.contains("text/xml"));
                ObjectMapper mapper;
                if (isJson) {
                    mapper = new ObjectMapper();
                } else if (isXml) {
                    mapper = new XmlMapper();
                } else {
                    if(responseString.startsWith("[") || responseString.startsWith("{")){
                        mapper = new ObjectMapper();
                    }else{
                        throw new IllegalArgumentException("Unsupported content type: " + contentType);
                    }
                }
                JavaType _typeRef = null;
                if(responseString.startsWith("[")){
                    // Array
                    if (typeRef == null) {
                        _typeRef = mapper.getTypeFactory().constructType(new TypeReference<List<T>>() {});
                    } else {
                        JavaType _type = mapper.getTypeFactory().constructType(typeRef.getType());
                        _typeRef = mapper.getTypeFactory().constructParametricType(List.class, _type);
                    }
                    mapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    List<T> items = mapper.readValue(responseString, _typeRef);

                    result.setData(items);
                    result.setRequestUrl(url);
                    return result;
                }else{
                    if (typeRef == null) {
                        _typeRef = mapper.getTypeFactory().constructType(new TypeReference<DataResponseVO<T>>() {});
                    } else {
                        _typeRef = mapper.getTypeFactory().constructType(typeRef.getType());
                    }
                    mapper.setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy());
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    T _result = mapper.readValue(responseString, _typeRef);
                    result.setItem(_result);
                    return result;
                }
            } else {
                result = new DataResponseVO<>();
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new DataResponseVO<>();
            return result;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

}
