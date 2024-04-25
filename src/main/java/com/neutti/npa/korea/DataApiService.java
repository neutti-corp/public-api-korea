package com.neutti.npa.korea;

import com.neutti.npa.external.B552657.AedInfo;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.service.APIPullService;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import lombok.Data;

import java.net.URL;

/**
 * <a href="https://www.data.go.kr/">공공데이터포털</a>
 */
@Data
public class DataApiService<T> implements APIPullService<T> {
    public static <T> DataApiService<T> getInstance() {
        return new DataApiService<>();
    }
    private final HostType hostType = HostType.DATA_GO;
    private String path;
    private String serviceKey;
    private ParamVO param;
    @Override
    public ResponseVO<T> response(String serviceKey, String apiNum, String reqPath, String servicePath) {
        return null;
    }

    @Override
    public ResponseVO<T> response(String serviceKey, URL url) {
        return null;
    }

    @Override
    public ResponseVO<T> response(ParamVO param) {
        CallHelper call = new CallHelper();
        param.setServiceKey(serviceKey);
        ResponseVO<T> r = call.load(hostType, path, param);
        return r;
    }
}
