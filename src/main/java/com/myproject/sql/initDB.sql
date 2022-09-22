CREATE TABLE `Category` (
  `id` varchar(10) PRIMARY KEY,
  `name` nvarchar(50) NOT NULL
);

CREATE TABLE `Courses` (
  `id` varchar(255) PRIMARY KEY,
  `category_id` varchar(10) NOT NULL,
  `language` varchar(50),
  `name` nvarchar(20) NOT NULL,
  `description` text,
  `user_created` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `is_public` boolean,
  `create_date` date,
  `update_date` date,
  `num_students` int,
  `is_active` boolean
);

CREATE TABLE `ResourcesCourse` (
  `id` varchar(10) PRIMARY KEY,
  `course_id` varchar(10),
  `title` text,
  `link` text NOT NULL
);

CREATE TABLE `CoursesVideo` (
  `id` varchar(10) PRIMARY KEY,
  `content_name` nvarchar(100) UNIQUE,
  `course_id` varchar(255),
  `link` text,
  `title` nvarchar(100),
  `descripton` text,
  `is_active` boolean,
  `create_date` date,
  `update_date` date
);

CREATE TABLE `Review` (
  `id` varchar(10) PRIMARY KEY,
  `user_id` varchar(255),
  `course_id` varchar(255),
  `content` text,
  `rate` double,
  `create_date` date,
  `update_date` date
);

CREATE TABLE `Role` (
  `id` varchar(10) PRIMARY KEY,
  `name` nvarchar(50)
);

CREATE TABLE `Account` (
  `user_name` varchar(255) PRIMARY KEY,
  `role_id` varchar(10),
  `password` varchar(50)
);

CREATE TABLE `UserDetail` (
  `user_name` varchar(255) PRIMARY KEY,
  `fullname` nvarchar(50),
  `birthdate` date,
  `gender` varchar(10),
  `email` varchar(50),
  `phone` varchar(10)
);

CREATE TABLE `Courses_Paid` (
  `id` varchar(10),
  `user_name` varchar(10),
  `courses_id` varchar(10),
  `buy_date` date
);

CREATE TABLE `Cart` (
  `user_name` varchar(255) PRIMARY KEY,
  `total_price` double,
  `payment_price` double
);

CREATE TABLE `CartDetail` (
  `id` varchar(10) PRIMARY KEY,
  `user_name` varchar(255),
  `course_id` varchar(10)
);

CREATE TABLE `Orders` (
  `id` varchar(10) PRIMARY KEY,
  `user_name` varchar(255),
  `payment_id` varchar(10),
  `total_price` double,
  `payment_price` double,
  `coupon_id` varchar(10),
  `qty` int,
  `create_date` date,
  `is_active` boolean
);

CREATE TABLE `Order_Details` (
  `id` varchar(10) PRIMARY KEY,
  `order_id` varchar(10),
  `course_id` varchar(10),
  `price` double
);

CREATE TABLE `Coupon` (
  `id` varchar(10) PRIMARY KEY,
  `name` nvarchar(50),
  `descripion` text,
  `type` varchar(5),
  `expired_date` date,
  `start_date` date,
  `create_date` date,
  `update_date` date,
  `user_created` varchar(255)
);

CREATE TABLE `Payment` (
  `id` varchar(10) PRIMARY KEY,
  `name` varchar(100)
);

ALTER TABLE `Courses` ADD FOREIGN KEY (`user_created`) REFERENCES `Account` (`user_name`);

ALTER TABLE `Courses` ADD FOREIGN KEY (`category_id`) REFERENCES `Category` (`id`);

ALTER TABLE `ResourcesCourse` ADD FOREIGN KEY (`course_id`) REFERENCES `Courses` (`id`);

ALTER TABLE `CoursesVideo` ADD FOREIGN KEY (`course_id`) REFERENCES `Courses` (`id`);

ALTER TABLE `Review` ADD FOREIGN KEY (`user_id`) REFERENCES `Account` (`user_name`);

ALTER TABLE `CartDetail` ADD FOREIGN KEY (`user_name`) REFERENCES `Cart` (`user_name`);

ALTER TABLE `CartDetail` ADD FOREIGN KEY (`course_id`) REFERENCES `Courses` (`id`);

ALTER TABLE `Orders` ADD FOREIGN KEY (`user_name`) REFERENCES `Account` (`user_name`);

ALTER TABLE `Order_Details` ADD FOREIGN KEY (`order_id`) REFERENCES `Orders` (`id`);

ALTER TABLE `Order_Details` ADD FOREIGN KEY (`course_id`) REFERENCES `Courses` (`id`);

ALTER TABLE `Orders` ADD FOREIGN KEY (`payment_id`) REFERENCES `Payment` (`id`);

ALTER TABLE `Coupon` ADD FOREIGN KEY (`user_created`) REFERENCES `Account` (`user_name`);

ALTER TABLE `Account` ADD FOREIGN KEY (`role_id`) REFERENCES `Role` (`id`);

ALTER TABLE `Review` ADD FOREIGN KEY (`course_id`) REFERENCES `Courses` (`id`);

ALTER TABLE `UserDetail` ADD FOREIGN KEY (`user_name`) REFERENCES `Account` (`user_name`);

ALTER TABLE `Courses_Paid` ADD FOREIGN KEY (`courses_id`) REFERENCES `Courses` (`id`);

ALTER TABLE `Courses_Paid` ADD FOREIGN KEY (`user_name`) REFERENCES `Account` (`user_name`);
