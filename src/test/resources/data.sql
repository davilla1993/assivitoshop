-- Passwords are in the format: Password<UserLetter>123. Unless specified otherwise.
-- Encrypted using https://www.javainuse.com/onlineBcrypt

INSERT INTO local_user(email, first_name, last_name, password, username, email_verified)
    VALUES ('UserA@junit.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$mQX.ouGI2dYIDTskrsuR9OMvoz5ORn/3GUP2se.YgQbHBRfNVe116', 'UserA', true),
        ('UserB@junit.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$s7ARhJYH0CB9H6ZQT9N/iefNzf1G9SnnNqarOPA2Dfpd.2v62yz9i', 'UserB', false);
