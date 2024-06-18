package com.neutti.npa.service.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.*;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.vo.data_go.DataResponseVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;


@Slf4j
@Data
public class PrivateApiService<T> implements NService<T> {

    protected PrivateApiService(){}
    public static <E> PrivateApiService<E> getInstance() {
        return new PrivateApiService<>();
    }
    private String path;
    private String serviceKey;
    private TypeReference<T> itemTypeRef;
    private NParamVO param;
    private String requestMethod;

    @Override
    public void setDataPath(String path) throws NpaException {
        if(path.startsWith("http://") || path.startsWith("https://")){
            setPath(path);
        }else{
            throw new NpaException("path 는 http:// 또는 https:// 로 시작해주세요.");
        }
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
    public void setRequestMethod(String requestMethod) throws NpaException {
        this.requestMethod = requestMethod;
    }
    @Override
    public NResultVO<T> response(NParamVO param) {
        CallHelper call = new CallHelper();
        param.setServiceKey(serviceKey);
        NResultVO<T> r = call.loadItem(null, path, requestMethod, param, itemTypeRef);
        return r;
    }
}
