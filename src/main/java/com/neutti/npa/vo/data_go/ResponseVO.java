package com.neutti.npa.vo.data_go;

import lombok.Data;

@Data
public class ResponseVO<T> {
    private HeaderVO header;
    private ErrHeaderVO cmmMsgHeader;
    private BodyVO<T> body;
}
