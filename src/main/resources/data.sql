-- =============================================
-- backoffice sample data
-- =============================================

-- 0-1. admin (categorys.admin_id, products.admin_id 참조)
INSERT IGNORE INTO backoffice.admin
(approved_at, created_at, created_by, deleted_at, deleted_by, modified_at, modified_by, rejected_at, rejected_reason, email, name, password, phone, role, status)
VALUES
    (NOW(), NOW(), NULL, NULL, NULL, NOW(), NULL, NULL, NULL, 'admin@example.com', '슈퍼관리자', 'hashed_password_1', '010-9999-0001', 'SUPER_ADMIN', 'ACTIVE');

-- 0-2. categorys (admin_id → admin 참조)
INSERT IGNORE INTO backoffice.categorys
(admin_id, created_at, created_by, deleted_at, deleted_by, modified_at, modified_by, code, name, status)
VALUES
    (1, NOW(), 1, NULL, NULL, NOW(), 1, 'FRUIT',  '과일',     'ACTIVE'),
    (1, NOW(), 1, NULL, NULL, NOW(), 1, 'FOOD',   '식품',     'ACTIVE'),
    (1, NOW(), 1, NULL, NULL, NOW(), 1, 'IMPORT', '수입식품',  'ACTIVE');

-- 1. customers
INSERT IGNORE INTO backoffice.customers
(created_at, created_by, deleted_at, deleted_by, modified_at, modified_by, email, name, phone, status)
VALUES
    (NOW(), NULL, NULL, NULL, NOW(), NULL, 'alice@example.com', '김앨리스', '010-1111-0001', 'ACTIVE'),
    (NOW(), NULL, NULL, NULL, NOW(), NULL, 'bob@example.com',   '이밥',     '010-1111-0002', 'ACTIVE'),
    (NOW(), NULL, NULL, NULL, NOW(), NULL, 'carol@example.com', '박캐롤',   '010-1111-0003', 'PENDING'),
    (NOW(), NULL, NULL, NULL, NOW(), NULL, 'dave@example.com',  '최데이브', '010-1111-0004', 'SUSPENDED'),
    (NOW(), NULL, NULL, NULL, NOW(), NULL, 'eve@example.com',   '정이브',   '010-1111-0005', 'INACTIVE');

-- 2. products (admin_id → admin, category_id → categorys 참조)
INSERT IGNORE INTO backoffice.products
(admin_id, category_id, created_at, created_by, deleted_at, deleted_by, modified_at, modified_by, price, total_qty, name, status)
VALUES
    (1, 1, NOW(), 1, NULL, NULL, NOW(), 1, 15000, 100, '유기농 사과',    'AVAILABLE'),
    (1, 1, NOW(), 1, NULL, NULL, NOW(), 1, 25000, 50,  '제주 감귤 세트', 'AVAILABLE'),
    (1, 2, NOW(), 1, NULL, NULL, NOW(), 1, 39000, 200, '국내산 쌀 5kg',  'AVAILABLE'),
    (1, 2, NOW(), 1, NULL, NULL, NOW(), 1,  8000, 30,  '신선 우유 1L',   'SOLD_OUT'),
    (1, 3, NOW(), 1, NULL, NULL, NOW(), 1, 12000, 0,   '수입 바나나',    'DISCONTINUED');

-- 3. orders (customer_id → customers, registration_admin_id → admin 참조)
INSERT IGNORE INTO backoffice.orders
(created_at, created_by, deleted_at, deleted_by, modified_at, modified_by, ordered_at, registration_admin_id, total_amount, total_quantity, version, order_number, cancellation_reason, status, customer_id)
VALUES
    (NOW(), 1, NULL, NULL, NOW(), 1, NOW(), 1, 15000, 1, 1, 'ORD-2025-00001', NULL,        'COMPLETED', 1),
    (NOW(), 1, NULL, NULL, NOW(), 1, NOW(), 1, 75000, 3, 1, 'ORD-2025-00002', NULL,        'SHIPPING',  2),
    (NOW(), 1, NULL, NULL, NOW(), 1, NOW(), 1, 39000, 1, 1, 'ORD-2025-00003', NULL,        'READY',     3),
    (NOW(), 1, NULL, NULL, NOW(), 1, NOW(), 1,  8000, 1, 1, 'ORD-2025-00004', '고객 변심', 'CANCELED',  4),
    (NOW(), 1, NULL, NULL, NOW(), 1, NOW(), 1, 27000, 2, 1, 'ORD-2025-00005', NULL,        'COMPLETED', 5);

-- 4. reviews (customer_id → customers, order_id → orders, product_id → products 참조)
INSERT IGNORE INTO backoffice.reviews
(created_at, created_by, deleted_at, deleted_by, modified_at, modified_by, customer_id, order_id, product_id, rating, content)
VALUES
    (NOW(), 1, NULL, NULL, NOW(), 1, 1, 1, 1, 5,  '정말 신선하고 맛있어요!'),
    (NOW(), 1, NULL, NULL, NOW(), 1, 2, 2, 2, 4,  '달콤한 감귤이 최고입니다.'),
    (NOW(), 1, NULL, NULL, NOW(), 1, 3, 3, 3, 5,  '밥맛이 확실히 달라요. 재구매 예정!'),
    (NOW(), 1, NULL, NULL, NOW(), 1, 4, 4, 4, 2,  '배송이 늦어서 아쉬웠어요.'),
    (NOW(), 1, NULL, NULL, NOW(), 1, 5, 5, 1, 4,  '품질은 좋은데 가격이 살짝 비싸요.');

-- 5. order items (order_id → orders, product_id → products 참조)
INSERT IGNORE INTO backoffice.order_items
(order_id, order_price, product_id, quantity, total_amount)
VALUES
    (1, 15000, 1, 1, 15000),
    (2, 25000,2, 3, 75000),
    (3, 39000, 3, 1, 39000),
    (4, 8000, 4, 1, 8000),
    (5, 15000, 1, 1, 15000),
    (5, 12000,5, 1, 12000);