package com.neutti.npa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.external.B552657.AedInfo;
import com.neutti.npa.external.OpenAPI_ToolInstallPackage.RTMSData;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.service.korea.DataApiService;
import com.neutti.npa.service.korea.EximApiService;
import com.neutti.npa.vo.data_go.DataResponseVO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Unit test for simple App.
 */
@Slf4j
public class NpaTest {

    private static final Properties properties = new Properties();
    @Before
    public void setup() {
        try (InputStream input = NpaTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (Exception ex) {
            log.error("Error loading properties", ex);
        }
    }
    /**
     * 국립중앙의료원_전국 자동심장충격기(AED) 정보 조회 서비스
     */
    @Test
    public void test1() throws NpaException {
        NService<AedInfo> service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/B552657/AEDInfoInqireService/getAedLcinfoInqire");
        service.setCertKey(properties.getProperty("service1.key"));
        service.setDataTypeRef(new TypeReference<AedInfo>() {});
        DataResponseVO<AedInfo> r = service.response(null);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());
    }
    /**
     * 국토교통부_아파트매매 실거래 상세 자료
     */
    @Test
    public void getRTMSDataSvcAptTradeDev() throws NpaException {
        NService<RTMSData> service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
        service.setCertKey(properties.getProperty("service2.key"));
        service.setDataTypeRef(new TypeReference<RTMSData>() {});
        NParamVO param = new NParamVO();
        param.add("LAWD_CD", "11110");
        param.add("DEAL_YMD", "201512");
        NResultVO<RTMSData> r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());
    }
    @Test
    public void test3() throws NpaException {
        NService service = NServiceFactory.getService(NHostType.EXIM);
        service.setDataPath("/site/program/financial/exchangeJSON");
        service.setCertKey(properties.getProperty("service3.key"));
        NParamVO param = new NParamVO();
        param.add("data", "AP01");
        NResultVO r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());

    }
    @Test
    public void test4() throws NpaException {
        NService service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty");
        service.setCertKey(properties.getProperty("service1.key"));
        NParamVO param = new NParamVO();
        param.add("sidoName", "서울");
        param.add("ver", "1.0");
        NResultVO r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());

    }
}
