--
-- Data for Name: tb_user; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO tb_user (email, name, password, phone, role) VALUES
('john.doe@email.com', 'John Doe', '$2a$10$QsOFlxuIlaK7wL2Jgh3zKOvIvdoTrdn23eNl5G02Cbp5YZT7.fmXG', '1234567890', 'ADMIN'),
('jane.smith@email.com', 'Jane Smith', '$2a$10$sA63g7KW00eIFAK6o9y6Ruvl/L8azPoL5ypkw5OQQktd5x0eSjH26', '0987654321', 'USER');


--
-- Data for Name: tb_order; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO tb_order (moment, order_status, client_id) VALUES
('2024-06-02 08:30:00-03', 'WAITING_PAYMENT', 2),
('2024-06-03 11:15:00-03', 'SHIPPED', 1),
('2026-01-28 14:15:10.128047-03', 'PAID', 1);

--
-- Data for Name: payment; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO payment (moment, order_id) VALUES
('2026-01-28 14:15:10.163816-03', 1);


--
-- Data for Name: tb_category; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO tb_category (name) VALUES
('Electronics'),
('Books'),
('Clothing'),
('Periféricos'),
('Monitores');
--
-- Data for Name: tb_product; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO tb_product (description, img_url, name, price) VALUES
('42 inch smart TV', 'https://www.hisense.com.br/_next/image?url=%2FA4N%2F1.png&w=2048&q=100', 'Smart TV', 2190),
('Apple laptop', 'https://t4.ftcdn.net/jpg/06/01/14/23/360_F_601142328_VnY6DMf1sC0RULodemaCSrvXSlFhO1lA.jpg', 'Macbook Pro', 6250),
('High performance gaming PC', 'https://images.unsplash.com/photo-1587202372775-e229f172b9d7?auto=format&fit=crop&q=80&w=1000', 'PC Gamer', 3200),
('A beginner''s guide to Rails', 'https://images.unsplash.com/photo-1516116216624-53e697fedbea?auto=format&fit=crop&q=80&w=1000', 'Rails for Dummies', 100.99),
('Noise-cancelling headphones', 'https://www.pngall.com/wp-content/uploads/5/Headset-PNG-Image-HD.png', 'Headset', 299.99),
('Camiseta algodão 100%', 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?q=80&w=1000', 'Camiseta Tech', 59.9),
('Moletom com capuz confortável', 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?q=80&w=1000', 'Moletom Gamer', 150),
('Boné aba curva preto', 'https://images.unsplash.com/photo-1588850561407-ed78c282e89b?q=80&w=1000', 'Boné Estilizado', 45),
('A fantasy novel', 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&q=80&w=1000', 'The Lord of the Rings', 90.99);


--
-- Data for Name: tb_order_item; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO tb_order_item (price, quantity, product_id, order_id) VALUES
(90.5, 2, 1, 1),
(6250, 1, 3, 1),
(6250, 2, 3, 2),
(100.99, 2, 5, 3);

--
-- Data for Name: tb_product_category; Type: TABLE DATA; Schema: public; Owner: -
--

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

--
-- PostgreSQL database dump complete
--

