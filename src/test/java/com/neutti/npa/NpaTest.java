package com.neutti.npa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.external.B552657.AedInfo;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.service.korea.DataApiService;
import com.neutti.npa.service.korea.EximApiService;
import com.neutti.npa.vo.data_go.DataResponseVO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Unit test for simple App.
 */
@Slf4j
public class NpaTest extends TestCase {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = NpaTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (Exception ex) {
            log.error("Error loading properties", ex);
        }
    }

    public void test1() throws NpaException {
        NService<AedInfo> service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/B552657/AEDInfoInqireService/getAedLcinfoInqire");
        service.setCertKey(properties.getProperty("service1.key"));
        service.setDataTypeRef(new TypeReference<AedInfo>() {});
        NParamVO param = new NParamVO();
        param.setPageNo(1);
        param.setNumOfRows(3);
        DataResponseVO<AedInfo> r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());
    }

    public void test2() {
        DataApiService service = DataApiService.getInstance();
        service.setPath("/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
        service.setServiceKey(properties.getProperty("service2.key"));
        Map<String, Object> etcParam = new HashMap<String, Object>();
        etcParam.put("LAWD_CD", "11110");
        etcParam.put("DEAL_YMD", "201512");
        //service.setItemTypeRef(new TypeReference<AedInfo>() {});
        NParamVO param = new NParamVO();
        param.setPageNo(1);
        param.setNumOfRows(10);
        param.setEtcParam(etcParam);
        DataResponseVO r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getBody().getItems().toString());

    }

    public void test3() {
        EximApiService<Map> service = EximApiService.getInstance();
        service.setPath("/site/program/financial/exchangeJSON");
        service.setServiceKey(properties.getProperty("service3.key"));
        Map<String, Object> etcParam = new HashMap<String, Object>();
        etcParam.put("data", "AP01");
        NParamVO param = new NParamVO();
        param.setEtcParam(etcParam);
        NResultVO<Map> r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());

    }

    public void test4() {
        /*NService service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty");
        service.setCertKey(properties.getProperty("service1.key"));
        NParamVO param = new NParamVO();
        param.setPageNo(1);
        param.setNumOfRows(3);
        HashMap<String, Object> etcParam = new HashMap<String, Object>();
        etcParam.put("sidoName", "서울");
        etcParam.put("ver", "1.0");
        param.setEtcParam(etcParam);
        DataResponseVO r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getBody().getItems().toString());*/

    }
}
