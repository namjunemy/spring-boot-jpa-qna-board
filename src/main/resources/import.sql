INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('njkim', 'test', '남준', 'namjunemy@gmail.com');
INSERT INTO USER (USER_ID, PASSWORD, NAME, EMAIL) VALUES ('njkim2', 'test', '남준2', 'namjunemy@gmail.com');

INSERT INTO QUESTION (id, writer_id, title, contents, create_date) VALUES (1, 1, 'Spring Boot JPA QnA Board', 'this is a contents', CURRENT_TIMESTAMP());
INSERT INTO QUESTION (id, writer_id, title, contents, create_date) VALUES (2, 2, 'Spring Boot JPA QnA Board2', 'this is a contents2', CURRENT_TIMESTAMP());