package com.neutti.npa.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.ResultVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
public class CallHelper {

    public <T> ResponseVO<T> load(HostType type, String path, ParamVO param, TypeReference<T> typeRef) {
        HttpURLConnection conn = null;
        ResponseVO<T> result = null;
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
                    _typeRef = mapper.getTypeFactory().constructType(new TypeReference<ResponseVO<T>>() {});
                } else {
                    JavaType _type = mapper.getTypeFactory().constructType(typeRef.getType());
                    _typeRef = mapper.getTypeFactory().constructParametricType(ResponseVO.class, _type);
                }
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                result = mapper.readValue(responseString, _typeRef);
                result.setRequestUrl(url);
                return result;
            } else {
                result = new ResponseVO<>();
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new ResponseVO<>();
            return result;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public <T> ResultVO<T> loadItem(HostType type, String path, ParamVO param, TypeReference<T> typeRef) {
        HttpURLConnection conn = null;
        ResponseVO<T> result = null;
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
                result = new ResponseVO<>();
                result.setItems(items);
                result.setRequestUrl(url);
                return result;
            } else {
                result = new ResponseVO<>();
                return result;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new ResponseVO<>();
            return result;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

}
