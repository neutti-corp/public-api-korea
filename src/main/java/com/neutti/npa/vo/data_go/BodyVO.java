package com.neutti.npa.vo.data_go;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neutti.npa.vo.DataType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class BodyVO<T> {
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;
    private List<T> items;
}
