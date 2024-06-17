package com.neutti.npa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.external.M15057511.RTMSData;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
@Slf4j
public class SampleTest {
    /**
     * 국토교통부_아파트매매 실거래 상세 자료
     * https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15057511
     */
    @Test
    public void getRTMSDataSvcAptTradeDev() throws NpaException {
        NService<DataVO> service = NServiceFactory.getPrivateService();
        service.setDataPath("http://nexus.wedb.co.kr:9099/query/json?qid=HeungUPMapper.sel_tb_b1_obslast");
        service.setRequestMethod("POST");
        service.setDataTypeRef(new TypeReference<DataVO>() {});
        NParamVO param = new NParamVO();
        NResultVO<DataVO> r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());
    }
}
