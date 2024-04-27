package com.neutti.npa.vo.vworld;

import com.neutti.npa.NResultVO;
import lombok.Data;

@Data
public class VworldResponseVO<T> extends NResultVO<T> {
    private ServiceVO service;
    private PageVO page;
    private NResultVO<T> result;
}
