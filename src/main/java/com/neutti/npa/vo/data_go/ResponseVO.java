package com.neutti.npa.vo.data_go;

import lombok.Data;

import java.net.URL;

@Data
public class ResponseVO<T> {
    private URL requrestUrl;
    private HeaderVO header;
    private ErrHeaderVO cmmMsgHeader;
    private BodyVO<T> body;
}
