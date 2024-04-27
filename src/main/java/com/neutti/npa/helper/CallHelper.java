package com.neutti.npa.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.neutti.npa.NHostType;
import com.neutti.npa.NParamVO;
import com.neutti.npa.NResultVO;
import com.neutti.npa.vo.data_go.DataResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class CallHelper {

    public <T> DataResponseVO<T> load(NHostType type, String path, NParamVO param, TypeReference<T> typeRef) {
        if(typeRef == null) {
            log.warn("DataTypeRef 이 선언이 안 되있을 경우 항목(Data) 객체는 Map 형태로 변환됩니다.");
        }
        HttpURLConnection conn = null;
        DataResponseVO<T> result = null;
        String responseString = "";

        try {
            UrlHelper urlHelper = new UrlHelper();
            URL url = urlHelper.generate(type, path, param);
            //log.debug("Access URL: " + url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(false); // Disable automatic redirects

            // Initial request
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
            result = new DataResponseVO<>();
            return result;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public <T> NResultVO<T> loadItem(NHostType type, String path, NParamVO param, TypeReference<T> typeRef) {
        HttpURLConnection conn = null;
        DataResponseVO<T> result = null;
        String responseString = "";
        try {
            UrlHelper urlHelper = new UrlHelper();
            URL url = urlHelper.generate(type, path, param);
            //log.debug("Access URL: " + url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setInstanceFollowRedirects(false); // Disable automatic redirects

            // Initial request
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_MOVED_TEMP || responseCode == HttpURLConnection.HTTP_MOVED_PERM) {
                String newUrl = conn.getHeaderField("Location");
                log.debug("Redirect to: " + newUrl);
                url = new URL(newUrl);  // Follow the redirect manually
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                responseCode = conn.getResponseCode(); // Get response code from the new connection
            }

            if (responseCode >= 200 && responseCode <= 300) {
                responseString = IOUtils.toString(conn.getInputStream(), StandardCharsets.UTF_8);
                String contentType = conn.getContentType();
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
                    _typeRef = mapper.getTypeFactory().constructType(new TypeReference<List<T>>() {});
                } else {
                    JavaType _type = mapper.getTypeFactory().constructType(typeRef.getType());
                    _typeRef = mapper.getTypeFactory().constructParametricType(List.class, _type);
                }
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                List<T> items = mapper.readValue(responseString, _typeRef);
                result = new DataResponseVO<>();
                result.setData(items);
                result.setRequestUrl(url);
                return result;
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
