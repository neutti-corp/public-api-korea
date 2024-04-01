package com.neutti.publicdata;

import com.neutti.publicdata.service.APIPullService;
import com.neutti.publicdata.vo.ResultVO;

import java.net.URL;

/**
 * <a href="https://data.gg.go.kr/">경기데이터드림</a>
 */
public class DataGGApiService implements APIPullService {
    @Override
    public ResultVO response(String serviceKey, String apiNum, String reqPath, String servicePath) {
        return null;
    }

    @Override
    public ResultVO response(String serviceKey, URL url) {
        return null;
    }
}
