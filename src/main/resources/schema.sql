USE triend;

SET FOREIGN_KEY_CHECKS=0;

DROP TABLE IF EXISTS `schedules`;
DROP TABLE IF EXISTS `places_images`;
DROP TABLE IF EXISTS `planners_share`;
DROP TABLE IF EXISTS `planners_members`;
DROP TABLE IF EXISTS `planners_likes`;
DROP TABLE IF EXISTS `planners_locations`;
DROP TABLE IF EXISTS `planners`;
DROP TABLE IF EXISTS `my_places`;
DROP TABLE IF EXISTS `places`;
DROP TABLE IF EXISTS `places_categories`;
DROP TABLE IF EXISTS `members_characters`;
DROP TABLE IF EXISTS `characters`;
DROP TABLE IF EXISTS `members`;
DROP TABLE IF EXISTS `categories`;
DROP TABLE IF EXISTS `api_logs`;
DROP TABLE IF EXISTS `places_categories_groups`;
SET FOREIGN_KEY_CHECKS=1;

-- SIDOS
CREATE TABLE IF NOT EXISTS `sidos`
(
    `id`        BIGINT      NOT NULL AUTO_INCREMENT,
    `sido_code` INT         NOT NULL UNIQUE,
    `sido_name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`)
);

-- GUGUNS
CREATE TABLE IF NOT EXISTS `guguns`
(
    `id`         BIGINT      NOT NULL AUTO_INCREMENT,
    `gugun_code` INT         NOT NULL,
    `gugun_name` VARCHAR(20) NOT NULL,
    `sido_code`  INT         NOT NULL,
    PRIMARY KEY (`id`),
    -- 복합 유니크 키 (시도-구군)
    UNIQUE KEY `uk_guguns_sido_gugun` (`sido_code`, `gugun_code`),
    CONSTRAINT `fk_guguns_sido`
      FOREIGN KEY (`sido_code`)
      REFERENCES `sidos` (`sido_code`)
);

-- MEMBERS
CREATE TABLE `members`
(
    `id`            BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `email`         VARCHAR(50) NOT NULL UNIQUE COMMENT 'unique',
    `password`      CHAR(60)    NOT NULL COMMENT '해싱해서 저장',
    `name`          VARCHAR(10) NOT NULL,
    `role`          ENUM('MEMBER', 'ADMIN') NOT NULL DEFAULT 'MEMBER',
    `birth`         DATE NULL,
    `mbti`          CHAR(4) NULL,
    `refresh_token` VARCHAR(255),
    PRIMARY KEY (`id`)
);

-- CHARACTERS
CREATE TABLE `characters`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `name` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`id`)
);

-- MEMBERS_CHARACTERS
CREATE TABLE `members_characters`
(
    `id`           BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `member_id`    BIGINT NOT NULL,
    `character_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
    FOREIGN KEY (`character_id`) REFERENCES `characters` (`id`)
);

-- CATEGORIES (for future use)
CREATE TABLE `categories`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `name` VARCHAR(50) NOT NULL COMMENT '카테고리 이름',
    PRIMARY KEY (`id`)
);

-- PLACES_CATEGORIES_GROUPS
CREATE TABLE `places_categories_groups` (
  `id`               BIGINT         NOT NULL AUTO_INCREMENT,
  `group_code`       VARCHAR(10)    NOT NULL COMMENT 'API 제공 그룹 코드',
  `group_name`       VARCHAR(100)   NOT NULL COMMENT 'API 제공 그룹 이름',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_pcg_group_code` (`group_code`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COMMENT='장소 카테고리 그룹 테이블';

-- PLACES_CATEGORIES
CREATE TABLE `places_categories` (
  `id`                   BIGINT       NOT NULL AUTO_INCREMENT,
  `category_name`                 VARCHAR(200) NOT NULL COMMENT 'API 제공 카테고리 이름',
  `category_group_id`    BIGINT       NULL            COMMENT 'places_categories_grouops.id 참조',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_pc_category_name` (`category_name`),
  KEY `idx_pc_category_group` (`category_group_id`),
  CONSTRAINT `fk_places_categories_group`
    FOREIGN KEY (`category_group_id`)
    REFERENCES `places_categories_groups` (`id`)
    ON DELETE SET NULL
    ON UPDATE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COMMENT='장소 카테고리 테이블';

-- PLACES
CREATE TABLE `places` (
  `id`                  BIGINT        NOT NULL AUTO_INCREMENT COMMENT '내부 PK',
  `kakao_id`            BIGINT        NOT NULL               COMMENT '카카오 API 고유 키',
  `address_name`        VARCHAR(225)  NOT NULL               COMMENT '지번 주소',
  `road_address_name`   VARCHAR(225)  DEFAULT NULL           COMMENT '도로명 주소',
  `place_name`          VARCHAR(225)  NOT NULL               COMMENT '장소 이름',
  `latitude`            DECIMAL(10,7) NOT NULL               COMMENT '위도',
  `longitude`           DECIMAL(10,7) NOT NULL               COMMENT '경도',
  `phone`               VARCHAR(15)   DEFAULT NULL           COMMENT '전화번호',
  `save_count`          BIGINT        NOT NULL DEFAULT 0     COMMENT '추가된 횟수',
  `category_id`         BIGINT        NOT NULL               COMMENT 'places_categories.id 참조',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_places_kakao_id` (`kakao_id`),
  KEY `idx_places_category` (`category_id`),
  CONSTRAINT `fk_places_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `places_categories` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COMMENT='플래너용 장소 테이블';



-- MY_PLACES
CREATE TABLE `my_places`
(
    `id`        BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `member_id` BIGINT NOT NULL,
    `place_id`   BIGINT NOT NULL COMMENT '장소 테이블 PK',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
    FOREIGN KEY (`place_id`) REFERENCES `places` (`id`)
);

-- PLANNERS
CREATE TABLE `planners`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `start_day`   DATETIME     NOT NULL,
    `end_day`     DATETIME     NOT NULL,
    `member_id`   BIGINT       NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `comment`     VARCHAR(100),
    `exposure`    ENUM('PRIVATE', 'PUBLIC') NOT NULL DEFAULT 'PUBLIC',
    `likes_count` BIGINT       NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
);

-- PLANNERS_LOCATIONS
CREATE TABLE `planners_locations`
(
    `id`          BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id`  BIGINT NOT NULL,
    `sido_code`   INT    NOT NULL,
    `gugun_code`  INT    NOT NULL,
    PRIMARY KEY (`id`),
    -- 플래너 참조
    CONSTRAINT `fk_planners_locations_planner`
      FOREIGN KEY (`planner_id`)
      REFERENCES `planners` (`id`),
    -- 시도 참조
    CONSTRAINT `fk_planners_locations_sido`
      FOREIGN KEY (`sido_code`)
      REFERENCES `sidos` (`sido_code`),
    -- 복합 (시도, 구군) 참조
    CONSTRAINT `fk_planners_locations_gugun`
      FOREIGN KEY (`sido_code`, `gugun_code`)
      REFERENCES `guguns` (`sido_code`, `gugun_code`)
);

-- PLANNERS_LIKES
CREATE TABLE `planners_likes`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id` BIGINT NOT NULL,
    `member_id`  BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
);

-- PLANNERS_MEMBERS
CREATE TABLE `planners_members`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id` BIGINT NOT NULL,
    `member_id`  BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
);

-- PLANNERS_share
CREATE TABLE  `planners_share`
(
     `id` BIGINT NOT NULL AUTO_INCREMENT,
     `secret_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '공유 링크 식별자',
     `planner_id` BIGINT NOT NULL,
     `password` VARCHAR(255) NOT NULL COMMENT 'BCrypt 해시된 비밀번호',
     PRIMARY KEY (`id`),
     FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`) ON DELETE CASCADE
);

-- PLACES_IMAGES
CREATE TABLE `places_images`
(
    `place_id`   BIGINT       NOT NULL,
    `image_key` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`place_id`),
    FOREIGN KEY (`place_id`) REFERENCES `places` (`id`)
);

-- SCHEDULES
CREATE TABLE  `schedules`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id` BIGINT NOT NULL,
    `date`       DATE   NOT NULL,
    `start_time` TIME,
    `content`    VARCHAR(255),
    `place_url`  VARCHAR(255),
    `idx`        INT    NOT NULL,
    `place_id`   BIGINT NOT NULL,
    PRIMARY KEY (`id`, `planner_id`),
    FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`),
    FOREIGN KEY (`place_id`) REFERENCES `places` (`id`)
);

-- ApiLog
CREATE TABLE `api_logs`
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id        BIGINT,
    endpoint         VARCHAR(255) NOT NULL,
    http_method      VARCHAR(10)  NOT NULL,
    status_code      INT          NOT NULL,
    response_time_ms BIGINT       NOT NULL,
    ip_address       VARCHAR(45),
    user_agent       TEXT,
    request_body     TEXT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_created_at ON api_logs(created_at);
