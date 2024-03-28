package com.neutti.publicdata.vo;

import lombok.Data;

import java.util.HashMap;

@Data
public class ParamVO {
    /* api url */
    private String url;
    /* api serviceKey */
    private String serviceKey;
    /* page Number of datas */
    private int pageNo = 1;
    /* rows per page */
    private int numOfRows = 10;
    /* when isJson = false, it means xml. the other means json */
    private Boolean isJson = false;
    /* when isCamelCase = true, the return variables changed to camel case */
    private Boolean isCamelCase = false;
    /* etc parameter */
    private HashMap<String, Object> etcParam;

}
