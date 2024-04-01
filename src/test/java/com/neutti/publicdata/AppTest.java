package com.neutti.publicdata;

import com.neutti.publicdata.service.GeneralService;
import com.neutti.publicdata.vo.ParamVO;
import junit.framework.TestCase;

import java.util.HashMap;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
    public void testSomething() {
        GeneralService service = new GeneralService();
        String url = "https://openapi.gg.go.kr/Animalhosptl";
        String serviceKey = "maQN9ERlzOBZfcjIu1K8huRRCi%2BYhF%2B%2BeEy%2BtnCMTi3QGADZlvLzq%2FYgO2t3O95nzI5MGT5dkNmx03gEAnzqyA%3D%3D";
        try {
            ParamVO paramVO = new ParamVO();
            paramVO.setUrl(url);
            paramVO.setServiceKey(serviceKey);
            paramVO.setIsJson(false);
            paramVO.setPageNo(1);
            paramVO.setNumOfRows(20);
            HashMap<String, Object> etcParam = new HashMap<String, Object>();
            etcParam.put("instit_nm", "동아대학교병원");
//            paramVO.setEtcParam(etcParam);
            paramVO.setIsCamelCase(true);

            HashMap<String, Object>[] rtrnMapArray = service.retrieveData(paramVO);
            for(HashMap<String, Object> dataMap : rtrnMapArray){
                System.out.println(dataMap.toString());
            }
        } catch (Exception e){
          e.printStackTrace();
        }


        // Setup
        // Call the method you want to test
        // Assert statements to verify the expected outcomes
    }
}
