package com.neutti.npa.service.korea;

import com.neutti.npa.NService;
import com.neutti.npa.NParamVO;
import com.neutti.npa.vo.data_go.DataResponseVO;

import java.net.URL;

/**
 * <a href="https://data.seoul.go.kr/">서울열린데이터광장</a>
 */
public class DataSeoulApiService implements NService {
    @Override
    public DataResponseVO response(String serviceKey, String apiNum, String reqPath, String servicePath) {
        return null;
    }

    @Override
    public DataResponseVO response(String serviceKey, URL url) {
        return null;
    }

    @Override
    public DataResponseVO response(NParamVO param) {
        return null;
    }
}
