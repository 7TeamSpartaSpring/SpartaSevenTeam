-- =============================================
-- backoffice 데이터 초기화
-- =============================================

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE backoffice.reviews;
TRUNCATE TABLE backoffice.orders;
TRUNCATE TABLE backoffice.products;
TRUNCATE TABLE backoffice.customers;
TRUNCATE TABLE backoffice.categorys;
TRUNCATE TABLE backoffice.admin;
TRUNCATE TABLE backoffice.order_items;

SET FOREIGN_KEY_CHECKS = 1;