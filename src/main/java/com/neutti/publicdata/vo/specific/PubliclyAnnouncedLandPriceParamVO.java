package com.neutti.publicdata.vo.specific;

import lombok.Data;

@Data
public class PubliclyAnnouncedLandPriceParamVO {
    private String pnu;
    private String stdrYear;
    private int numOfRows = 10;
    private int pageNo = 1;
    private String key;
    private String domain;
    private boolean isJson = true;
    private boolean isCamelCase = true;

}
