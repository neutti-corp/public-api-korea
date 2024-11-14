package com.neutti.npa;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.external.B552657.AedInfo;
import com.neutti.npa.external.M15057511.RTMSData;
import com.neutti.npa.vo.WmsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        NParamVO param = new NParamVO();
//        param.add("stdr", "2018");
        NResultVO<AedInfo> r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());
    }
    /**
     * 국토교통부_아파트매매 실거래 상세 자료
     */
    @Test
    public void getRTMSDataSvcAptTradeDev() throws NpaException {
        NService<RTMSData> service = NServiceFactory.getService(NHostType.MOLIT);
        service.setDataPath("/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
        service.setCertKey(properties.getProperty("service2.key"));
        service.setDataTypeRef(new TypeReference<RTMSData>() {});
        NParamVO param = new NParamVO();
//        param.add("pageNo", 1);
//        param.add("numOfRows", 10);
        param.add("LAWD_CD", "11110");
        param.add("DEAL_YMD", "202312");
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
    @Test
    public void test5() throws NpaException {
        NService service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo");
        service.setCertKey(properties.getProperty("service1.key"));
        NParamVO param = new NParamVO();
        param.add("solYear", "2015");
        param.add("solMonth", "09");
        NResultVO r = service.response(param);
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());

    }
    /* 여성가족부_성범죄자 지역별 통계 */
    @Test
    public void test6() throws NpaException {
//        NService service = NServiceFactory.getService(NHostType.SEX_OFFENDER);
//        service.setDataPath("/openapi/SOCitysStats");
//        NParamVO param = new NParamVO();
//        NResultVO r = service.response(param);
//        log.info(r.getRequestUrl().toString());
//        log.info(r.getData().toString());

    }

    @Test
    public void test7() throws NpaException {
//        NService service = NServiceFactory.getService(NHostType.KOBIS);
//        service.setDataPath("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml");
//        service.setCertKey(properties.getProperty("service5.key"));
//        NParamVO param = new NParamVO();
//        param.add("targetDt", "20120101");
//        NResultVO r = service.response(param);
//        log.info(r.getRequestUrl().toString());
//        log.info(r.getData().toString());

    }

    /**
     *
     */
    @Test
    public void test10() throws NpaException, IOException {
        NService service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/B553084/ecoapi/EcologyzmpService/wms/getEcologyzmpWMS");
        service.setCertKey("uNv/f+F4gnIwp9xLSlXd5Mdt/zPHTM2nOuzjmEhfr7Qcx1zgTXxztte950Z4K91I1IMVj1s2Nond0m3vppGSiw==");
        WmsVO param = new WmsVO();
        param.setLayers("tbl_opn_eczm");
        param.setSrs("EPSG:5186");
        param.setBbox("210092.22524226015,587778.2997109308,277821.23446882307,612686.7957937511");
        param.setWidth(3291);
        param.setHeight(1201);
        param.setFormat("png");
        param.setTransparent(false);
        param.setBgcolor("0xFF0000");
        param.setExceptions("BLANK");
        BufferedImage r = service.getWmsImage(param);
        File outputfile = new File("image1.png");
        ImageIO.write(r, "png", outputfile);
    }
    @Test
    public void test11() throws NpaException, IOException {
        NService service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/1192000/apVhdService_Opz/getOpnOpzWMS");
        service.setCertKey("uNv/f+F4gnIwp9xLSlXd5Mdt/zPHTM2nOuzjmEhfr7Qcx1zgTXxztte950Z4K91I1IMVj1s2Nond0m3vppGSiw==");
        WmsVO param = new WmsVO();
        param.setSrs("EPSG:5179");
        param.setBbox("718918.25,1433106.875,1249928.75,2071446.875");
        param.setWidth(768);
        param.setHeight(654);
        BufferedImage r = service.getWmsImage(param);
        File outputfile = new File("image2.png");
        ImageIO.write(r, "png", outputfile);
    }
}
