package com.neutti.publicdata;

import com.neutti.publicdata.service.APIPullService;
import com.neutti.publicdata.vo.ResultVO;

import java.net.URL;

/**
 * <a href="https://data.seoul.go.kr/">서울열린데이터광장</a>
 */
public class DataSeoulApiService implements APIPullService {
    @Override
    public ResultVO response(String serviceKey, String apiNum, String reqPath, String servicePath) {
        return null;
    }

    @Override
    public ResultVO response(String serviceKey, URL url) {
        return null;
    }
}
