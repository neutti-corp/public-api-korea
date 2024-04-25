package com.neutti.npa.service;


import com.neutti.npa.vo.data_go.ResponseVO;

import java.net.URL;

public interface APIPullService {
    /**
     *
     * @param serviceKey
     * @param reqPath
     * @param servicePath
     * @return
     */
    public ResponseVO response(String serviceKey, String apiNum, String reqPath, String servicePath);

    /**
     *
     * @param serviceKey
     * @param url : full request url
     * @return
     */
    public ResponseVO response(String serviceKey, URL url);
}
