package com.neutti.npa.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.service.APIPullService;
import com.neutti.npa.vo.HostType;
import com.neutti.npa.vo.ParamVO;
import com.neutti.npa.vo.data_go.ResponseVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.net.URL;

/**
 * <a href="http://openapi.molit.go.kr/">국토교통부</a>
 */
@Slf4j
@Data
public class MolitApiService<T> implements APIPullService<T> {

    protected MolitApiService(){}
    public static <E> MolitApiService<E> getInstance() {
        return new MolitApiService<>();
    }
    private final HostType hostType = HostType.MOLIT;
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
