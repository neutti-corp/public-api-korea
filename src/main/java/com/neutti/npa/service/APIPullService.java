package com.neutti.npa.service;


import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;

import java.net.URL;

public interface APIPullService<T> {
    /**
     *
     * @param serviceKey
     * @param reqPath
     * @param servicePath
     * @return
     */
    public ResponseVO<T> response(String serviceKey, String apiNum, String reqPath, String servicePath);

    /**
     *
     * @param serviceKey
     * @param url : full request url
     * @return
     */
    public ResponseVO<T> response(String serviceKey, URL url);
    public ResponseVO<T> response(ParamVO param);
}
