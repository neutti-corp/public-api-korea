package com.neutti.npa.vo;

import com.neutti.npa.vo.data_go.BodyVO;
import com.neutti.npa.vo.data_go.ErrHeaderVO;
import com.neutti.npa.vo.data_go.HeaderVO;
import lombok.Data;

import java.net.URL;
import java.util.List;

@Data
public class ResultVO<T> {
    private URL requestUrl;
    private List<T> items;
}
