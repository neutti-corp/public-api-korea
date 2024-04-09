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
