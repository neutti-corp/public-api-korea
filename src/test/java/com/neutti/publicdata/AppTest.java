package com.neutti.publicdata;

import com.neutti.publicdata.service.GeneralService;
import com.neutti.publicdata.vo.ParamVO;
import com.neutti.publicdata.vo.specific.PubliclyAnnouncedLandPriceParamVO;
import com.neutti.publicdata.vo.specific.PubliclyAnnouncedLandPriceResultDtlVO;
import com.neutti.publicdata.vo.specific.PubliclyAnnouncedLandPriceResultVO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;


/**
 * Unit test for simple App.
 */

@Slf4j
public class AppTest
    extends TestCase
{
    public void testSomething() {
        retrievePubliclyAnnouncedLandPriceWms();

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
}
