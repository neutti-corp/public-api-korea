package com.neutti.npa.service.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.*;
import com.neutti.npa.helper.CallHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;

import java.net.URL;

/**
 * 영화 진흥 위원회
 * @param <T>
 */
@Slf4j
@Data
public class KobisApiService<T>  implements NService<T> {

    protected KobisApiService(){}
    public static <E> KobisApiService<E> getInstance() {
        return new KobisApiService<>();
    }
    private final NHostType hostType = NHostType.KOBIS;
    private String path;
    private String serviceKey;
    private TypeReference<T> itemTypeRef;
    private NParamVO param;
    @Override
    public void addRequestProperty(String key, String value) {

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
    public void setRequestMethod(String requestMethod) throws NpaException {

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
        param.setServiceKey(serviceKey);
        NResultVO<T> r = call.loadItem(hostType, path,null,  param, itemTypeRef);
        return r;
    }
}
