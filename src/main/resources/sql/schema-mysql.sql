
DROP TABLE IF EXISTS user_info;
CREATE TABLE `user_info`
(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(12) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `age` int(12) DEFAULT NULL,
    `username` varchar(20) DEFAULT NULL,
    `password` varchar(30) DEFAULT NULL,
    `gender` int(10) DEFAULT 0,
     PRIMARY KEY(id)
)