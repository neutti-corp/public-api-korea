package com.neutti.npa.vo.data_go;

import com.neutti.npa.external.B552657.AedInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ItemsVO<T> {
    private List<Map> item;
}
