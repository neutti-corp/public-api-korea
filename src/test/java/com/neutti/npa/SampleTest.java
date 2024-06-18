package com.neutti.npa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.external.M15057511.RTMSData;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
@Slf4j
public class SampleTest {

    @Test
    public void getRTMSDataSvcAptTradeDev() throws NpaException {
        NService<DataVO> service = NServiceFactory.getPrivateService();
        //service.setDataPath("http://nexus.wedb.co.kr:9099/query/json?qid=HeungUPMapper.sel_tb_b1_obsraw");
        service.setDataPath("http://nexus.wedb.co.kr:9099/query/json?qid=HeungUPMapper.sel_tb_b1_obslast");
        service.setRequestMethod("POST");
        service.setDataTypeRef(new TypeReference<DataVO>() {});
        NParamVO param = new NParamVO();
        param.add("mea_date_start", "2023-07-24");
        param.add("mea_date_end", "2023-07-25");
        param.add("odev_sq", "4");
        NResultVO<DataVO> r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());
    }
}
