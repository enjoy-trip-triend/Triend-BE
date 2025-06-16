-- SIDOS
INSERT INTO sidos (id, sido_code, sido_name)
VALUES (1, 11, '서울특별시'),
       (2, 26, '부산광역시');

-- GUGUNS
INSERT INTO guguns (id, gugun_code, gugun_name, sido_code)
VALUES (1, 11010, '강남구', 11),
       (2, 26010, '해운대구', 26);

-- MEMBERS
INSERT INTO members (email, password, name, role, birth, mbti, refresh_token)
VALUES
    ('test@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'test', 'MEMBER', '1995-06-15', 'INFP', NULL),
    ('admin@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'admin', 'ADMIN', '1990-01-01', 'ENTJ', NULL),
    ('alice@example.com', 'hashed_pw1', 'Alice', 'MEMBER', '1995-06-01', 'INTJ', NULL),
    ('bob@example.com', 'hashed_pw2', 'Bob', 'ADMIN', NULL, NULL, NULL);

-- CHARACTERS
INSERT INTO characters (name)
VALUES ('탐험가'),
       ('계획러');

-- MEMBERS_CHARACTERS
INSERT INTO members_characters (member_id, character_id)
VALUES (1, 1),
       (1, 2);

-- CATEGORIES
INSERT INTO categories (name)
VALUES ('관광'),
       ('맛집');

-- PLACES_CATEGORIES
INSERT INTO places_categories (category_group_code, category_group_name, category_name)
VALUES ('FD6', '음식점', '한식'),
       ('CE7', '카페', '커피전문점');

-- PLACES
INSERT INTO places (kakaoId, address, location_name, lat, lon, count, category_id)
VALUES (1001, '서울 강남구 테헤란로 1', '강남 맛집', 37.4979, 127.0276, 0, 1),
       (1002, '부산 해운대구 해운대로 1', '해운대 카페', 35.1587, 129.1604, 0, 2);

-- MY_PLACES
INSERT INTO my_places (member_id, kakaoId)
VALUES (1, 1001),
       (1, 1002);

-- PLANNERS
INSERT INTO planners (id, start_day, end_day, member_id, name, location, comment, exposure,
                      password, likes_count)
VALUES ('p1', '2025-07-01 00:00:00', '2025-07-03 00:00:00', 1, '여름 서울 여행', '서울', '서울 맛집 투어',
        'PUBLIC', NULL, 1),
       ('p2', '2025-08-01 00:00:00', '2025-08-02 00:00:00', 2, '부산 여행', '부산', '해운대 힐링', 'PRIVATE',
        NULL, 0);

-- PLANNERS_LOCATIONS
INSERT INTO planners_locations (planner_id, sido_code, gugun_code)
VALUES ('p1', 11, 11010),
       ('p2', 26, 26010);

-- PLANNERS_LIKES
INSERT INTO planners_likes (planner_id, member_id)
VALUES ('p1', 2);

-- PLANNERS_MEMBERS
INSERT INTO planners_members (planner_id, member_id)
VALUES ('p1', 1),
       ('p1', 2),
       ('p2', 2);

-- PLACES_IMAGES
INSERT INTO places_images (kakaoId, image_key)
VALUES (1001, 'img_1001.jpg'),
       (1002, 'img_1002.jpg');

-- SCHEDULES
INSERT INTO schedules (id, planner_id, date, start_time, content, place_url, idx, place_id)
VALUES (1, 'p1', '2025-07-01 10:00:00', '2025-07-01 10:00:00', '점심 식사', 'https://place1.com', 1,
        1001),
       (2, 'p1', '2025-07-01 15:00:00', '2025-07-01 15:00:00', '카페 타임', 'https://place2.com', 2,
        1002);

-- INSERT INTO members (email, password, name, role, birth, mbti) VALUES
-- ('test@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'test', 'MEMBER', '1995-06-15', 'INFP'),
-- ('admin@test.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'admin', 'ADMIN', '1990-01-01', 'ENTJ'),
-- ('carol@example.com', '$2a$10$KqE.zTLUwVQnIlGCfYcSeOlY06SrzZhGy9OcdwJuYhW0kiGmVQu6C', 'Carol', 'MEMBER', '1993-08-20', 'ENFP'),
-- ('dave@example.com', '$2a$10$KqE.zTLUwVQnIlGCfYcSeOlY06SrzZhGy9OcdwJuYhW0kiGmVQu6C', 'Dave', 'MEMBER', '1998-11-03', 'ISTJ'),
-- ('eve@example.com', '$2a$10$KqE.zTLUwVQnIlGCfYcSeOlY06SrzZhGy9OcdwJuYhW0kiGmVQu6C', 'Eve', 'MEMBER', '2000-02-29', 'ISFP'),
-- ('frank@example.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', 'Frank', 'MEMBER', '1994-05-10', 'INFP'),
-- ('hong@example.com', '$2a$10$.k8PcfjUA0KTJf3AK0lbD.FqrxsqJBoB6gn2khl2vjhlM51fkcOxe', '홍길동', 'MEMBER', '1999-01-01', 'INFP');
--
--
-- INSERT INTO categories (name) VALUES
--   ('음식'),
--   ('카페'),
--   ('관광지'),
--   ('쇼핑');
--
--  INSERT INTO my_places (kakao_id, address, lat, lon, location_name, member_id, category_id, description)
-- VALUES
-- ('18862213', '광주 북구 금곡동 산 1-3', 35.133137163805486, 126.99046193302027, '무등산국립공원', 7, 1, '장소에 대한 설명'),
-- ('27455633', '광주 동구 지산동 산 63-1', 35.1489270247463, 126.947442160022, '무등산리프트&모노레일', 7, 2, '장소에 대한 설명'),
-- ('25022571', '광주 동구 금남로1가 12-7', 35.1474913380556, 126.919801652795, '5.18민주광장', 7, 3, '장소에 대한 설명'),
-- ('978069918', '광주 남구 양과동 179-2', 35.0905522004795, 126.882375338142, '광주광역시립수목원', 7, 4, '장소에 대한 설명'),
-- ('978069918', '광주 남구 양과동 179-2', 35.0905522004795, 126.882375338142, '광주광역시립수목원', 6, 4, '장소에 대한 설명'),
-- ('27560699', '서울 송파구 잠실동 40-1', 37.51113059993883, 127.09811980036908, '롯데월드 어드벤처', 7, 1, '장소에 대한 설명'),
-- ('1917455244', '서울 종로구 세종로 1', 37.58657325133722, 126.9748537345249, '청와대 본관', 7, 2, '장소에 대한 설명'),
-- ('1913983226', '서울 용산구 용산동2가 산 1-3', 37.55127433407266, 126.98820799353979, 'YTN서울타워', 7, 3, '장소에 대한 설명'),
-- ('25022571', '광주 동구 금남로1가 12-7', 35.1474913380556, 126.919801652795, '5.18민주광장', 6, 3, '장소에 대한 설명'),
-- ('1917455244', '서울 종로구 세종로 1', 37.58657325133722, 126.9748537345249, '청와대 본관', 7, 2, '장소에 대한 설명');
--
--
--
-- INSERT INTO planners (start_day, end_day, member_id, name, location) VALUES
-- ('2025-12-24', '2025-12-25', 1, '경주 1박 2일 유적지 여행', '경주'),
-- ('2025-06-01', '2025-06-05', 1, '제주도 4박 5일 여행', '제주도');
--
--
-- INSERT INTO plans (planner_id, date, start_time, end_time, place_name, address, content, lat, lon, place_url) VALUES
-- (1, '2025-12-24', '09:30:00', '10:00:00', '황리단길', '경북 경주시 태종로 746', '레트로 감성 가득한 골목', 35.8393348165945, 129.209645417434, 'http://place.map.kakao.com/627627078'),
-- (1, '2025-12-24', '11:00:00', '11:30:00', '천마총', '경북 경주시 계림로 9', '황금의 유물이 잠든 천마총', 35.8384565998858, 129.210559431045, 'http://place.map.kakao.com/17809057'),
-- (1, '2025-12-24', '14:00:00', '15:00:00', '대릉원', '경북 경주시 황남동 31-1', '신라 왕들의 숨결이 깃든 고분군', 35.83819105876201, 129.21333399231364, 'http://place.map.kakao.com/8288444'),
-- (1, '2025-12-24', '16:00:00', '17:30:00', '첨성대', '경북 경주시 인왕동 839-1', '천년의 별자리를 품은 고대의 천문대', 35.83471481233599, 129.21900018259583, 'http://place.map.kakao.com/8089382'),
-- (1, '2025-12-24', '17:30:00', '18:30:00', '국립경주박물관', '경북 경주시 일정로 186', '신라의 유산이 모인 박물관', 35.829233895353426, 129.22795026492514, 'http://place.map.kakao.com/9666845'),
-- (1, '2025-12-25', '09:30:00', '11:00:00', '불국사', '경북 경주시 불국로 385', '천년의 세월을 견뎌온 사찰', 35.78991466172384, 129.3318426090494, 'http://place.map.kakao.com/12760573'),
-- (1, '2025-12-25', '13:00:00', '14:00:00', '경주동궁원', '경북 경주시 보문로 74-14', '이국적인 식물들', 35.849507092525485, 129.26166060389696, 'http://place.map.kakao.com/19464673'),
-- (1, '2025-12-25', '15:00:00', '16:30:00', '경주보문관광단지', '경북 경주시 신평동 719-203', '관광 명소들이 모인 보문단지', 35.842992007168284, 129.28430859591472, 'http://place.map.kakao.com/10170779'),
-- (1, '2025-12-25', '17:00:00', '18:00:00', '경북천년숲정원', '경북 경주시 통일로 366-4', '힐링 산책로.', 35.81189405943047, 129.2421504256791, 'http://place.map.kakao.com/349177105');
--
-- INSERT INTO plan_images (plan_id, image_key) VALUES
-- (1, 'cdee1c8c-2db3-4296-8438-e9abb683b98f.jpeg'),
-- (1, '6565af88-b726-4d94-9630-adb6e1914603.jpeg'),
-- (1, 'd363b9bd-f140-4665-8be3-294ced3856dd.jpeg'),
-- (3, '534112ae-ae25-480b-a563-f299ab930dcb.jpeg'),
-- (3, '40168a02-50ff-4443-aaaf-19e78acf0c03.jpeg'),
-- (3, '6512b764-9a88-4b4a-bfa0-e665c5322ab5.jpeg'),
-- (2, '78186bc7-c296-4fff-8a71-9911a9254a49.jpeg'),
-- (2, '3192e05f-8769-4d2e-aadf-bac5775e52d7.jpeg'),
-- (2, '5c98a2e7-5808-4d6d-972a-8eb94a6cea40.jpeg'),
-- (4, 'cf9eea8a-b2b9-4d31-a0a6-571351c279a0.jpeg'),
-- (4, 'e386bdd9-7c35-4455-b965-eca7e6e0341a.jpeg'),
-- (4, 'f54f652c-0dc6-42ec-8882-e24bfad72eee.jpeg'),
-- (5, '16010779-e2bf-4088-b067-b77cb4a5d831.jpeg'),
-- (5, 'a9189633-3e5d-46b6-82e4-9e14585d960e.jpeg'),
-- (5, 'ffe7ceed-27a3-4bbc-b0cd-4bf3579ab35f.jpeg'),
-- (6, '3d0ee7e2-2d24-43eb-9ef4-566afb034821.jpeg'),
-- (6, 'e944a7aa-e044-4f39-b4ad-c073e7709f28.jpeg'),
-- (6, '3551ff9a-aa18-4a5f-89f1-07cf07adfc47.jpeg'),
-- (7, 'c84259cb-8ca5-4eec-86e3-8cdcb12501df.jpeg'),
-- (7, '03acb889-4434-42dd-9021-0eac532dd3df.jpeg'),
-- (7, '08827bf6-5554-4e49-8718-fa5bae7a709a.jpeg'),
-- (9, '87092ae9-c2e1-4306-acd4-02717373f3a7.jpeg'),
-- (9, 'cb4525ff-e606-40f2-9767-967179d012c0.jpeg'),
-- (9, 'c5de1519-571b-4633-8231-f2573524f261.jpeg'),
-- (8, '98788167-394e-47ad-a07b-3f9cb32e9f73.jpeg'),
-- (8, '5c96ae3e-453b-4527-9804-0b07cbeb77ac.jpeg'),
-- (8, '4157a500-f7b4-42e1-a298-d28f9abe40eb.jpeg');
--
-- INSERT INTO characters (name) VALUES
-- ('모험적인'),
-- ('호기심 많은'),
-- ('활동적인'),
-- ('정적인'),
-- ('편안한'),
-- ('문화 탐방가'),
-- ('사교적인'),
-- ('자연을 사랑하는'),
-- ('예산 중심의'),
-- ('사진가');
--
-- INSERT INTO members_characters (member_id, character_id) VALUES
-- (1, 1),
-- (1, 2),
-- (1, 4),
-- (7, 1),
-- (7, 2),
-- (7, 4);
