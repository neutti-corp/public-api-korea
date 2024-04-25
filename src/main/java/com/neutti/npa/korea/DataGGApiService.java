package com.neutti.npa.korea;

import com.neutti.npa.service.APIPullService;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;

import java.net.URL;

/**
 * <a href="https://data.gg.go.kr/">경기데이터드림</a>
 */
public class DataGGApiService implements APIPullService {
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
