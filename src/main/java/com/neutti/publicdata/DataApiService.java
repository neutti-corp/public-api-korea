package com.neutti.publicdata;

import com.neutti.publicdata.service.APIPullService;
import com.neutti.publicdata.vo.ResultVO;

import java.net.URL;

/**
 * <a href="https://www.data.go.kr/">공공데이터포털</a>
 */
public class DataApiService implements APIPullService {
    @Override
    public ResultVO response(String serviceKey, String apiNum, String reqPath, String servicePath) {
        return null;
    }

    @Override
    public ResultVO response(String serviceKey, URL url) {
        return null;
    }
}
