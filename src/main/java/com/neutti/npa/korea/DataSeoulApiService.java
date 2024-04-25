package com.neutti.npa.korea;

import com.neutti.npa.service.APIPullService;
import com.neutti.npa.vo.data_go.ResponseVO;

import java.net.URL;

/**
 * <a href="https://data.seoul.go.kr/">서울열린데이터광장</a>
 */
public class DataSeoulApiService implements APIPullService {
    @Override
    public ResponseVO response(String serviceKey, String apiNum, String reqPath, String servicePath) {
        return null;
    }

    @Override
    public ResponseVO response(String serviceKey, URL url) {
        return null;
    }
}
