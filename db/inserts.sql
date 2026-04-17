USE virtual_wallet;

INSERT INTO roles (name)
VALUES
    ('USER'),
    ('ADMIN');

INSERT INTO currencies (currency_code, name, symbol, decimals, is_active)
VALUES
    ('EUR', 'Euro', '€', 2, TRUE),
    ('USD', 'US Dollar', '$', 2, TRUE),
    ('BGN', 'Bulgarian Lev', 'лв', 2, FALSE);

INSERT INTO users
(username, password_hash, first_name, last_name, email, phone_number, photo_url, role_id, is_blocked)
VALUES
    ('ivan',
     '$2a$12$BTDgPqC5sEuzNweEW2ODG.R6cC1NuOJRUWCOXS9.npTJuV8INlyJ2',
     'Ivan', 'Petrov', 'ivan@test.com', '0888000001', NULL, 1, FALSE),

    ('maria',
     '$2a$12$7P.qXtipD.NGhVI6apI8BuczeJmImTTwVmH/YNBiSfii5GIwk2xtC',
     'Maria', 'Ivanova', 'maria@test.com', '0888000002', NULL, 1, FALSE),

    ('georgi',
     '$2a$12$s0AaJUBrkLe9IY4Ddp2eIOTNXd7sLTLC5HE.agUOwNomM6nCOF0mO',
     'Georgi', 'Dimitrov', 'georgi@test.com', '0888000003', NULL, 1, FALSE),

    ('admin',
     '$2a$12$OXI1ydbzap4eYyUs5zgoyOL27Wt3Hb8XWfD908GFNStr/6GD0A4t2',
     'Admin', 'Root', 'admin@test.com', NULL, NULL, 2, FALSE);

INSERT INTO wallets
(user_id, name, balance, currency_code, is_default, version)
VALUES
    (1, 'Ivan Main',    300.00, 'EUR', TRUE,  0),
    (1, 'Ivan Savings', 800.00, 'USD', FALSE, 0),

    (2, 'Maria Main',   150.00, 'EUR', TRUE,  0),
    (2, 'Maria Travel', 400.00, 'USD', FALSE, 0),

    (3, 'Georgi Main',  500.00, 'BGN', TRUE,  0),

    (4, 'Admin Wallet', 5000.00,'EUR', TRUE,  0);

INSERT INTO cards
(user_id, card_suffix, expiration_month, expiration_year, card_holder, status)
VALUES
    (1, '1111', 12, 2028, 'Ivan Petrov',   'ACTIVE'),
    (1, '2222',  6, 2026, 'Ivan Petrov',   'USER_DEACTIVATED'),

    (2, '3333', 11, 2027, 'Maria Ivanova', 'ACTIVE'),

    (3, '4444',  3, 2029, 'Georgi Dimitrov','ACTIVE'),

    (4, '9999',  1, 2030, 'Admin Root',    'ACTIVE');

INSERT INTO transactions
(label, type, status, amount, currency_code,
 sender_wallet_id, recipient_wallet_id,
 sender_id, recipient_id, external_reference)
VALUES
    (NULL, 'TOP_UP',   'CONFIRMED', 200.00, 'EUR', NULL, 1, NULL, 1, 'TOPUP-IVAN-001'),
    (NULL, 'TOP_UP',   'CONFIRMED', 300.00, 'EUR', NULL, 3, NULL, 2, 'TOPUP-MARIA-001'),

    ('For Caffe', 'TRANSFER', 'CONFIRMED', 50.00,  'EUR', 1, 3, 1, 2, 'TX-IVAN-MARIA-001'),
    (NULL, 'TRANSFER', 'CONFIRMED', 75.00,  'EUR', 3, 5, 2, 3, 'TX-MARIA-GEORGI-001'),

    ('Apple Inc.', 'PAYMENT',  'CONFIRMED', 20.00,  'BGN', 5, NULL, 3, NULL, 'PAY-GEORGI-001'),
    ('Adobe' ,'PAYMENT',  'CONFIRMED', 100.00, 'EUR', 6, NULL, 4, NULL, 'PAY-ADMIN-001');
