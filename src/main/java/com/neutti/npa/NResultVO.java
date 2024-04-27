package com.neutti.npa;

import lombok.Data;

import java.net.URL;
import java.util.List;

@Data
public class NResultVO<T> {
    private URL requestUrl;
    private String responseCode;
    private String resultCode;
    private String resultMessage;
    private Boolean error;
    private List<T> data;
}
