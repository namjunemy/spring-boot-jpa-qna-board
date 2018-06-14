INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('njkim', 'test', '남준', 'namjunemy@gmail.com');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('njkim2', 'test', '남준2', 'namjunemy@gmail.com');

INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer) VALUES (1, 1, 'Spring Boot JPA QnA Board', 'this is a contents', CURRENT_TIMESTAMP(), 0);
INSERT INTO QUESTION (id, writer_id, title, contents, create_date, count_of_answer) VALUES (2, 2, 'Spring Boot JPA QnA Board2', 'this is a contents2', CURRENT_TIMESTAMP(), 0);