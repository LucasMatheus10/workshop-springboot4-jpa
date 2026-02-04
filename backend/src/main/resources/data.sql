-- TB_USER
INSERT INTO tb_user (id, email, name, password, phone, role) VALUES
(1, 'john.doe@email.com', 'John Doe', '$2a$10$QsOFlxuIlaK7wL2Jgh3zKOvIvdoTrdn23eNl5G02Cbp5YZT7.fmXG', '1234567890', 'ADMIN'),
(2, 'jane.smith@email.com', 'Jane Smith', '$2a$10$sA63g7KW00eIFAK6o9y6Ruvl/L8azPoL5ypkw5OQQktd5x0eSjH26', '0987654321', 'USER');

-- TB_ORDER
INSERT INTO tb_order (id, moment, order_status, client_id) VALUES
(1, '2024-06-02 08:30:00-03', 'WAITING_PAYMENT', 2),
(2, '2024-06-03 11:15:00-03', 'SHIPPED', 1),
(3, '2026-01-28 14:15:10.128047-03', 'PAID', 1);

-- PAYMENT
INSERT INTO payment (order_id, moment) VALUES
(1, '2026-01-28 14:15:10.163816-03');

-- TB_CATEGORY
INSERT INTO tb_category (id, name) VALUES
(1, 'Electronics'),
(2, 'Books'),
(3, 'Clothing'),
(4, 'Periféricos'),
(5, 'Monitores');

-- TB_PRODUCT
INSERT INTO tb_product (id, description, img_url, name, price) VALUES
(1, '42 inch smart TV', 'https://www.hisense.com.br/_next/image?url=%2FA4N%2F1.png&w=2048&q=100', 'Smart TV', 2190),
(2, 'Apple laptop', 'https://t4.ftcdn.net/jpg/06/01/14/23/360_F_601142328_VnY6DMf1sC0RULodemaCSrvXSlFhO1lA.jpg', 'Macbook Pro', 6250),
(3, 'High performance gaming PC', 'https://images.unsplash.com/photo-1587202372775-e229f172b9d7?auto=format&fit=crop&q=80&w=1000', 'PC Gamer', 3200),
(4, 'A beginner''s guide to Rails', 'https://images.unsplash.com/photo-1516116216624-53e697fedbea?auto=format&fit=crop&q=80&w=1000', 'Rails for Dummies', 100.99),
(5, 'Noise-cancelling headphones', 'https://www.pngall.com/wp-content/uploads/5/Headset-PNG-Image-HD.png', 'Headset', 299.99),
(6, 'Camiseta algodão 100%', 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?q=80&w=1000', 'Camiseta Tech', 59.9),
(7, 'Moletom com capuz confortável', 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?q=80&w=1000', 'Moletom Gamer', 150),
(8, 'Boné aba curva preto', 'https://images.unsplash.com/photo-1588850561407-ed78c282e89b?q=80&w=1000', 'Boné Estilizado', 45),
(9, 'A fantasy novel', 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&q=80&w=1000', 'The Lord of the Rings', 90.99);

-- TB_ORDER_ITEM
INSERT INTO tb_order_item (order_id, product_id, quantity, price) VALUES
(1, 1, 2, 90.5),
(1, 3, 1, 6250),
(2, 3, 2, 6250),
(3, 5, 2, 100.99);

-- TB_PRODUCT_CATEGORY
INSERT INTO tb_product_category (product_id, category_id) VALUES
(1, 2),
(2, 1),
(3, 1),
(4, 1),
(5, 2),
(7, 3),
(8, 3),
(9, 3),
(6, 3),
(2, 5);


ALTER SEQUENCE tb_user_seq RESTART WITH 3;
ALTER SEQUENCE tb_order_seq RESTART WITH 4;
ALTER SEQUENCE tb_category_seq RESTART WITH 6;
ALTER SEQUENCE tb_product_seq RESTART WITH 10;
