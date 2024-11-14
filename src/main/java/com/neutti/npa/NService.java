package com.neutti.npa;


import com.fasterxml.jackson.core.type.TypeReference;
import com.neutti.npa.vo.WmsVO;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;

public interface NService<T> {
    void setRequestMethod(String requestMethod) throws NpaException;
    void addRequestProperty(String key, String value);
    void setDataPath(String dataPath) throws NpaException;
    void setDataPath(URL dataUrl);
    void setCertKey(String certKey);
    void setDataTypeRef(TypeReference<T> dataTypeRef);
    <E extends NResultVO<T>> E response(NParamVO param);
    BufferedImage getWmsImage(WmsVO param);
}
