package com.neutti.publicdata.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ResultVO {
    private String resultCode;
    private String resultMsg;
    private DataType dataType;
    private Map resultInfo;
    private List<Map> items = new ArrayList<>();
}
