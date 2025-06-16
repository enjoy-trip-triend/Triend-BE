DROP
DATABASE IF EXISTS triend;
CREATE
DATABASE triend;
USE
triend;

-- SIDOS
CREATE TABLE `sidos`
(
    `id`        BIGINT      NOT NULL,
    `sido_code` INT         NOT NULL UNIQUE,
    `sido_name` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id`)
);

-- GUGUNS
CREATE TABLE `guguns`
(
    `id`         BIGINT      NOT NULL,
    `gugun_code` INT         NOT NULL UNIQUE,
    `gugun_name` VARCHAR(20) NOT NULL,
    `sido_code`  INT         NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`sido_code`) REFERENCES `sidos` (`sido_code`)
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

-- PLACES_CATEGORIES
CREATE TABLE `places_categories`
(
    `id`                  BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `category_group_code` VARCHAR(10),
    `category_group_name` VARCHAR(50),
    `category_name`       VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
);

-- PLACES
CREATE TABLE `places`
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
CREATE TABLE `my_places`
(
    `id`        BIGINT NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `member_id` BIGINT NOT NULL,
    `kakaoId`   BIGINT NOT NULL COMMENT '카카오 API 고유 키 값',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
    FOREIGN KEY (`kakaoId`) REFERENCES `places` (`kakaoId`)
);

-- PLANNERS
CREATE TABLE `planners`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `start_day`   DATETIME     NOT NULL,
    `end_day`     DATETIME     NOT NULL,
    `member_id`   BIGINT       NOT NULL,
    `name`        VARCHAR(255) NOT NULL,
    `location`    VARCHAR(10)  NOT NULL,
    `comment`     VARCHAR(100),
    `exposure`    ENUM('PRIVATE', 'PUBLIC') NOT NULL DEFAULT 'PUBLIC',
    `password`    VARCHAR(255),
    `likes_count` BIGINT       NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
);

-- PLANNERS_LOCATIONS
CREATE TABLE `planners_locations`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id` BIGINT      NOT NULL,
    `sido_code`  INT       NOT NULL,
    `gugun_code` INT       NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`),
    FOREIGN KEY (`sido_code`) REFERENCES `sidos` (`sido_code`),
    FOREIGN KEY (`gugun_code`) REFERENCES `guguns` (`gugun_code`)
);

-- PLANNERS_LIKES
CREATE TABLE `planners_likes`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id` BIGINT       NOT NULL,
    `member_id`  BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
);

-- PLANNERS_MEMBERS
CREATE TABLE `planners_members`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id` BIGINT       NOT NULL,
    `member_id`  BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`),
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`)
);

-- PLACES_IMAGES
CREATE TABLE `places_images`
(
    `kakaoId`   BIGINT       NOT NULL,
    `image_key` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`kakaoId`),
    FOREIGN KEY (`kakaoId`) REFERENCES `places` (`kakaoId`)
);

-- SCHEDULES
CREATE TABLE `schedules`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'auto_increment',
    `planner_id` BIGINT       NOT NULL,
    `date`       TIMESTAMP    NOT NULL,
    `start_time` TIMESTAMP,
    `content`    VARCHAR(255),
    `place_url`  VARCHAR(255),
    `idx`        BIGINT       NOT NULL,
    `place_id`   BIGINT       NOT NULL,
    PRIMARY KEY (`id`, `planner_id`),
    FOREIGN KEY (`planner_id`) REFERENCES `planners` (`id`),
    FOREIGN KEY (`place_id`) REFERENCES `places` (`kakaoId`)
);
