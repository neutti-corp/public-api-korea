package com.neutti.npa;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class NParamVO {
    /* api url */
    private String url;
    /* api serviceKey */
    private String serviceKey;
    /* page Number of datas */
    private int pageNo = 1;
    /* rows per page */
    private int numOfRows = 10;
    /* when isJson = false, it means xml. the other means json */
    private Boolean isJson = true;
    /* when isCamelCase = true, the return variables changed to camel case */
    private Boolean isCamelCase = false;
    /* etc parameter */
    private Map<String, Object> etcParam = new HashMap<>();
    public void add(String key, Object value) {
        etcParam.put(key, value);
    }
}
