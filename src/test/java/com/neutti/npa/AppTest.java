package com.neutti.npa;

import com.neutti.npa.external.B552657.AedInfo;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.korea.DataApiService;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest extends TestCase {
    public void testSomething() throws Exception {
        String path = "/B552657/AEDInfoInqireService/getAedLcinfoInqire";
        ParamVO param = new ParamVO();
        param.setServiceKey("maQN9ERlzOBZfcjIu1K8huRRCi+YhF++eEy+tnCMTi3QGADZlvLzq/YgO2t3O95nzI5MGT5dkNmx03gEAnzqyA==");
        param.setPageNo(1);
        param.setNumOfRows(3);
        DataApiService service = new DataApiService();
        CallHelper call = new CallHelper();
        ResponseVO<AedInfo> r = call.load(HostType.DATA_GO, path, param);
        log.info(r.getBody().getItems().toString());
    }
}
