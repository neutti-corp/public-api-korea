package com.neutti.npa.vo.data_go;

import lombok.Data;

@Data
public class ErrHeaderVO {
    private String errMsg;
    private String returnAuthMsg;
    private String returnReasonCode;
}
