package com.neutti.npa.external.B552657;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AedInfo {
    /**
     * 제조번호
     */
    private String rnum;
    /**
     * 경도
     */
    private String wgs84lon;
    /**
     * 위도
     */
    private String wgs84lat;
    /**
     * 설치기관명
     */
    private String org;
    /**
     * 제조사
     */
    private String mfg;
    /**
     * 설치위치
     */
    private String buildplace;
    /**
     * 설치기관전화번호
     */
    private String clerktel;
    /**
     * 설치기관주소
     */
    private String buildaddress;
    /**
     * 관리책임자명
     */
    private String manager;
    /**
     * 관리자 연락처
     */
    private String managertel;
    /**
     * AED 모델명
     */
    private String model;
    /**
     * 우편번호(앞자리)
     */
    private String zipcode1;
    /**
     * 우편번호(뒤자리)
     */
    private String zipcode2;
    /**
     * 건수
     */
    private String cnt;
    /**
     * 거리
     */
    private String distance;
    /**
     * 월요일가용시작시간
     */
    private String monSttTme;
    /**
     * 월요일가용종료시간
     */
    private String monEndTme;
    /**
     * 화요일가용시작시간
     */
    private String tueSttTme;
    /**
     * 화요일가용종료시간
     */
    private String tueEndTme;
    /**
     * 수요일가용시작시간
     */
    private String wedSttTme;
    /**
     * 수요일가용종료시간
     */
    private String wedEndTme;
    /**
     * 목요일가용시작시간
     */
    private String thuSttTme;
    /**
     * 목요일가용종료시간
     */
    private String thuEndTme;
    /**
     * 금요일가용시작시간
     */
    private String friSttTme;
    /**
     * 금요일가용종료시간
     */
    private String friEndTme;
    /**
     * 토요일가용시작시간
     */
    private String satSttTme;
    /**
     * 토요일가용종료시간
     */
    private String satEndTme;
    /**
     * 일요일가용시작시간
     */
    private String sunSttTme;
    /**
     * 일요일가용종료시간
     */
    private String sunEndTme;
    /**
     * 휴일가용시작시간
     */
    private String holSttTme;
    /**
     * 휴일가용종료시간
     */
    private String holEndTme;
    /**
     * 일요일첫째주가능여부
     */
    private String sunFrtYon;
    /**
     * 일요일둘째주가능여부
     */
    private String sunScdYon;
    /**
     * 일요일셋째주가능여부
     */
    private String sunThiYon;
    /**
     * 일요일넷째주가능여부
     */
    private String sunFurYon;
    /**
     * 일요일다섯째주가능여부
     */
    private String sunFifYon;
}
