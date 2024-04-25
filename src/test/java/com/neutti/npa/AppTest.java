package com.neutti.npa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.external.B552657.AedInfo;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.korea.DataApiService;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest extends TestCase {
    public void test() {
        DataApiService<AedInfo> service = DataApiService.getInstance();
        service.setPath("/B552657/AEDInfoInqireService/getAedLcinfoInqire");
        service.setServiceKey("maQN9ERlzOBZfcjIu1K8huRRCi+YhF++eEy+tnCMTi3QGADZlvLzq/YgO2t3O95nzI5MGT5dkNmx03gEAnzqyA==");
        service.setItemTypeRef(new TypeReference<AedInfo>() {});
        ParamVO param = new ParamVO();
        param.setPageNo(1);
        param.setNumOfRows(3);
        ResponseVO<AedInfo> r = service.response(param);
        //log.info(r.getBody().getItems().get(0).getClass().toString());
        log.info(r.getBody().getItems().toString());
    }
}
