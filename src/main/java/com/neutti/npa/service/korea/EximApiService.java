package com.neutti.npa.service.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.NService;
import com.neutti.npa.NHostType;
import com.neutti.npa.NParamVO;
import com.neutti.npa.NResultVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

@Slf4j
@Data
public class EximApiService<T> implements NService<T> {

    protected EximApiService(){}
    public static <E> EximApiService<E> getInstance() {
        return new EximApiService<>();
    }
    private final NHostType hostType = NHostType.EXIM;
    private String path;
    private String serviceKey;
    private TypeReference<T> itemTypeRef;
    private NParamVO param;

    @Override
    public void setDataPath(String path) {
setPath(path);
    }

    @Override
    public void setCertKey(String key) {
setServiceKey(key);
    }

    @Override
    public void setDataTypeRef(TypeReference<T> dataTypeRef) {
        setItemTypeRef(dataTypeRef);
    }


    @Override
    public NResultVO<T> response(NParamVO param) {
        CallHelper call = new CallHelper();
        param.setServiceKey(serviceKey);
        NResultVO<T> r = call.loadItem(hostType, path, param, itemTypeRef);
        return r;
    }
}
