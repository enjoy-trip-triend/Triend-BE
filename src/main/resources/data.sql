-- Delete original data
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE schedules;
TRUNCATE places_images;
TRUNCATE planners_members;
TRUNCATE planners_likes;
TRUNCATE planners_locations;
TRUNCATE my_places;
TRUNCATE members_characters;

TRUNCATE planners;
TRUNCATE places;
TRUNCATE characters;
TRUNCATE members;
TRUNCATE places_categories;

-- 최초 1회 서버 구동 후
-- 1) sidos, guguns의 TRUNCATE문은 주석처리
-- 2) 아래 Dummy data는 주석해제 후 서버 재구동 부탁드립니다.
TRUNCATE sidos;
TRUNCATE guguns;

TRUNCATE categories;

SET FOREIGN_KEY_CHECKS = 1;

-- Add dummy data
-- SIDOS/GUGUNS will be fetched by tour API

---- MEMBERS
--INSERT INTO members (email, password, name, role, birth, mbti, refresh_token)
--VALUES
--    ('test@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'test', 'MEMBER', '1995-06-15', 'INFP', NULL),
--    ('admin@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'admin', 'ADMIN', '1990-01-01', 'ENTJ', NULL),
--    ('alice@example.com', 'hashed_pw1', 'Alice', 'MEMBER', '1995-06-01', 'INTJ', NULL),
--    ('bob@example.com', 'hashed_pw2', 'Bob', 'ADMIN', NULL, NULL, NULL);
--
---- CHARACTERS
--INSERT INTO characters (name)
--VALUES ('탐험가'),
--       ('계획러');
--
---- MEMBERS_CHARACTERS
--INSERT INTO members_characters (member_id, character_id)
--VALUES (1, 1),
--       (1, 2);
--
---- CATEGORIES
--INSERT INTO categories (name)
--VALUES ('관광'),
--       ('맛집');
--
---- PLACES_CATEGORIES
--INSERT INTO places_categories (category_group_code, category_group_name, category_name)
--VALUES ('FD6', '음식점', '한식'),
--       ('CE7', '카페', '커피전문점');
--
---- PLACES
--INSERT INTO places (kakaoId, address, location_name, lat, lon, count, category_id)
--VALUES (1001, '서울 강남구 테헤란로 1', '강남 맛집', 37.4979, 127.0276, 0, 1),
--       (1002, '부산 해운대구 해운대로 1', '해운대 카페', 35.1587, 129.1604, 0, 2);
--
---- MY_PLACES
--INSERT INTO my_places (member_id, kakaoId)
--VALUES (1, 1001),
--       (1, 1002);
--
---- PLANNERS
--INSERT INTO planners (start_day, end_day, member_id, name, location, comment, exposure,
--                      password, likes_count)
--VALUES ('2025-07-01 00:00:00', '2025-07-03 00:00:00', 1, '여름 서울 여행', '서울', '서울 맛집 투어',
--        'PUBLIC', NULL, 1),
--       ('2025-08-01 00:00:00', '2025-08-02 00:00:00', 2, '부산 여행', '부산', '해운대 힐링', 'PRIVATE',
--        NULL, 0);
--
---- PLANNERS_LOCATIONS
--INSERT INTO planners_locations (planner_id, sido_code, gugun_code)
---- 서울 강남구 / 부산 해운대구
--VALUES (1, 1, 1),
--       (2, 6, 16);
--
---- PLANNERS_LIKES
--INSERT INTO planners_likes (planner_id, member_id)
--VALUES (1, 2);
--
---- PLANNERS_MEMBERS
--INSERT INTO planners_members (planner_id, member_id)
--VALUES (1, 1),
--       (1, 2),
--       (2, 2);
--
---- PLACES_IMAGES
--INSERT INTO places_images (kakaoId, image_key)
--VALUES (1001, 'img_1001.jpg'),
--       (1002, 'img_1002.jpg');
--
---- SCHEDULES
--INSERT INTO schedules (id, planner_id, date, start_time, content, place_url, idx, place_id)
--VALUES (1, 1, '2025-07-01 10:00:00', '2025-07-01 10:00:00', '점심 식사', 'https://place1.com', 1,
--        1001),
--       (2, 1, '2025-07-01 15:00:00', '2025-07-01 15:00:00', '카페 타임', 'https://place2.com', 2,
--        1002);