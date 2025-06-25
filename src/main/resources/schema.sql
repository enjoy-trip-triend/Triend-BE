USE triend;

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
CREATE TABLE IF NOT EXISTS `members`
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
CREATE TABLE IF NOT EXISTS `characters`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `name` VARCHAR(10) NOT NULL,
    PRIMARY KEY (`id`)
);

-- MEMBERS_CHARACTERS
CREATE TABLE IF NOT EXISTS `members_characters`
(
    `id`           BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `member_id`    BIGINT NOT NULL,
    `character_id` BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
    FOREIGN KEY (`character_id`) REFERENCES `characters` (`id`)
);

-- CATEGORIES (for future use)
CREATE TABLE IF NOT EXISTS `categories`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `name` VARCHAR(50) NOT NULL COMMENT '카테고리 이름',
    PRIMARY KEY (`id`)
);

-- PLACES_CATEGORIES
CREATE TABLE IF NOT EXISTS `places_categories`
(
    `id`                  BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `category_group_code` VARCHAR(10),
    `category_group_name` VARCHAR(50),
    `category_name`       VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
);

-- PLACES
CREATE TABLE IF NOT EXISTS `places`
(
    `kakaoId`       BIGINT       NOT NULL COMMENT '카카오 API 고유 키 값',
    `address`       VARCHAR(225) NOT NULL,
    `location_name` VARCHAR(225) NOT NULL,
    `lat` DOUBLE NOT NULL,
    `lon` DOUBLE NOT NULL,
    `count`         BIGINT       NOT NULL DEFAULT 0,
    `category_id`   BIGINT       NOT NULL,
    PRIMARY KEY (`kakaoId`),
    FOREIGN KEY (`category_id`) REFERENCES `places_categories` (`id`)
);

-- MY_PLACES
CREATE TABLE IF NOT EXISTS `my_places`
(
    `id`        BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `member_id` BIGINT NOT NULL,
    `kakaoId`   BIGINT NOT NULL COMMENT '카카오 API 고유 키 값',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
    FOREIGN KEY (`kakaoId`) REFERENCES `places` (`kakaoId`)
);

-- PLANNERS
CREATE TABLE IF NOT EXISTS `planners`
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
CREATE TABLE IF NOT EXISTS `planners_locations` (
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
CREATE TABLE IF NOT EXISTS `planners_likes`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id` BIGINT NOT NULL,
    `member_id`  BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
);

-- PLANNERS_MEMBERS
CREATE TABLE IF NOT EXISTS `planners_members`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id` BIGINT NOT NULL,
    `member_id`  BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
);

-- PLANNERS_share
CREATE TABLE IF NOT EXISTS `planners_share` (
     `id` BIGINT NOT NULL AUTO_INCREMENT,
     `secret_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '공유 링크 식별자',
     `planner_id` BIGINT NOT NULL,
     `password` VARCHAR(255) NOT NULL COMMENT 'BCrypt 해시된 비밀번호',
     PRIMARY KEY (`id`),
     FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`) ON DELETE CASCADE
);

-- PLACES_IMAGES
CREATE TABLE IF NOT EXISTS `places_images`
(
    `kakaoId`   BIGINT       NOT NULL,
    `image_key` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`kakaoId`),
    FOREIGN KEY (`kakaoId`) REFERENCES `places` (`kakaoId`)
);

-- SCHEDULES
CREATE TABLE IF NOT EXISTS `schedules`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id` BIGINT NOT NULL,
    `date`       DATE   NOT NULL,
    `start_time` TIMESTAMP,
    `content`    VARCHAR(255),
    `place_url`  VARCHAR(255),
    `idx`        INT    NOT NULL,
    `place_id`   BIGINT NOT NULL,
    PRIMARY KEY (`id`, `planner_id`),
    FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`),
    FOREIGN KEY (`place_id`) REFERENCES `places` (`kakaoId`)
);
