package com.ssafy.client.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class KakaoSearchResponse {
    private Meta meta;
    private List<Document> documents;

    @Data
    public static class Meta {
        @JsonProperty("pageable_count")
        private int pageableCount;

        @JsonProperty("total_count")
        private int totalCount;

        @JsonProperty("is_end")
        private boolean isEnd;
    }

    @Data
    public static class Document {
        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("category_name")
        private String categoryName;

        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("road_address_name")
        private String roadAddressName;

        @JsonProperty("id")
        private String kakaoId;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("category_group_code")
        private String categoryGroupCode;

        @JsonProperty("category_group_name")
        private String categoryGroupName;

        @JsonProperty("x")
        private String longitude;

        @JsonProperty("y")
        private String latitude;
    }
}