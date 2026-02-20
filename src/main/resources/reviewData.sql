drop table backoffice.reviews;

create table backoffice.reviews
(
    created_at  datetime(6)  null,
    created_by  bigint       null,
    customer    bigint       null,
    deleted_at  datetime(6)  null,
    deleted_by  bigint       null,
    id          bigint auto_increment
        primary key,
    modified_at datetime(6)  null,
    modified_by bigint       null,
    order_id    bigint       null,
    product     bigint       null,
    rating      bigint       null,
    review_qty  bigint       null,
    content     varchar(255) null
);

create index idx_deleted_at
    on backoffice.reviews (deleted_at);


INSERT INTO backoffice.reviews
(created_at, created_by, customer, deleted_at, deleted_by, modified_at, modified_by, order_id, product, rating, review_qty, content)
VALUES
    ('2024-02-05 10:12:34.000000', 1, 3,  NULL, NULL, '2024-02-05 10:12:34.000000', 1,  101, 201, 5, 1, '제품이 정말 만족스럽습니다. 배송도 빠르고 품질도 좋아요!'),
    ('2024-02-10 11:23:45.000000', 2, 7,  NULL, NULL, '2024-02-10 11:23:45.000000', 2,  102, 205, 4, 1, '전반적으로 좋은데 포장이 조금 아쉬웠어요.'),
    ('2024-02-15 12:34:56.000000', 1, 12, NULL, NULL, '2024-02-15 12:34:56.000000', 1,  103, 210, 3, 1, '보통이에요. 기대했던 것보다는 평범했습니다.'),
    ('2024-02-20 13:45:07.000000', 3, 15, NULL, NULL, '2024-02-20 13:45:07.000000', 3,  104, 202, 5, 2, '두 번째 구매인데 역시 실망 없네요. 강력 추천합니다!'),
    ('2024-02-25 14:56:18.000000', 2, 20, NULL, NULL, '2024-02-25 14:56:18.000000', 2,  105, 215, 2, 1, '품질이 사진과 달랐고 사이즈도 맞지 않았어요.'),
    ('2024-03-02 09:07:29.000000', 4, 25, NULL, NULL, '2024-03-02 09:07:29.000000', 4,  106, 208, 4, 1, '가격 대비 훌륭한 품질입니다. 다음에도 구매할 예정이에요.'),
    ('2024-03-08 10:18:40.000000', 1, 30, NULL, NULL, '2024-03-10 15:00:00.000000', 5,  107, 203, 1, 1, '불량품이 왔어요. 교환 처리 요청했습니다.'),
    ('2024-03-15 11:29:51.000000', 5, 35, NULL, NULL, '2024-03-15 11:29:51.000000', 5,  108, 212, 5, 3, '세 번 구매했는데 항상 최고입니다. 재구매 의사 100%!'),
    ('2024-03-20 12:40:02.000000', 3, 40, '2024-04-01 00:00:00.000000', 2, '2024-03-20 12:40:02.000000', 3, 109, 207, 3, 1, '그냥 무난한 제품이에요.'),
    ('2024-03-28 14:51:13.000000', 2, 50, NULL, NULL, '2024-03-28 14:51:13.000000', 2,  110, 220, 4, 1, '배송이 예상보다 빨리 와서 좋았고 제품 상태도 양호합니다.');