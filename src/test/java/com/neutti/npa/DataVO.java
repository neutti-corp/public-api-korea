package com.neutti.npa;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class DataVO {
    private Double flowValocity;
    private Double level;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date meaDt;
    private String modemPhoneNum;
    private Integer odevSq;
    private Integer orawSq;
    private String rainfall;
    private String siteName;
    private Date transDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updDt;
}
