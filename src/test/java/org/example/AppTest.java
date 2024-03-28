package org.example;

import com.neutti.publicdata.service.GeneralService;
import com.neutti.publicdata.service.JsonService;
import com.neutti.publicdata.vo.HospitalVO;
import com.neutti.publicdata.vo.ParamVO;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.HashMap;

/**
 * Unit test for simple App.
 */
public class AppTest
    extends TestCase
{
    public void testSomething() {
        GeneralService service = new GeneralService();
        String url = "https://apis.data.go.kr/6260000/MedicInstitService/MedicalInstitInfo";
        String serviceKey = "maQN9ERlzOBZfcjIu1K8huRRCi+YhF++eEy+tnCMTi3QGADZlvLzq/YgO2t3O95nzI5MGT5dkNmx03gEAnzqyA==";
        try {
            ParamVO paramVO = new ParamVO();
            paramVO.setUrl(url);
            paramVO.setServiceKey(serviceKey);
            paramVO.setIsJson(false);
            paramVO.setPageNo(1);
            paramVO.setNumOfRows(20);
            HashMap<String, Object> etcParam = new HashMap<String, Object>();
            etcParam.put("instit_nm", "동아대학교병원");
            paramVO.setEtcParam(etcParam);
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
