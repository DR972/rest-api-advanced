INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('ATV riding', 'Description ATV riding', 100, 10, '2022-04-01T10:12:45.123', '2022-04-07T14:15:13.257');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Horse riding', 'Horse riding description', 80, 8, '2022-04-02T10:12:45.123', '2022-04-05T14:15:13.257');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Visiting a restaurant', 'Visiting the Plaza restaurant', 50, 7, '2022-04-02T10:12:45.123', '2022-04-02T14:15:13.257');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Visit to the drama theater', 'Description visit to the drama theater', 45, 2, '2022-03-30T10:12:45.123', '2022-04-08T14:15:13.257');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Shopping at the tool store', 'Description shopping at the tool store', 30, 10, '2022-03-25T10:12:45.123', '2022-04-01T14:15:13.257');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Shopping at the supermarket', 'Shopping at Lidl supermarket chain', 80, 12, '2022-04-01T10:12:45.123', '2022-04-014T14:15:13.257');
INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Hot air balloon flight', 'An unforgettable hot air balloon flight', 150, 12, '2022-03-01T10:12:45.123', '2022-03-014T14:15:13.257');

INSERT INTO tag (name) VALUES ('rest');
INSERT INTO tag (name) VALUES ('nature');
INSERT INTO tag (name) VALUES ('shopping');
INSERT INTO tag (name) VALUES ('atv');
INSERT INTO tag (name) VALUES ('horse');
INSERT INTO tag (name) VALUES ('theater');
INSERT INTO tag (name) VALUES ('tool');
INSERT INTO tag (name) VALUES ('food');
INSERT INTO tag (name) VALUES ('supermarket');
INSERT INTO tag (name) VALUES ('restaurant');
INSERT INTO tag (name) VALUES ('flight');
INSERT INTO tag (name) VALUES ('visit');

INSERT INTO gift_certificate_tag VALUES (1, 1);
INSERT INTO gift_certificate_tag VALUES (1, 2);
INSERT INTO gift_certificate_tag VALUES (1, 4);

INSERT INTO gift_certificate_tag VALUES (2, 1);
INSERT INTO gift_certificate_tag VALUES (2, 2);
INSERT INTO gift_certificate_tag VALUES (2, 5);

INSERT INTO gift_certificate_tag VALUES (3, 8);
INSERT INTO gift_certificate_tag VALUES (3, 10);
INSERT INTO gift_certificate_tag VALUES (3, 12);

INSERT INTO gift_certificate_tag VALUES (4, 6);
INSERT INTO gift_certificate_tag VALUES (4, 12);

INSERT INTO gift_certificate_tag VALUES (5, 3);
INSERT INTO gift_certificate_tag VALUES (5, 7);

INSERT INTO gift_certificate_tag VALUES (6, 3);
INSERT INTO gift_certificate_tag VALUES (6, 8);
INSERT INTO gift_certificate_tag VALUES (6, 9);

INSERT INTO gift_certificate_tag VALUES (7, 1);
INSERT INTO gift_certificate_tag VALUES (7, 2);
INSERT INTO gift_certificate_tag VALUES (7, 11);

INSERT INTO customer (name) VALUES ('user1');
INSERT INTO customer (name) VALUES ('user2');
INSERT INTO customer (name) VALUES ('user3');

INSERT INTO customer_order (customer_id, purchase_time, amount) VALUES (1, '2022-04-02T10:12', 150);
INSERT INTO customer_order (customer_id, purchase_time, amount) VALUES (2, '2022-04-03T10:12', 180);
INSERT INTO customer_order (customer_id, purchase_time, amount) VALUES (3, '2022-04-05T10:12', 125);
INSERT INTO customer_order (customer_id, purchase_time, amount) VALUES (1, '2022-04-04T10:12', 110);
INSERT INTO customer_order (customer_id, purchase_time, amount) VALUES (2, '2022-04-07T10:12', 50);

INSERT INTO customer_order_gift_certificate VALUES (1, 7);

INSERT INTO customer_order_gift_certificate VALUES (2, 1);
INSERT INTO customer_order_gift_certificate VALUES (2, 2);

INSERT INTO customer_order_gift_certificate VALUES (3, 2);
INSERT INTO customer_order_gift_certificate VALUES (3, 4);

INSERT INTO customer_order_gift_certificate VALUES (4, 5);
INSERT INTO customer_order_gift_certificate VALUES (4, 6);

INSERT INTO customer_order_gift_certificate VALUES (5, 3);

COMMIT;