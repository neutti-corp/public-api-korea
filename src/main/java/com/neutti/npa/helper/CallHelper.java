package com.neutti.npa.helper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
public class CallHelper {

    public <T> ResponseVO<T> load(HostType type, String path, ParamVO param, TypeReference<T> typeRef) {
        HttpURLConnection conn = null;
        ResponseVO<T> result = null;

        try {
            UrlHelper urlHelper = new UrlHelper();
            URL url = urlHelper.generate(type, path, param);
            log.debug("Access URL: " + url);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Checking the content type
            String contentType = conn.getContentType();
            boolean isJson = contentType != null && contentType.contains("application/json");
            boolean isXml = contentType != null && (contentType.contains("application/xml") || contentType.contains("text/xml"));

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                InputStream inputStream = conn.getInputStream();
                if (log.isDebugEnabled()) {
                    String responseString = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                    log.debug("Result origin string: " + responseString);
                    inputStream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8));
                }

                if (isJson) {
                    ObjectMapper jsonMapper = new ObjectMapper();
                    jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    jsonMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    result = jsonMapper.readValue(inputStream, constructJavaType(jsonMapper, typeRef));
                } else if (isXml) {
                    XmlMapper xmlMapper = new XmlMapper();
                    xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    xmlMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    result = xmlMapper.readValue(inputStream, constructJavaType(xmlMapper, typeRef));
                } else {
                    throw new IllegalArgumentException("Unsupported content type: " + contentType);
                }

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

    private <T> JavaType constructJavaType(ObjectMapper mapper, TypeReference<T> typeRef) {
        if (typeRef == null) {
            return mapper.getTypeFactory().constructType(new TypeReference<ResponseVO<T>>() {});
        } else {
            JavaType type = mapper.getTypeFactory().constructType(typeRef.getType());
            return mapper.getTypeFactory().constructParametricType(ResponseVO.class, type);
        }
    }
}
