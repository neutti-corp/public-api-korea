package com.neutti.publicdata.vo;

import lombok.Data;

import java.util.HashMap;

@Data
public class ParamVO {
    private String url;
    private String serviceKey;
    private int pageNo = 1;
    private int numOfRows = 10;
    private Boolean isJson = false;
    private HashMap<String, Object> etcParam;

}
