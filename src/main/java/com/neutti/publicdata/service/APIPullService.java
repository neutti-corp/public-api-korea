package com.neutti.publicdata.service;


import com.neutti.publicdata.vo.ResultVO;

import java.net.URL;

public interface APIPullService {
    /**
     *
     * @param serviceKey
     * @param reqPath
     * @param servicePath
     * @return
     */
    public ResultVO response(String serviceKey, String apiNum, String reqPath, String servicePath);

    /**
     *
     * @param serviceKey
     * @param url : full request url
     * @return
     */
    public ResultVO response(String serviceKey, URL url);
}
