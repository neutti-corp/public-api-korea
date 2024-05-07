# Public API Bridge - 한국

# 홈페이지 (작업중)
http://npacode.com

# Installation 
#### maven
```xml
<dependency>
    <groupId>com.neutti.npa</groupId>
    <artifactId>npa-korea</artifactId>
    <version>0.1.3</version>
</dependency>
```
##### gradle
    implementation 'com.neutti.npa:npa-korea:0.1.3'

# Usage
### sample code
```java
@Slf4j
public class SampleTest {
    /**
     * 국토교통부_아파트매매 실거래 상세 자료
     * https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15057511
     */
    @Test
    public void getRTMSDataSvcAptTradeDev() throws NpaException {
        NService service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
        // allow full url
        //service.setDataPath("http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
        service.setCertKey(/*발급서비스키*/);
        NParamVO param = new NParamVO();
        param.add("LAWD_CD", "11110");
        param.add("DEAL_YMD", "202312");
        NResultVO r = service.response(param);
        // result
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());
    }
    /**
     * Possible General Type - 자체적으로 생성한 VO를 활용하세요.
     */
    @Test
    public void getRTMSDataSvcAptTradeDev_WithType() throws NpaException {
        NService<RTMSData> service = NServiceFactory.getService(NHostType.DATA_GO);
        service.setDataPath("/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev");
        service.setCertKey(/*발급서비스키*/);
        // RTMSData VO 사용
        service.setDataTypeRef(new TypeReference<RTMSData>() {});
        NParamVO param = new NParamVO();
        param.add("LAWD_CD", "11110");
        param.add("DEAL_YMD", "202312");
        NResultVO<RTMSData> r = service.response(param);
        // result
        log.info(r.getRequestUrl().toString());
        log.info(r.getData().toString());
    }
}
```

# 지원사이트(계속추가)
### 공개 공공 API
```java
public enum NHostType {
    /**
     * <a href="https://www.data.go.kr/">공공데이터포털</a>
     */
    DATA_GO,
    /**
     * <a href="https://data.gg.go.kr/">경기데이터드림</a>
     */
    DATA_GG,
    /**
     * <a href="https://www.vworld.kr/dev/v4api.do">디지털트윈국토</a>
     */
    VWORLD,
    /**
     * <a href="https://www.koreaexim.go.kr/ir/HPHKIR019M01">한국수출입은행</a>
     */
    EXIM,
}
```
### 비공개 API 지원

# Category
