package com.neutti.npa.vo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class HospitalVO {
    @SerializedName("instit_nm") private String institNm;
    @SerializedName("instit_kind") private String institKind;
    @SerializedName("medical_instit_kind") private String medicalInstitKind;
    @SerializedName("zip_code") private String zipCode;
    @SerializedName("street_nm_addr") private String streetNmAddr;
    @SerializedName("tel") private String tel;
    @SerializedName("organ_loc") private String organLoc;
    @SerializedName("Monday") private String monday;
    @SerializedName("Tuesday") private String tuesday;
    @SerializedName("Wednesday") private String wednesday;
    @SerializedName("Thursday") private String thursday;
    @SerializedName("Friday") private String friday;
    @SerializedName("Saturday") private String saturday;
    @SerializedName("Sunday") private String sunday;
    @SerializedName("holiday") private String holiday;
    @SerializedName("sunday_oper_week") private String sundayOperWeek;
    @SerializedName("exam_part") private String examPart;
    @SerializedName("regist_dt") private String registDt;
    @SerializedName("update_dt") private String updateDt;
    @SerializedName("lng") private Double lng;
    @SerializedName("lat") private Double lat;

}
