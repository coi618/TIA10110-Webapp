# TIA101G2-product DB 
CREATE DATABASE G2_product; # 建立
-- DROP DATABASE G2_product; # 刪除
USE G2_product; # 指定使用DB

# --------- 建立 table: product_order > product > product_detail > product_img ---------
# product_order
CREATE TABLE product_order ( # CREATE product_order TABLE
	prod_ord_id int AUTO_INCREMENT PRIMARY KEY comment '商品訂單ID',
	mem_id int comment '會員的ID', # NOT NULL 
	est_time datetime comment '訂單成立時間',
	ord_status int comment '訂單狀態 - 0:未完成, 1:已完成',
	total int comment '總額',
	recipient varchar(255) comment '收件人姓名',
	rec_addr varchar(255) comment '收件人地址'
);
# Increment with default value XXX
ALTER TABLE product_order AUTO_INCREMENT=11000001;

# product
CREATE TABLE product ( # CREATE my_detail TABLE
	prod_id int AUTO_INCREMENT PRIMARY KEY comment '商品ID',
	prod_name varchar(255) comment '商品名稱',
	prod_intro text comment '商品介紹',
	prod_price int comment '商品價格', 
	release_date date comment '上架日期',
	remove_date date comment '下架日期',
	sales_status int comment '銷售狀態, 0:銷售中, 1:停售',
	time_limit_prod bool comment '限時商品'
);
# Increment with default value XXX
ALTER TABLE product AUTO_INCREMENT=13000001;

# product_detail
CREATE TABLE product_detail (
	prod_detail_id int AUTO_INCREMENT PRIMARY KEY comment '明細ID',
	prod_ord_id int comment '商品訂單ID', # NOT NULL 
	prod_id int comment '商品ID', # NOT NULL 
	unit_price int comment '單價',
	prod_count int comment '數量', 
	prod_sum int comment '小計',
	FOREIGN KEY (prod_ord_id) REFERENCES product_order(prod_ord_id),
	FOREIGN KEY (prod_id) REFERENCES product(prod_id)
);
# Increment with default value XXX
ALTER TABLE product_detail AUTO_INCREMENT=12000001;

# product_img
CREATE TABLE product_img ( # CREATE my_detail TABLE
	prod_img_id int AUTO_INCREMENT PRIMARY KEY comment '圖片ID',
	prod_id int comment '商品ID', #  NOT NULL
	img_src varchar(255) comment '圖片路徑', # 如何解決檔名重複?
	img_name varchar(255) comment '圖片名稱', 
	FOREIGN KEY (prod_id) REFERENCES product(prod_id) 
);
# Increment with default value XXX
ALTER TABLE product_img AUTO_INCREMENT=14000001;

# --------- 加入資料: product > product_img > product_order > product_detail ---------
# product
INSERT INTO product (prod_name, prod_intro, prod_price, release_date, remove_date, sales_status, time_limit_prod)
VALUES 
	('功夫熊猫資料夾', '功夫熊猫的資料夾', 100, '2024-03-27', null, 0, false),
	('沙丘保溫瓶', '沙丘保的溫瓶', 1000, '2024-03-27', null, 0, false),
	('千尋卡片', '千尋的卡片', 250, '2002-01-01', null, 1, false),
	('金鋼玩偶', '金鋼的玩偶', 1000, '2024-03-27', '2024-06-27', 0, true),
	('怪盜基德明信片', '怪盜基德的明信片', 50, '2024-03-29', null, 0, false),
	-- 5
	('科南T-shirt', '科南的T-shirt', 500, '2024-03-29', null, 0, false),
	('《NEXT LEVEL》加油棒', '《NEXT LEVEL》的加油棒', 1000, '2024-04-09', '2024-04-30', 1, true),
	('蜘蛛人背包', '蜘蛛的人背包', 1500, '2023-12-25', null, 0, false),
	('鏡之孤城BD', '鏡之孤城的BD', 1800, '2023-12-20', null, 0, false),
	('哥吉拉玩偶', '哥吉拉的玩偶', 1000, '2024-03-27', '2024-06-27', 0, true)
	-- 10
	;

# product_img
INSERT INTO product_img (prod_id, img_src, img_name)
VALUES 
	(13000001, './prod_img/功夫熊猫資料夾_1.jpg', '功夫熊猫資料夾_1.jpg'),
	(13000001, './prod_img/功夫熊猫資料夾_2.jpg', '功夫熊猫資料夾_2.jpg'),
	(13000001, './prod_img/功夫熊猫資料夾_3.jpg', '功夫熊猫資料夾_3.jpg'),
	(13000002, './prod_img/沙丘保溫瓶_1.jpg', '沙丘保溫瓶_1.jpg'),
	(13000002, './prod_img/沙丘保溫瓶_2.jpg', '沙丘保溫瓶_2.jpg'),
	-- 5
	(13000003, './prod_img/千尋卡片_1.jpg', '千尋卡片_1.jpg'),
	(13000004, './prod_img/金鋼玩偶_1.jpg', '金鋼玩偶_1.jpg'),
	(13000004, './prod_img/金鋼玩偶_2.jpg', '金鋼玩偶_2.jpg'),
	(13000004, './prod_img/金鋼玩偶_3.jpg', '金鋼玩偶_3.jpg'),
	(13000005, './prod_img/怪盜基德明信片_1.jpg', '怪盜基德明信片_1.jpg'),
	-- 10
	(13000006, './prod_img/科南T-shirt_1.jpg', '科南T-shirt_1.jpg'),
	(13000007, './prod_img/《NEXT LEVEL》加油棒_1.jpg', '《NEXT LEVEL》加油棒_1.jpg'),
	(13000007, './prod_img/《NEXT LEVEL》加油棒_2.jpg', '《NEXT LEVEL》加油棒_2.jpg'),
	(13000008, './prod_img/蜘蛛人背包_1.jpg', '蜘蛛人背包_1.jpg'),
	(13000008, './prod_img/蜘蛛人背包_2.jpg', '蜘蛛人背包_2.jpg'),
	-- 15
	(13000009, './prod_img/鏡之孤城BD_1.jpg', '鏡之孤城BD_1.jpg'),
	(13000010, './prod_img/哥吉拉玩偶_1.jpg', '哥吉拉玩偶_1.jpg'),
	(13000010, './prod_img/哥吉拉玩偶_2.jpg', '哥吉拉玩偶_2.jpg'),
	(13000010, './prod_img/哥吉拉玩偶_3.jpg', '哥吉拉玩偶_3.jpg')
	;

# product_order
INSERT INTO product_order (mem_id, est_time, ord_status, total, recipient, rec_addr)
VALUES 
	(1, '2024-03-27 10:59:15', 1, 1300, '王小明', '104台北市中山區南京東路三段219號5樓'),
	(1, '2024-03-27 12:33:00', 0,  200, '王小明', '104台北市中山區南京東路三段219號5樓'),
	(2, '2024-03-27 15:07:00', 0, 1100, '李四端', '104台北市中山區南京東路三段9號'),
	(2, '2024-03-31 12:00:21', 1, 3500, '蔡元熙', '高雄市中央路12號'),
	(3, '2024-04-05 15:26:59', 1,  650, '陳琳瑤', '臺中市三榮五路5號'),
	-- 5
	(3, '2024-04-11 12:00:01', 0, 1800, '郭駱騏', '雲林縣南安街96號')
	;

# product_detail
INSERT INTO product_detail (prod_ord_id, prod_id, unit_price, prod_count, prod_sum)
VALUES 
	(11000001, 13000001,  100, 3,  300),
	(11000001, 13000004, 1000, 1, 1000),
	(11000003, 13000001,  100, 1,  100),
	(11000003, 13000002, 1000, 1, 1000),
	(11000002, 13000001,  100, 2,  200),
	-- 5
	(11000004, 13000004, 1000, 1, 1000),
	(11000004, 13000010, 1000, 1, 1000),
	(11000004, 13000008, 1500, 1, 1500),
	(11000005, 13000005,   50, 3,  150),
	(11000005, 13000006,  500, 1,  500),
	-- 10
	(11000006, 13000009, 1800, 1, 1800)
	;

# --------- 查看表格 ---------
DESC product_order;
DESC product_detail;
DESC product;
DESC product_img;

SELECT * FROM product_order;
SELECT * FROM product_detail;
SELECT * FROM product;
SELECT * FROM product_img;
# --------- 清空表格: product_img > product_detail > product > product_order ---------
-- DELETE FROM product_order;
-- DELETE FROM product_detail;
-- DELETE FROM product;
-- DELETE FROM product_img;
-- # --------- 刪除表格: product_img > product_detail > product > product_order ---------
-- DROP TABLE IF EXISTS product_order;
-- DROP TABLE IF EXISTS product_detail;
-- DROP TABLE IF EXISTS product;
-- DROP TABLE IF EXISTS product_img;









