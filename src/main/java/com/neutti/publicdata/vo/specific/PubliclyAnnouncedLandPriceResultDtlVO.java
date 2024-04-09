package com.neutti.publicdata.vo.specific;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class PubliclyAnnouncedLandPriceResultDtlVO {
    private String mnnmSlno;
    private String stdLandAt;
    private String pblntfDe;
    private String stdrYear;
    private String pnu;
    private String lastUpdtDt;
    private String regstrSeCodeNm;
    private String ldCode;
    private String ldCodeNm;
    private String stdrMt;
    private String pblntfPclnd;
    private String regstrSeCode;
}
