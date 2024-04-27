package com.neutti.npa.vo.data_go;

import lombok.Data;

import java.util.List;

@Data
public class BodyVO<T> {
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;
    private List<T> items;
}
