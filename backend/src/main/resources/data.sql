--
-- Data for Name: tb_user; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_user VALUES (1, 'john.doe@email.com', 'John Doe', '$2a$10$QsOFlxuIlaK7wL2Jgh3zKOvIvdoTrdn23eNl5G02Cbp5YZT7.fmXG', '1234567890', 'ADMIN');
INSERT INTO public.tb_user VALUES (2, 'jane.smith@email.com', 'Jane Smith', '$2a$10$sA63g7KW00eIFAK6o9y6Ruvl/L8azPoL5ypkw5OQQktd5x0eSjH26', '0987654321', 'USER');

--
-- Data for Name: tb_order; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_order VALUES (2, '2024-06-02 08:30:00-03', 'WAITING_PAYMENT', 2);
INSERT INTO public.tb_order VALUES (3, '2024-06-03 11:15:00-03', 'SHIPPED', 1);
INSERT INTO public.tb_order VALUES (1, '2026-01-28 14:15:10.128047-03', 'PAID', 1);


--
-- Data for Name: payment; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.payment VALUES ('2026-01-28 14:15:10.163816-03', 1);


--
-- Data for Name: tb_category; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_category VALUES (1, 'Electronics');
INSERT INTO public.tb_category VALUES (2, 'Books');
INSERT INTO public.tb_category VALUES (3, 'Clothing');
INSERT INTO public.tb_category VALUES (52, 'Periféricos');
INSERT INTO public.tb_category VALUES (53, 'Monitores');


--
-- Data for Name: tb_product; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_product VALUES (2, '42 inch smart TV', 'https://www.hisense.com.br/_next/image?url=%2FA4N%2F1.png&w=2048&q=100', 'Smart TV', 2190);
INSERT INTO public.tb_product VALUES (3, 'Apple laptop', 'https://t4.ftcdn.net/jpg/06/01/14/23/360_F_601142328_VnY6DMf1sC0RULodemaCSrvXSlFhO1lA.jpg', 'Macbook Pro', 6250);
INSERT INTO public.tb_product VALUES (4, 'High performance gaming PC', 'https://images.unsplash.com/photo-1587202372775-e229f172b9d7?auto=format&fit=crop&q=80&w=1000', 'PC Gamer', 3200);
INSERT INTO public.tb_product VALUES (5, 'A beginner''s guide to Rails', 'https://images.unsplash.com/photo-1516116216624-53e697fedbea?auto=format&fit=crop&q=80&w=1000', 'Rails for Dummies', 100.99);
INSERT INTO public.tb_product VALUES (6, 'Noise-cancelling headphones', 'https://www.pngall.com/wp-content/uploads/5/Headset-PNG-Image-HD.png', 'Headset', 299.99);
INSERT INTO public.tb_product VALUES (7, 'Camiseta algodão 100%', 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?q=80&w=1000', 'Camiseta Tech', 59.9);
INSERT INTO public.tb_product VALUES (8, 'Moletom com capuz confortável', 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?q=80&w=1000', 'Moletom Gamer', 150);
INSERT INTO public.tb_product VALUES (9, 'Boné aba curva preto', 'https://images.unsplash.com/photo-1588850561407-ed78c282e89b?q=80&w=1000', 'Boné Estilizado', 45);
INSERT INTO public.tb_product VALUES (1, 'A fantasy novel', 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&q=80&w=1000', 'The Lord of the Rings', 90.99);


--
-- Data for Name: tb_order_item; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_order_item VALUES (90.5, 2, 1, 1);
INSERT INTO public.tb_order_item VALUES (6250, 1, 3, 1);
INSERT INTO public.tb_order_item VALUES (6250, 2, 3, 2);
INSERT INTO public.tb_order_item VALUES (100.99, 2, 5, 3);


--
-- Data for Name: tb_product_category; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tb_product_category VALUES (1, 2);
INSERT INTO public.tb_product_category VALUES (2, 1);
INSERT INTO public.tb_product_category VALUES (3, 1);
INSERT INTO public.tb_product_category VALUES (4, 1);
INSERT INTO public.tb_product_category VALUES (5, 2);
INSERT INTO public.tb_product_category VALUES (7, 3);
INSERT INTO public.tb_product_category VALUES (8, 3);
INSERT INTO public.tb_product_category VALUES (9, 3);
INSERT INTO public.tb_product_category VALUES (6, 52);
INSERT INTO public.tb_product_category VALUES (2, 53);


--
-- Name: tb_category_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT setval('tb_category_seq', 101, true);


--
-- Name: tb_order_seq; Type: SEQUENCE SET; Schema: public; Owner: -


SELECT setval('tb_order_seq', 101, true);


--
-- Name: tb_product_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT setval('tb_product_seq', 51, true);


--
-- Name: tb_user_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT setval('tb_user_seq', 101, true);


--
-- PostgreSQL database dump complete
--

