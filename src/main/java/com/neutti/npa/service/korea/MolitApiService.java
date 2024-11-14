package com.neutti.npa.service.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.NpaException;
import com.neutti.npa.helper.CallHelper;
import com.neutti.npa.NService;
import com.neutti.npa.NHostType;
import com.neutti.npa.NParamVO;
import com.neutti.npa.vo.WmsVO;
import com.neutti.npa.vo.data_go.DataResponseVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

/**
 * <a href="http://openapi.molit.go.kr/">국토교통부</a>
 */
@Slf4j
@Data
public class MolitApiService<T> implements NService<T> {

    protected MolitApiService(){}
    public static <E> MolitApiService<E> getInstance() {
        return new MolitApiService<>();
    }
    private final NHostType hostType = NHostType.MOLIT;
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
    public BufferedImage getWmsImage(WmsVO param) {
        return null;
    }

    @Override
    public DataResponseVO<T> response(NParamVO param) {
        CallHelper call = new CallHelper();
        param.setServiceKey(serviceKey);
        DataResponseVO<T> r = call.load(hostType, path, "GET", param, itemTypeRef);
        return r;
    }
}
