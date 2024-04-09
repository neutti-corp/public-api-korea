package com.neutti.publicdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neutti.publicdata.helper.NHelper;
import com.neutti.publicdata.vo.ParamVO;
import com.neutti.publicdata.vo.specific.PubliclyAnnouncedLandPriceParamVO;
import com.neutti.publicdata.vo.specific.PubliclyAnnouncedLandPriceResultDtlVO;
import com.neutti.publicdata.vo.specific.PubliclyAnnouncedLandPriceResultVO;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

        HashMap<String, Object>[] mapArray = null;

        String url = "https://api.vworld.kr/ned/data/getIndvdLandPriceAttr";
        String serviceKey = param.getKey();
        int pageNo = param.getPageNo();
        int numOfRows = param.getNumOfRows();
        String domain = param.getDomain();
        String pnu = param.getPnu();
        String stdrYear = param.getStdrYear();
        boolean isJson = param.isJson();
        String format;
        if(isJson){
            format = "json";
        }else{
            format = "xml";
        }
        serviceKey = NHelper.ensureDecoded(serviceKey);

        StringBuilder urlBuilder = new StringBuilder(url); /* URL */
        StringBuilder parameter  = new StringBuilder();
        parameter.append("?" + URLEncoder.encode("key","UTF-8") + "=" + serviceKey); /*key*/
        if(domain != null)  parameter.append("&" + URLEncoder.encode("domain","UTF-8") + "=" + domain); /*domain*/
        if(pnu != null)  parameter.append("&" + URLEncoder.encode("pnu","UTF-8") + "=" + URLEncoder.encode(pnu, "UTF-8")); /* 고유번호(8자리 이상) */
        if(stdrYear != null)  parameter.append("&" + URLEncoder.encode("stdrYear","UTF-8") + "=" + URLEncoder.encode(stdrYear, "UTF-8")); /* 기준연도(YYYY: 4자리) */
        parameter.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode(format, "UTF-8")); /* 응답결과 형식(xml 또는 json) */
        parameter.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(String.valueOf(numOfRows), "UTF-8")); /* 검색건수 (최대 1000) */
        parameter.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8")); /* 페이지 번호 */

        String getUrl = urlBuilder.toString() + parameter.toString();

        mapArray = NHelper.getHashMapArrayDataFromUrl(getUrl, isJson);

        ObjectMapper mapper = new ObjectMapper();
        List<PubliclyAnnouncedLandPriceResultDtlVO> rtrnList = new ArrayList<PubliclyAnnouncedLandPriceResultDtlVO>();
        PubliclyAnnouncedLandPriceResultVO rtrnVO = new PubliclyAnnouncedLandPriceResultVO();

        for(int i=0; i< mapArray.length; i++){
            HashMap<String, Object> map = mapArray[i];
            if(i == 0){
                String resultCode = (String)map.get("resultCode");
                if(resultCode != null && !resultCode.isEmpty()){
                    rtrnVO.setResultCode(resultCode);
                    rtrnVO.setResultMsg((String)map.get("resultMsg"));
                }
            }
            PubliclyAnnouncedLandPriceResultDtlVO vo = mapper.convertValue(map, PubliclyAnnouncedLandPriceResultDtlVO.class);
            rtrnList.add(vo);
        }
        rtrnVO.setDataList(rtrnList);

        return rtrnVO;

    }
}
