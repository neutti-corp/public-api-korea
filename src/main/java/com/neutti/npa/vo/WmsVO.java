package com.neutti.npa.vo;

import com.google.gson.annotations.SerializedName;
import com.neutti.npa.NParamVO;
import lombok.Data;

@Data
public class WmsVO {
    private String layers;
    private String srs;
    private String bbox;
    private Integer width;
    private Integer height;
    private String format;
    private Boolean transparent;
    private String bgcolor;
    private String exceptions;

}
