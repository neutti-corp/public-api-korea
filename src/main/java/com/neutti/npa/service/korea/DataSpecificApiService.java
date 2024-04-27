package com.neutti.npa.service.korea;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neutti.npa.helper.NHelper;
import com.neutti.npa.vo.specific.PubliclyAnnouncedLandPriceParamVO;
import com.neutti.npa.vo.specific.PubliclyAnnouncedLandPriceResultDtlVO;
import com.neutti.npa.vo.specific.PubliclyAnnouncedLandPriceResultVO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 특정 api 서비스
 */
public class DataSpecificApiService {

    /**
     * 국토교통부_개별공시지가정보 속성정보 api
     * @param param
     * @return
     * @throws Exception
     */
    public PubliclyAnnouncedLandPriceResultVO retrievePubliclyAnnouncedLandPrice(PubliclyAnnouncedLandPriceParamVO param) throws Exception {
        // Basic URL setup
        String baseUrl = "https://api.vworld.kr/ned/data/getIndvdLandPriceAttr";
        String serviceKey = encodeValue(NHelper.ensureDecoded(param.getKey()));
        String format = param.isJson() ? "json" : "xml";

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("key", serviceKey);
        queryParams.put("format", format);
        queryParams.put("numOfRows", String.valueOf(param.getNumOfRows()));
        queryParams.put("pageNo", String.valueOf(param.getPageNo()));

        if (param.getDomain() != null) queryParams.put("domain", param.getDomain());
        if (param.getPnu() != null) queryParams.put("pnu", encodeValue(param.getPnu()));
        if (param.getStdrYear() != null) queryParams.put("stdrYear", encodeValue(param.getStdrYear()));

        StringBuilder queryBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            queryBuilder.append(encodeValue(entry.getKey()))
                    .append('=')
                    .append(encodeValue(entry.getValue()))
                    .append('&');
        }
        String query = queryBuilder.toString().replaceAll(".$", "");

        String fullUrl = baseUrl + "?" + query;

        HashMap<String, Object>[] mapArray;

        if(param.isJson()){
            mapArray = NHelper.getHashMapArrayDataFromUrlJson(fullUrl);
        }else{
            mapArray = NHelper.getHashMapArrayDataFromUrlXml(fullUrl);
        }

        ObjectMapper mapper = new ObjectMapper();
        List<PubliclyAnnouncedLandPriceResultDtlVO> rtrnList = new ArrayList<>();
        PubliclyAnnouncedLandPriceResultVO rtrnVO = new PubliclyAnnouncedLandPriceResultVO();

        for (int i = 0; i < mapArray.length; i++) {
            HashMap<String, Object> map = mapArray[i];
            if (i == 0) {
                String resultCode = (String) map.get("resultCode");
                if (resultCode != null && !resultCode.isEmpty()) {
                    rtrnVO.setResultCode(resultCode);
                    rtrnVO.setResultMsg((String) map.get("resultMsg"));
                }
            }
            PubliclyAnnouncedLandPriceResultDtlVO vo = mapper.convertValue(map, PubliclyAnnouncedLandPriceResultDtlVO.class);
            rtrnList.add(vo);
        }
        rtrnVO.setDataList(rtrnList);

        return rtrnVO;
    }

    // Utility method to encode URL parameter values
    private static String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // This should never happen with the UTF-8 encoding
            throw new RuntimeException(e);
        }
    }


    public String retrievePubliclyAnnouncedLandPriceWms(String url) throws Exception {
        String base64 = NHelper.fetchAndEncodeBase64(url);

        return base64;
    }
}
