-- Add dummy data
-- SIDOS/GUGUNS will be fetched by tour API
-- 서버 최초 구동 시 아래 내용 모두 주석 처리 후 진행해주세요.

-- MEMBERS
INSERT INTO members (email, password, name, role, birth, mbti, refresh_token)
VALUES
    ('test@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'test', 'MEMBER', '1995-06-15', 'INFP', NULL),
    ('hong@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'hong', 'MEMBER', '1995-06-20', 'INFP', NULL),
    ('admin@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'admin', 'ADMIN', '1990-01-01', 'ENTJ', NULL),
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
VALUES
  ('FD6', '음식점'),            -- 숙박업소
  ('CE7', '카페'),            -- 카페
  ('PM9', '약국'),            -- 약국
  ('CS2', '편의점'),          -- 편의점
  ('HP8', '병원'),            -- 병원
  ('CT1', '문화시설'),        -- 공연·전시·영화관 등
  ('AT4', '관광명소');        -- 관광/명소


-- PLACES_CATEGORIES
INSERT INTO `places_categories` (`category_name`, `category_group_id`)
VALUES ('음식점 > 한식', 1),
       ('카페 > 커피숍', 2);


-- PLACES
INSERT INTO places (kakao_id, address_name, road_address_name, place_name, latitude, longitude, phone, save_count, category_id)
VALUES (12678345, '서울특별시 종로구 사직로 161', '서울특별시 종로구 사직로 지하 1', '경복궁', 37.5796170, 126.9770410, '02-3700-3900', 5, 1),
       (18965432, '부산광역시 해운대구 우동 1420', '부산광역시 해운대구 해운대로 264', '해운대해수욕장', 35.1631824, 129.1635765, '051-749-7611', 8, 2);



-- MY_PLACES
INSERT INTO my_places (member_id, place_id)
VALUES (1, 1),
       (1, 2);

-- PLANNERS
INSERT INTO planners (start_day, end_day, member_id, name, comment, exposure,
                      likes_count)
VALUES ('2025-07-01 00:00:00', '2025-07-03 00:00:00', 1, '여름 서울 여행', '서울 맛집 투어',
        'PUBLIC', 1),
       ('2025-08-01 00:00:00', '2025-08-02 00:00:00', 2, '부산 여행', '부산', 'PRIVATE',
       0);

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
INSERT INTO schedules (id, planner_id, date, start_time, content, place_url, idx, place_id)
VALUES (1, 1, '2025-07-01 10:00:00', '2025-07-01 10:00:00', '점심 식사', 'https://place1.com', 1, 1),
       (2, 1, '2025-07-01 15:00:00', '2025-07-01 15:00:00', '카페 타임', 'https://place2.com', 2, 2);