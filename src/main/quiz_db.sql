CREATE DATABASE quiz_system;

USE quiz_system;

-- Bảng User
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng roles
CREATE TABLE roles (
 id INT AUTO_INCREMENT PRIMARY KEY,
 role_name VARCHAR(50) NOT NULL
);

-- Bảng users_roles
CREATE TABLE users_roles (
   user_id INT NOT NULL,
   role_id INT NOT NULL,
   FOREIGN KEY (user_id) REFERENCES users(id),
   FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- Bảng User Quiz History
CREATE TABLE user_quiz_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    quiz_id INT,
    score INT,
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
);

-- Bảng Quiz
-- question_type: single, multiple, fill
-- question_title: cau hoi nay thuoc goi nao
-- question_content: noi dung cau hoi
CREATE TABLE quizzes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question_title VARCHAR(255) NOT NULL,
    question_content TEXT,
    question_type  TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng quizzes_choices
-- choices: chua noi dung dap an a,b,c,d
CREATE TABLE quizzes_choices (
    id INT AUTO_INCREMENT PRIMARY KEY,
	choices TEXT,
    FOREIGN KEY (id) REFERENCES quizzes(id)
);

-- Bảng quizzes_choices
-- chua danh sach dap an dung voi tung cau hoi
CREATE TABLE quizzes_correct_answers (
    id INT AUTO_INCREMENT PRIMARY KEY,
	correct_answers TEXT,
    FOREIGN KEY (id) REFERENCES quizzes(id)
);

INSERT INTO `quiz_system`.`roles` (`role_name`) VALUES ('ROLE_ADMIN');
INSERT INTO `quiz_system`.`roles` (`role_name`) VALUES ('ROLE_USER');