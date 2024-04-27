package com.neutti.npa;


import java.net.URL;

public interface NService<T> {
    /**
     *
     * @param serviceKey
     * @param reqPath
     * @param servicePath
     * @return
     */
    public NResultVO<T> response(String serviceKey, String apiNum, String reqPath, String servicePath);

    /**
     *
     * @param serviceKey
     * @param url : full request url
     * @return
     */
    public NResultVO<T> response(String serviceKey, URL url);
    public NResultVO<T> response(NParamVO param);
}
