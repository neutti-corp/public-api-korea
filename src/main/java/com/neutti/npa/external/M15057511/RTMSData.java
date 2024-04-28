package com.neutti.npa.external.M15057511;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RTMSData {

    @JsonProperty("거래금액")
    private String transactionAmount;

    @JsonProperty("거래유형")
    private String transactionType;

    @JsonProperty("건축년도")
    private int constructionYear;

    @JsonProperty("년")
    private int year;

    @JsonProperty("도로명")
    private String roadName;

    @JsonProperty("도로명건물본번호코드")
    private String roadNameBuildingMainNumberCode;

    @JsonProperty("도로명건물부번호코드")
    private String roadNameBuildingSubNumberCode;

    @JsonProperty("도로명시군구코드")
    private String roadNameDistrictCode;

    @JsonProperty("도로명일련번호코드")
    private String roadNameSequenceCode;

    @JsonProperty("도로명지상지하코드")
    private String roadNameGroundUnderCode;

    @JsonProperty("도로명코드")
    private String roadNameCode;

    @JsonProperty("동")
    private String dong;

    @JsonProperty("등기일자")
    private String registrationDate;

    @JsonProperty("매도자")
    private String seller;

    @JsonProperty("매수자")
    private String buyer;

    @JsonProperty("법정동")
    private String legalDong;

    @JsonProperty("법정동본번코드")
    private String legalDongMainNumberCode;

    @JsonProperty("법정동부번코드")
    private String legalDongSubNumberCode;

    @JsonProperty("법정동시군구코드")
    private String legalDongCityCountyDistrictCode;

    @JsonProperty("법정동읍면동코드")
    private String legalDongTownCode;

    @JsonProperty("법정동지번코드")
    private String legalDongLandLotNumberCode;

    @JsonProperty("아파트")
    private String apartment;

    @JsonProperty("월")
    private int month;

    @JsonProperty("일")
    private int day;

    @JsonProperty("일련번호")
    private String serialNumber;

    @JsonProperty("전용면적")
    private double exclusiveArea;

    @JsonProperty("중개사소재지")
    private String brokerLocation;

    @JsonProperty("지번")
    private String jibun;

    @JsonProperty("지역코드")
    private String regionCode;

    @JsonProperty("층")
    private int floor;

    @JsonProperty("해제사유발생일")
    private String cancellationReasonOccurrenceDate;

    @JsonProperty("해제여부")
    private String cancellationStatus;
}
