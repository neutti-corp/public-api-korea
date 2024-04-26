package com.neutti.npa.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.service.APIPullService;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import lombok.Data;

import java.net.URL;

/**
 * <a href="https://data.gg.go.kr/">경기데이터드림</a>
 */
@Data
public class DataGGApiService<T> implements APIPullService {
    protected DataGGApiService(){}
    public static <E> DataGGApiService<E> getInstance() {
        return new DataGGApiService<>();
    }
    private final HostType hostType = HostType.DATA_GG;
    private String path1;
    private String serviceKey1;
    private TypeReference<T> itemTypeRef;
    @Override
    public ResponseVO response(String serviceKey, String apiNum, String reqPath, String servicePath) {
        return null;
    }

    @Override
    public ResponseVO response(String serviceKey, URL url) {
        return null;
    }

    @Override
    public ResponseVO response(ParamVO param) {
        return null;
    }
}
