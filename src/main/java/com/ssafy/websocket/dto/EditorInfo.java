package com.ssafy.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class EditorInfo {
    private static final String[] nameList1 = {
            "잠자는", "고민하는", "느릿느릿", "행복한", "미소짓는", "얼렁뚱땅", "재빠른", "엄격한", "뚝딱거리는", "열일하는", "누워있는", "앉아있는",
            "엎드린", "뚜벅뚜벅", "엉금엉금", "폴짝폴짝", "풀쩍풀쩍", "데굴데굴", "똑똑한", "날렵한", "미끌미끌", "빈둥빈둥", "허겁지겁"
    };
    private static final String[] nameList2 = {
            "코알라", "사슴", "나무늘보", "아르마딜로", "피카츄", "캥거루", "곰", "반달가슴곰", "사막여우", "방울뱀", "코브라", "토끼",
            "북극곰", "호랑이", "사자", "기린", "암모나이트", "치타", "하이에나", "멧돼지", "꿀꿀이", "병아리", "닭", "독수리", "대머리 독수리",
            "알바트로스"
    };

    private String name;
    private String ipAddr;
    private Long plannerId;
    private String sessionId;

    public void setName(){
        int randNum1 = (int)(Math.random() * nameList1.length);
        int randNum2 = (int)(Math.random() * nameList2.length);
        this.name = nameList1[randNum1] + " " + nameList2[randNum2];
    }
}
