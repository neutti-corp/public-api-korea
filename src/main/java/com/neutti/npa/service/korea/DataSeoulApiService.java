package com.neutti.npa.service.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.NService;
import com.neutti.npa.NParamVO;
import com.neutti.npa.NpaException;
import com.neutti.npa.vo.data_go.DataResponseVO;

import java.net.URL;

/**
 * <a href="https://data.seoul.go.kr/">서울열린데이터광장</a>
 */
public class DataSeoulApiService implements NService {
    @Override
    public void setDataPath(String path) {

    }
    @Override
    public void setDataPath(URL dataUrl) {

    }
    @Override
    public void setCertKey(String key) {

    }
    @Override
    public void addRequestProperty(String key, String value) {

    }
    @Override
    public void setRequestMethod(String requestMethod) throws NpaException {

    }
    @Override
    public void setDataTypeRef(TypeReference dataTypeRef) {

    }

    @Override
    public DataResponseVO response(NParamVO param) {
        return null;
    }
}
