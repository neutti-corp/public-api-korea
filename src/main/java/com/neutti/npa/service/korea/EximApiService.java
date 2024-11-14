package com.neutti.npa.service.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.*;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.vo.WmsVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.InputStream;
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
    public void setCertKey(String key) {
setServiceKey(key);
    }

    @Override
    public void setDataTypeRef(TypeReference<T> dataTypeRef) {
        setItemTypeRef(dataTypeRef);
    }

    @Override
    public BufferedImage getWmsImage(WmsVO param) {
        return null;
    }

    @Override
    public void setRequestMethod(String requestMethod) throws NpaException {

    }
    @Override
    public NResultVO<T> response(NParamVO param) {
        if(param == null){
            param = new NParamVO();
        }
        CallHelper call = new CallHelper();
        param.setServiceKey(serviceKey);
        NResultVO<T> r = call.loadItem(hostType, path, null, param, itemTypeRef);
        return r;
    }
}
