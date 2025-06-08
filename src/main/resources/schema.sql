DROP DATABASE IF EXISTS triend;

CREATE DATABASE triend;
use triend;

CREATE TABLE members (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    email VARCHAR(50) NOT NULL UNIQUE COMMENT 'unique',
    password VARCHAR(255) NOT NULL COMMENT '해싱해서 저장',
    name VARCHAR(10) NOT NULL,
    role ENUM('MEMBER', 'ADMIN') NOT NULL DEFAULT 'MEMBER',
    birth DATE NULL COMMENT '생년월일',
    mbti CHAR(4) NULL COMMENT 'MBTI 성격 유형', 
    refresh_token VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE characters (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE categories (
  id   BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
  name VARCHAR(50)  NOT NULL COMMENT '카테고리 이름',
  PRIMARY KEY (id)
);


CREATE TABLE members_characters (
    id BIGINT NOT NULL AUTO_INCREMENT,
    member_id BIGINT NOT NULL,
    character_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (character_id) REFERENCES characters(id)
);

CREATE TABLE planners (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    start_day VARCHAR(255) NOT NULL,
    end_day VARCHAR(255) NOT NULL,
    member_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id) REFERENCES Members(id)
);

CREATE TABLE my_places (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    kakao_id VARCHAR(30) NOT NULL COMMENT '카카오 플레이스 PK',
    address       VARCHAR(255) NOT NULL COMMENT '주소',
    lat           DOUBLE        NOT NULL COMMENT '위도',
    lon           DOUBLE        NOT NULL COMMENT '경도',
    location_name VARCHAR(100) NOT NULL COMMENT '장소 이름',
    description   TEXT           NULL COMMENT '장소 설명',
    member_id     BIGINT        NOT NULL COMMENT '회원 ID',
    category_id   BIGINT        NOT NULL COMMENT '카테고리 ID',
    PRIMARY KEY (id),
    CONSTRAINT fk_my_places_member
      FOREIGN KEY (member_id) REFERENCES members(id),
    CONSTRAINT fk_my_places_category
      FOREIGN KEY (category_id) REFERENCES categories(id)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COMMENT='내가 저장한 나만의 장소';


CREATE TABLE plans (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    planner_id BIGINT NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    place_name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NULL,
    content VARCHAR(255) NULL,
    lat DOUBLE NULL,
    lon DOUBLE NULL,
    place_url VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (planner_id) REFERENCES planners(id)
);

CREATE TABLE plan_images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    plan_id BIGINT NOT NULL,
    image_key VARCHAR(255) NOT NULL,
    FOREIGN KEY (plan_id) REFERENCES plans(id) ON DELETE CASCADE
);


CREATE TABLE boards (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '작성시간',
    member_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);