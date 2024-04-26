package com.neutti.npa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.external.B552657.AedInfo;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.korea.DataApiService;
import com.neutti.npa.korea.DataGGApiService;
import com.neutti.npa.korea.EximApiService;
import com.neutti.npa.korea.MolitApiService;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.ResultVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest extends TestCase {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (Exception ex) {
            log.error("Error loading properties", ex);
        }
    }

    public void test1() {
        DataApiService<AedInfo> service = DataApiService.getInstance();
        service.setPath("/B552657/AEDInfoInqireService/getAedLcinfoInqire");
        service.setServiceKey(properties.getProperty("service1.key"));
        service.setItemTypeRef(new TypeReference<AedInfo>() {});

        ParamVO param = new ParamVO();
        param.setPageNo(1);
        param.setNumOfRows(3);

        ResponseVO<AedInfo> r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getHeader().toString());
    }

    public void test2() {
        DataApiService service = DataApiService.getInstance();
        service.setPath("/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
        service.setServiceKey(properties.getProperty("service2.key"));
        Map<String, Object> etcParam = new HashMap<String, Object>();
        etcParam.put("LAWD_CD", "11110");
        etcParam.put("DEAL_YMD", "201512");
        //service.setItemTypeRef(new TypeReference<AedInfo>() {});
        ParamVO param = new ParamVO();
        param.setPageNo(1);
        param.setNumOfRows(10);
        param.setEtcParam(etcParam);
        ResponseVO r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getBody().getItems().toString());

    }

    public void test3() {
        EximApiService<Map> service = EximApiService.getInstance();
        service.setPath("/site/program/financial/exchangeJSON");
        service.setServiceKey(properties.getProperty("service3.key"));
        Map<String, Object> etcParam = new HashMap<String, Object>();
        etcParam.put("data", "AP01");
        ParamVO param = new ParamVO();
        param.setEtcParam(etcParam);
        ResultVO<Map> r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getItems().toString());

    }

    public void test4() {
        DataApiService service = DataApiService.getInstance();
        service.setPath("/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty");
        service.setServiceKey(properties.getProperty("service1.key"));
        ParamVO param = new ParamVO();
        param.setPageNo(1);
        param.setNumOfRows(3);
        HashMap<String, Object> etcParam = new HashMap<String, Object>();
        etcParam.put("sidoName", "서울");
        etcParam.put("ver", "1.0");
        param.setEtcParam(etcParam);
        ResponseVO r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getBody().getItems().toString());

    }
}
