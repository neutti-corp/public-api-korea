package com.neutti.npa.vo.specific;

import lombok.Data;

import java.util.List;

@Data
public class PubliclyAnnouncedLandPriceResultVO {
    private String resultCode;
    private String resultMsg;
    private List<PubliclyAnnouncedLandPriceResultDtlVO> dataList;
}
