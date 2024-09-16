CREATE TABLE user_accounts (
    email varchar(255) primary key NOT NULL,
    password varchar(255) NOT NULL
);

CREATE TABLE CALCULATIONS (
    calculation_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    equation VARCHAR(255) NOT NULL,
    result VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,
    CONSTRAINT FK_user_email FOREIGN KEY (user_email) REFERENCES user_accounts(email)
);
