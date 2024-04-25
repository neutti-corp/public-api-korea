package com.neutti.npa.korea;

import com.neutti.npa.service.APIPullService;
import com.neutti.npa.vo.data_go.ResponseVO;

import java.net.URL;

/**
 * <a href="https://www.data.go.kr/">공공데이터포털</a>
 */
public class DataApiService implements APIPullService {
    @Override
    public ResponseVO response(String serviceKey, String apiNum, String reqPath, String servicePath) {
        return null;
    }

    @Override
    public ResponseVO response(String serviceKey, URL url) {
        return null;
    }
}
