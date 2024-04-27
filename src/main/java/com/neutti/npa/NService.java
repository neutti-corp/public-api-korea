package com.neutti.npa;


import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Data;

import java.net.URL;

public interface NService<T> {
    void setDataPath(String dataPath);
    void setCertKey(String certKey);
    void setDataTypeRef(TypeReference<T> dataTypeRef);
    <E extends NResultVO<T>> E response(NParamVO param);
}
