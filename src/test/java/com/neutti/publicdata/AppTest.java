package com.neutti.publicdata;

import com.neutti.publicdata.service.GeneralService;
import com.neutti.publicdata.vo.ParamVO;
import com.neutti.publicdata.vo.specific.PubliclyAnnouncedLandPriceParamVO;
import com.neutti.publicdata.vo.specific.PubliclyAnnouncedLandPriceResultDtlVO;
import com.neutti.publicdata.vo.specific.PubliclyAnnouncedLandPriceResultVO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;


/**
 * Unit test for simple App.
 */

@Slf4j
public class AppTest
    extends TestCase
{
    public void testSomething() {
        //retrievePubliclyAnnouncedLandPrice();
        //retrieveJsonToMapData();
        //retrieveXmlToMapData2();
        //retrieveXmlToMapData3();
        retrieveXmlToMapData5();
        //retrieveJsonToMapData2();
    }

    // 공시지가 wms 이미지 불러오기
    private void retrievePubliclyAnnouncedLandPriceWms(){
        DataSpecificApiService service = new DataSpecificApiService();
        String url = "https://api.vworld.kr/ned/wms/getIndvdLandPriceWMS?key=A56F212F-B33B-3139-B776-DF93207F4BAC&crs=EPSG:4326&bbox=37.5666502857805,127.31259030366,37.5689495688305,127.316674702516&width=915&height=700&format=image/png";
        try{
            String base64 = service.retrievePubliclyAnnouncedLandPriceWms(url);
            System.out.println("base64: " + base64);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 공시지가 속성정보 가져오기
    private void retrievePubliclyAnnouncedLandPrice(){
        PubliclyAnnouncedLandPriceParamVO param = new PubliclyAnnouncedLandPriceParamVO();
        param.setKey("A56F212F-B33B-3139-B776-DF93207F4BAC");
        param.setPnu("1111017700102110000");
        param.setJson(true);

        DataSpecificApiService service = new DataSpecificApiService();
        try{
            PubliclyAnnouncedLandPriceResultVO rtrnVO = service.retrievePubliclyAnnouncedLandPrice(param);

            if(rtrnVO.getResultCode() == null || rtrnVO.getResultCode().isEmpty()){
                for(PubliclyAnnouncedLandPriceResultDtlVO dataMap : rtrnVO.getDataList()){
                    log.info(dataMap.toString());
                }
            }else{
                log.info("resultCode: " + rtrnVO.getResultCode() + ", resultMsg: " + rtrnVO.getResultMsg());
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void retrieveJsonToMapData() {
        GeneralService service = new GeneralService();
        String url = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON";
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("data", "AP01");
        param.put("authkey", "0XoXF60wost04g7mjaxCVOzN980NGE18");

        try {
            HashMap<String, Object>[] rsltList = service.retrieveJsonToMapData(url,param);
            for(HashMap<String, Object> data : rsltList){
                log.info(data.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private void retrieveXmlToMapData() {
        GeneralService service = new GeneralService();
        String url = "https://api.vworld.kr/req/data";
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("service", "data");
        param.put("request", "GetFeature");
        param.put("data", "LP_PA_CBND_BUBUN");
        param.put("version", "2.0");
        param.put("format","xml");
        param.put("size", "10");
        param.put("page", "1");
        param.put("attrfilter", "pnu:like:11140");
        param.put("columns", "pnu,jibun,bonbun,bubun,ag_geom,addr,gosi_year,gosi_month,jiga");
        param.put("geometry", "true");
        param.put("attribute", "true");
        param.put("crs", "EPSG:900913");
        param.put("key", "A56F212F-B33B-3139-B776-DF93207F4BAC");
        param.put("geomFilter", "LINESTRING(14135552.266976 4518634.8999433,14134863.139393 4518195.3870307)");

        try {
            HashMap<String, Object>[] rsltList = service.retrieveXmlToMapData(url,param);
            for(HashMap<String, Object> data : rsltList){
                log.info(data.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void retrieveXmlToMapData2() {
        String url = "http://api.sexoffender.go.kr/openapi/SOCitysStats/";
        HashMap<String, Object> param = new HashMap<String, Object>();
        GeneralService service = new GeneralService();
        try {
            HashMap<String, Object>[] rsltList = service.retrieveXmlToMapData(url,param);
            for(HashMap<String, Object> data : rsltList){
                log.info(data.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void retrieveXmlToMapData3() {
        GeneralService service = new GeneralService();
        String url = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/get24DivisionsInfo";
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("serviceKey", "maQN9ERlzOBZfcjIu1K8huRRCi%2BYhF%2B%2BeEy%2BtnCMTi3QGADZlvLzq%2FYgO2t3O95nzI5MGT5dkNmx03gEAnzqyA%3D%3D");
        param.put("solYear", "2024");


        try {
            HashMap<String, Object>[] rsltList = service.retrieveXmlToMapData(url,param);
            for(HashMap<String, Object> data : rsltList){
                log.info(data.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void retrieveXmlToMapData4() {
        GeneralService service = new GeneralService();
        String url = "https://nip.kdca.go.kr/irgd/cov19stats.do";
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("list", "all");

        try {
            HashMap<String, Object>[] rsltList = service.retrieveXmlToMapData(url,param);
            for(HashMap<String, Object> data : rsltList){
                log.info(data.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void retrieveXmlToMapData5() {
        GeneralService service = new GeneralService();
        String url = "https://apis.data.go.kr/1400377/forestPoint/forestPointListGeongugSearch";
        HashMap<String, Object> param = new HashMap<String, Object>();
        // Decoding 인증키 입력
        param.put("serviceKey", "maQN9ERlzOBZfcjIu1K8huRRCi+YhF++eEy+tnCMTi3QGADZlvLzq/YgO2t3O95nzI5MGT5dkNmx03gEAnzqyA==");
        param.put("pageNo", 1);
        param.put("numOfRows", 10);
        param.put("_type", "xml");
        param.put("excludeForecast", 0);

        try {
            HashMap<String, Object>[] rsltList = service.retrieveXmlToMapData(url,param);
            for(HashMap<String, Object> data : rsltList){
                log.info(data.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void retrieveJsonToMapData2(){
        GeneralService service = new GeneralService();
        String url = "https://apis.data.go.kr/1400377/forestPoint/forestPointListGeongugSearch";
        HashMap<String, Object> param = new HashMap<String, Object>();
        // Decoding 인증키 입력
        param.put("serviceKey", "maQN9ERlzOBZfcjIu1K8huRRCi+YhF++eEy+tnCMTi3QGADZlvLzq/YgO2t3O95nzI5MGT5dkNmx03gEAnzqyA==");
        param.put("pageNo", 1);
        param.put("numOfRows", 10);
        param.put("_type", "json");
        param.put("excludeForecast", 0);

        try {
            HashMap<String, Object>[] rsltList = service.retrieveJsonToMapData(url,param);
            for(HashMap<String, Object> data : rsltList){
                log.info(data.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
