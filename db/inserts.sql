USE virtual_wallet;

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN');

INSERT INTO currencies (currency_code, name, symbol, decimals, is_active)
VALUES ('USD', 'US Dollar', '$', 2, 1),
       ('EUR', 'Euro', '€', 2, 1),
       ('BGN', 'Bulgarian Lev', 'лв', 2, 0),
       ('GBP', 'British Pound', '£', 2, 1);

INSERT INTO users (username, password_hash, first_name, last_name, email, role_id)
VALUES ('admin', '$2a$12$OXI1ydbzap4eYyUs5zgoyOL27Wt3Hb8XWfD908GFNStr/6GD0A4t2', 'Admin', 'Adminov', 'admin@mail.com',
        2),
       ('user1', '$2a$12$sXJx499qUExi3wapahjDBO8QSndygpTJFC2WP9meWFW.yNATbHp52', 'User', 'One', 'user1@mail.com', 1),
       ('user2', '$2a$12$tlv00PfAypPzhTgHpg2uyOkHJ0iJzLIBSnBeIB/yQBa1y3/GYhbnq', 'User', 'Two', 'user2@mail.com', 1);

INSERT INTO wallets (user_id, name, balance, currency_code, is_default)
VALUES (2, 'User1 USD Wallet', 500.00, 'USD', TRUE),
       (2, 'User1 EUR Wallet', 200.00, 'EUR', FALSE),
       (3, 'User2 EUR Wallet', 1000.00, 'EUR', TRUE);

INSERT INTO cards (user_id, card_holder, card_suffix, expiration_month, expiration_year, status)
VALUES (2, 'USER ONE', '1234', 12, 2028, 'ACTIVE'),
       (3, 'USER TWO', '5678', 11, 2027, 'ACTIVE');

INSERT INTO exchange_rates (from_currency_code, to_currency_code, rate, last_updated)
VALUES ('EUR', 'USD', 1.10, NOW()),
       ('USD', 'EUR', 0.91, NOW()),
       ('EUR', 'BGN', 1.95583, NOW()),
       ('BGN', 'EUR', 0.51, NOW());

INSERT INTO transactions (label, type, status,
                          sender_amount, sender_currency_code,
                          recipient_amount, recipient_currency_code,
                          exchange_rate,
                          sender_id, sender_wallet_id,
                          recipient_id, recipient_wallet_id)
VALUES ('Transfer user1 → user2',
        'TRANSFER',
        'CONFIRMED',
        100.00, 'USD',
        91.00, 'EUR',
        0.91,
        2, 1,
        3, 3),
       ('Top Up ⋅⋅1234',
        'TOP_UP',
        'CONFIRMED',
        200.00, 'EUR',
        200.00, 'EUR',
        1.0,
        NULL, NULL,
        2, 2);