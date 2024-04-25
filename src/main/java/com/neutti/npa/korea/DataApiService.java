package com.neutti.npa.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.external.B552657.AedInfo;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.service.APIPullService;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

/**
 * <a href="https://www.data.go.kr/">공공데이터포털</a>
 */
@Slf4j
@Data
public class DataApiService<T> implements APIPullService<T> {
    protected DataApiService(){}
    public static <E> DataApiService<E> getInstance() {
        return new DataApiService<>();
    }
    private final HostType hostType = HostType.DATA_GO;
    private String path;
    private String serviceKey;
    private TypeReference<T> itemTypeRef;
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
        if(itemTypeRef == null) {
            log.warn("ItemTypeRef 이 선언이 안 되있을 경우 항목(Item) 객체는 Map 형태로 변환됩니다.");
        }
        CallHelper call = new CallHelper();
        param.setServiceKey(serviceKey);
        ResponseVO<T> r = call.load(hostType, path, param, itemTypeRef);
        return r;
    }
}
