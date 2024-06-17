package com.neutti.npa.service.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.*;
import com.neutti.npa.helper.CallHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;

/**
 * 여성가족부_성범죄자
 * @param <T>
 */
@Slf4j
@Data
public class SexOffenderApiService<T> implements NService<T> {

    protected SexOffenderApiService(){}
    public static <E> SexOffenderApiService<E> getInstance() {
        return new SexOffenderApiService<>();
    }
    private final NHostType hostType = NHostType.SEX_OFFENDER;
    private String path;
    private String serviceKey;
    private TypeReference<T> itemTypeRef;
    private NParamVO param;

    @Override
    public void setRequestMethod(String requestMethod) throws NpaException {

    }

    @Override
    public void setDataPath(String path) {
        setPath(path);
    }
    @Override
    public void setDataPath(URL dataUrl) {
        setPath(dataUrl.toString());
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
        if(param == null){
            param = new NParamVO();
        }
        CallHelper call = new CallHelper();
       // param.setServiceKey(serviceKey);
        NResultVO<T> r = call.loadItem(hostType, path, null, param, itemTypeRef);
        return r;
    }
}
