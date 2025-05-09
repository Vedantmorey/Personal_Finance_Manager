CREATE DATABASE IF NOT EXISTS user_data1;
USE user_data1;

CREATE TABLE IF NOT EXISTS usernew(
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);


INSERT INTO usernew (user_id, username, password, email) VALUES 
(1, 'vedantmorey18', 'Vedant1#', 'moreyvedant@gmail.com'),
(2, 'Sumit18', 'Sumit123', 'Sumit123@gmail.com'),
(3, 'vaibhav18', 'vaibhav123', 'vaibhav123@gmail.com'),
(4, 'parth123', 'parth123', 'parth123@gmail.com')
ON DUPLICATE KEY UPDATE 
    password = VALUES(password),
    email = VALUES(email);


CREATE TABLE IF NOT EXISTS transaction_new(
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    date DATE,
    type VARCHAR(10),
    category VARCHAR(50),
    amount DECIMAL(10,2),
    CONSTRAINT fk_user_transaction
        FOREIGN KEY (user_id) 
        REFERENCES usernew(user_id)
        ON DELETE CASCADE
);


INSERT INTO transaction_new (user_id, date, type, category, amount) VALUES 
(1, '2012-12-12', 'income', 'salary', 1000),
(1, '2025-05-04', 'expense', 'food', 25.50);

create table budgets(budget_id int primary key auto_increment,
user_id int,
month varchar(10) ,
category varchar(50),
limit_amount decimal(10,2), 
CONSTRAINT fk_user_transaction
FOREIGN KEY (user_id) 
REFERENCES usernew(user_id)
ON DELETE CASCADE);
INSERT INTO budgets (user_id, month, category, limit_amount)
VALUES (1, 'May', 'Groceries', 5000.00);

SELECT 
    *
FROM
    budgets;
SELECT * FROM usernew;
SELECT * FROM transaction_new;