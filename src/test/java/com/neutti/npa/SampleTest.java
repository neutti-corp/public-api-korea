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
        NService<RTMSData> service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
        // allow full url
        //service.setDataPath("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
        //service.setCertKey(/*발급서비스키*/);
        service.setDataTypeRef(new TypeReference<RTMSData>() {});
        NParamVO param = new NParamVO();
        param.add("LAWD_CD", "11110");
        param.add("DEAL_YMD", "202312");
        NResultVO<RTMSData> r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());
    }
}
