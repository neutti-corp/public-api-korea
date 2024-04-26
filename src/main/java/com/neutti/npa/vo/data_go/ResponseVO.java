package com.neutti.npa.vo.data_go;

import lombok.Data;

import java.net.URL;
import java.util.List;

@Data
public class ResponseVO<T> {
    private URL requestUrl;
    private HeaderVO header;
    private ErrHeaderVO cmmMsgHeader;
    private BodyVO<T> body;
    private List<T> items;
}
