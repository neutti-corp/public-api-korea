package com.neutti.npa.service.korea;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.NService;
import com.neutti.npa.NHostType;
import com.neutti.npa.NParamVO;
import com.neutti.npa.NpaException;
import com.neutti.npa.vo.WmsVO;
import com.neutti.npa.vo.data_go.DataResponseVO;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

/**
 * <a href="https://data.gg.go.kr/">경기데이터드림</a>
 */
@Data
public class DataGGApiService<T> implements NService {
    protected DataGGApiService(){}
    public static <E> DataGGApiService<E> getInstance() {
        return new DataGGApiService<>();
    }
    private final NHostType hostType = NHostType.DATA_GG;
    private String path;
    private String serviceKey;
    private TypeReference<T> itemTypeRef;

    @Override
    public void setDataPath(String path) {

    }
    @Override
    public void setRequestMethod(String requestMethod) throws NpaException {

    }
    @Override
    public void setDataPath(URL dataUrl) {
        setPath(dataUrl.toString());
    }
    @Override
    public void setCertKey(String key) {

    }

    @Override
    public void setDataTypeRef(TypeReference dataTypeRef) {

    }

    @Override
    public BufferedImage getWmsImage(WmsVO param) {
        return null;
    }

    @Override
    public DataResponseVO response(NParamVO param) {
        return null;
    }
}
