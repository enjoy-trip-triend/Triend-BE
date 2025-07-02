-- Add dummy data
-- SIDOS/GUGUNS will be fetched by tour API
-- 서버 최초 구동 시 아래 내용 모두 주석 처리 후 진행해주세요.

-- MEMBERS
INSERT INTO members (email, password, name, role, birth, mbti, refresh_token)
VALUES ('test@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'test',
        'MEMBER', '1995-06-15', 'INFP', NULL),
       ('hong@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'hong',
        'MEMBER', '1995-06-20', 'INFP', NULL),
       ('admin@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'admin',
        'ADMIN', '1990-01-01', 'ENTJ', NULL),
       ('alice@example.com', 'hashed_pw1', 'Alice', 'MEMBER', '1995-06-01', 'INTJ', NULL),
       ('bob@example.com', 'hashed_pw2', 'Bob', 'ADMIN', NULL, NULL, NULL);

-- CHARACTERS
INSERT INTO characters (name)
VALUES ('탐험가'),
       ('계획러'),
       ('힐링러'),
       ('미식가'),
       ('포토그래퍼'),
       ('문화애호가'),
       ('자연탐방가'),
       ('액티비티러');

-- MEMBERS_CHARACTERS
INSERT INTO members_characters (member_id, character_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2);

-- CATEGORIES
INSERT INTO categories (name)
VALUES ('음식점'),
       ('관광지');

-- PLACES_CATEGORIES_GROUPS
INSERT INTO `places_categories_groups` (`group_code`, `group_name`)
VALUES ('FD6', '음식점'),  -- 숙박업소
       ('CE7', '카페'),   -- 카페
       ('PM9', '약국'),   -- 약국
       ('CS2', '편의점'),  -- 편의점
       ('HP8', '병원'),   -- 병원
       ('CT1', '문화시설'), -- 공연·전시·영화관 등
       ('AT4', '관광명소');
-- 관광/명소


-- PLACES_CATEGORIES
INSERT INTO `places_categories` (`category_name`, `category_group_id`)
VALUES ('음식점 > 한식', 1),
       ('카페 > 커피숍', 2);


-- PLACES
INSERT INTO places (kakao_id, address_name, road_address_name, place_name, latitude, longitude,
                    phone, save_count, category_id)
VALUES (627627078, '경북 경주시 태종로 746', '경북 경주시 태종로 746', '황리단길', 35.8393348165945, 129.209645417434,
        '054-111-0001', 0, 2),
       (17809057, '경북 경주시 계림로 9', '경북 경주시 계림로 9', '천마총', 35.8384565998858, 129.210559431045,
        '054-111-0002', 0, 2),
       (8288444, '경북 경주시 황남동 31-1', '경북 경주시 황남동 31-1', '대릉원', 35.83819105876201, 129.21333399231364,
        '054-111-0003', 0, 2),
       (8089382, '경북 경주시 인왕동 839-1', '경북 경주시 인왕동 839-1', '첨성대', 35.83471481233599,
        129.21900018259583, '054-111-0004', 0, 2),
       (9666845, '경북 경주시 일정로 186', '경북 경주시 일정로 186', '국립경주박물관', 35.829233895353426,
        129.22795026492514, '054-111-0005', 0, 2),
       (12760573, '경북 경주시 불국로 385', '경북 경주시 불국로 385', '불국사', 35.78991466172384, 129.3318426090494,
        '054-111-0006', 0, 2),
       (19464673, '경북 경주시 보문로 74-14', '경북 경주시 보문로 74-14', '경주동궁원', 35.849507092525485,
        129.26166060389696, '054-111-0007', 0, 2),
       (10170779, '경북 경주시 신평동 719-203', '경북 경주시 신평동 719-203', '경주보문관광단지', 35.842992007168284,
        129.28430859591472, '054-111-0008', 0, 2),
       (349177105, '경북 경주시 통일로 366-4', '경북 경주시 통일로 366-4', '경북천년숲정원', 35.81189405943047,
        129.2421504256791, '054-111-0009', 0, 2),
       (18619553, '서울특별시 종로구 사직로 161', '서울특별시 종로구 사직로 지하 1', '경복궁', 37.5796170, 126.9770410,
        '02-3700-3900', 5, 1),
       (18965432, '부산광역시 해운대구 우동 1420', '부산광역시 해운대구 해운대로 264', '해운대해수욕장', 35.1631824, 129.1635765,
        '051-749-7611', 8, 2);


-- MY_PLACES
INSERT INTO my_places (member_id, place_id)
VALUES (1, 1),
       (1, 2);

-- PLANNERS
INSERT INTO planners (start_day, end_day, member_id, name, comment, exposure,
                      likes_count)
VALUES ('2025-12-24', '2025-12-25', 1, '경주 1박 2일 유적지 여행', '크리스마스 경주 여행', 'PUBLIC', 0),
       ('2025-07-01', '2025-07-03', 2, '여름 서울 여행', '서울 맛집 투어', 'PUBLIC', 0),
       ('2025-08-01', '2025-08-02', 3, '부산 여행', '부산', 'PRIVATE', 0);

-- PLANNERS_LOCATIONS
INSERT INTO planners_locations (planner_id, sido_code, gugun_code)
-- 서울 강남구 / 부산 해운대구
VALUES (1, 1, 1),
       (2, 6, 16);

-- PLANNERS_LIKES
INSERT INTO planners_likes (planner_id, member_id)
VALUES (1, 2);

-- PLANNERS_MEMBERS
INSERT INTO planners_members (planner_id, member_id)
VALUES (1, 1),
       (1, 2),
       (2, 2);

-- PLACES_IMAGES
INSERT INTO places_images (place_id, image_key)
VALUES (1, 'img_1001.jpg'),
       (2, 'img_1002.jpg');

-- SCHEDULES
INSERT INTO schedules (planner_id, date, start_time, content, idx, place_id)
VALUES (1, '2025-12-24', '09:30:00', '황리단길', 1, 1),
       (1, '2025-12-24', '11:00:00', '천마총', 2, 2),
       (1, '2025-12-24', '14:00:00', '대릉원', 3, 3),
       (1, '2025-12-24', '16:00:00', '첨성대', 4, 4),
       (1, '2025-12-24', '17:30:00', '국립경주박물관', 5, 5),
       (1, '2025-12-25', '09:30:00', '불국사', 6, 6),
       (1, '2025-12-25', '13:00:00', '경주동궁원', 7, 7),
       (1, '2025-12-25', '15:00:00', '경주보문관광단지', 8, 8),
       (1, '2025-12-25', '17:00:00', '경북천년숲정원', 9, 9),
       (2, '2025-07-01', '10:00:00', '점심 식사', 1, 1),
       (3, '2025-07-01', '15:00:00', '카페 타임', 2, 2);

