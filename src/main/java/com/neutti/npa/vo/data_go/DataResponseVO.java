package com.neutti.npa.vo.data_go;

import com.neutti.npa.NResultVO;
import lombok.Data;

import java.net.URL;

@Data
public class DataResponseVO<T> extends NResultVO<T> {
    private URL requestUrl;
    private HeaderVO header;
    private ErrHeaderVO cmmMsgHeader;
    private BodyVO<T> body;
}
