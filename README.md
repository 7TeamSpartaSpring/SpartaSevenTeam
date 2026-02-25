# 🏢 Sparta Seven Team - E-Commerce BackOffice System

> Spring Boot 기반 관리자 전용 이커머스 백오피스 시스템  
> JWT + RBAC + QueryDSL + Soft Delete 기반 실무형 설계 팀 프로젝트

---

# 📌 프로젝트 개요

본 프로젝트는 이커머스 서비스의 관리자 전용 백오피스 시스템입니다.

관리자는 다음 도메인을 관리할 수 있습니다.

- 👤 관리자 관리
- 🔐 JWT 기반 인증
- 👥 고객 관리
- 📦 상품 / SKU / 재고 관리
- 🛒 주문 관리 (Order + OrderItem 구조)
- ⭐ 리뷰 관리
- 📊 통계 대시보드
- 🔎 검색 / 정렬 / 페이징
- 🗑 Soft Delete 전략

본 프로젝트는 단순 CRUD가 아닌 **실무 구조를 반영한 설계 기반 프로젝트**입니다.

![diagram](https://github.com/user-attachments/assets/c1a2a591-7611-461a-a3d2-f72aff505ac1)


# 📌 API 목록 테이블 (도메인별 전체 정리)

<details>
<summary>👉 클릭해서 펼치기</summary>

<br>

---

# 🔐 AUTH 도메인

| Method | Endpoint | 설명 | 인증 필요 | 권한 |
|--------|----------|------|------------|------|
| POST | /api/auth/v1/admin/signup | 관리자 회원가입 신청 | ❌ | - |
| POST | /api/auth/v1/admin/login | 관리자 로그인 (JWT 발급) | ❌ | - |
| POST | /api/auth/v1/admin/logout | 관리자 로그아웃 | ✅ | 전체 |

---

# 👤 ADMIN 도메인

| Method | Endpoint | 설명 | 인증 필요 | 권한 |
|--------|----------|------|------------|------|
| GET | /api/admin/v1/admin/me | 내 정보 조회 | ✅ | 전체 |
| PUT | /api/admin/v1/admin/me | 내 정보 수정 | ✅ | 전체 |
| PATCH | /api/admin/v1/admin/me/password | 비밀번호 변경 | ✅ | 전체 |
| GET | /api/admin/v1/admin | 관리자 목록 조회 (검색/페이징) | ✅ | SUPER_ADMIN |
| GET | /api/admin/v1/admin/{adminId} | 관리자 상세 조회 | ✅ | SUPER_ADMIN |
| PATCH | /api/admin/v1/admin/{adminId}/approve | 관리자 승인 | ✅ | SUPER_ADMIN |
| PATCH | /api/admin/v1/admin/{adminId}/reject | 관리자 거부 | ✅ | SUPER_ADMIN |
| PATCH | /api/admin/v1/admin/{adminId}/status | 관리자 상태 변경 | ✅ | SUPER_ADMIN |
| PATCH | /api/admin/v1/admin/{adminId}/role | 관리자 역할 변경 | ✅ | SUPER_ADMIN |
| PUT | /api/admin/v1/admin/{adminId} | 관리자 정보 수정 | ✅ | SUPER_ADMIN |
| DELETE | /api/admin/v1/admin/{adminId} | 관리자 삭제 (Soft Delete) | ✅ | SUPER_ADMIN |

---

# 👥 CUSTOMER 도메인

| Method | Endpoint | 설명 | 인증 필요 | 권한 |
|--------|----------|------|------------|------|
| GET | /api/customer/v1/customers | 고객 목록 조회 (검색/페이징/집계) | ✅ | 전체 |
| GET | /api/customer/v1/customers/{customerId} | 고객 상세 조회 | ✅ | 전체 |
| PUT | /api/customer/v1/customers/{customerId} | 고객 정보 수정 | ✅ | 전체 |
| PATCH | /api/customer/v1/customers/{customerId} | 고객 상태 변경 | ✅ | 전체 |
| DELETE | /api/customer/v1/customers/{customerId} | 고객 삭제 (Soft Delete) | ✅ | 전체 |

---

# 📂 CATEGORY 도메인

| Method | Endpoint | 설명 | 인증 필요 | 권한 |
|--------|----------|------|------------|------|
| POST | /api/product/v1/categorys | 카테고리 생성 | ✅ | OPERATION_ADMIN 이상 |
| GET | /api/product/v1/categorys | 카테고리 목록 조회 | ✅ | 전체 |
| DELETE | /api/product/v1/categorys/{id} | 카테고리 삭제 (Soft Delete) | ✅ | OPERATION_ADMIN 이상 |

---

# 🛍 PRODUCT 도메인

| Method | Endpoint | 설명 | 인증 필요 | 권한 |
|--------|----------|------|------------|------|
| POST | /api/product/v1/products | 상품 생성 | ✅ | OPERATION_ADMIN 이상 |
| GET | /api/product/v1/products | 상품 목록 조회 (검색/페이징) | ✅ | 전체 |
| GET | /api/product/v1/products/{productId} | 상품 상세 조회 | ✅ | 전체 |
| PUT | /api/product/v1/products/{productId} | 상품 수정 | ✅ | OPERATION_ADMIN 이상 |
| PATCH | /api/product/v1/products/{productId}/status | 상품 상태 변경 | ✅ | OPERATION_ADMIN 이상 |
| DELETE | /api/product/v1/products/{productId} | 상품 삭제 (Soft Delete) | ✅ | OPERATION_ADMIN 이상 |

---

# 📦 SKU 도메인

| Method | Endpoint | 설명 | 인증 필요 | 권한 |
|--------|----------|------|------------|------|
| PATCH | /api/product/v1/skus/{skuId}/qty | SKU 재고 수량 변경 | ✅ | OPERATION_ADMIN 이상 |

---

# 🧾 ORDER 도메인

| Method | Endpoint | 설명 | 인증 필요 | 권한 |
|--------|----------|------|------------|------|
| POST | /api/order/v1/orders | 주문 생성 (다품목) | ✅ | CS_ADMIN 이상 |
| POST | /api/order/v1/orders/single | 주문 생성 (단일상품) | ✅ | CS_ADMIN 이상 |
| GET | /api/order/v1/orders | 주문 목록 조회 (검색/정렬/페이징) | ✅ | 전체 |
| GET | /api/order/v1/orders/single | 단일상품 주문 목록 조회 | ✅ | 전체 |
| GET | /api/order/v1/orders/{orderId} | 주문 상세 조회 | ✅ | 전체 |
| PATCH | /api/order/v1/orders/{orderId}/status | 주문 상태 변경 | ✅ | OPERATION_ADMIN 이상 |
| PATCH | /api/order/v1/orders/{orderId}/cancel | 주문 취소 | ✅ | CS_ADMIN 이상 |

---

# ⭐ REVIEW 도메인

| Method | Endpoint | 설명 | 인증 필요 | 권한 |
|--------|----------|------|------------|------|
| GET | /api/review/v1/reviews | 리뷰 목록 조회 (검색/페이징) | ✅ | 전체 |
| GET | /api/review/v1/reviews/{id} | 리뷰 상세 조회 | ✅ | 전체 |
| PUT | /api/review/v1/reviews/{id} | 리뷰 수정 | ✅ | OPERATION_ADMIN 이상 |
| DELETE | /api/review/v1/reviews/{id} | 리뷰 삭제 (Soft Delete) | ✅ | OPERATION_ADMIN 이상 |
| PUT | /api/review/v1/reviews/{id}/restore | 리뷰 복구 | ✅ | OPERATION_ADMIN 이상 |

---

# 📊 DASHBOARD 도메인

| Method | Endpoint | 설명 | 인증 필요 | 권한 |
|--------|----------|------|------------|------|
| GET | /api/dashboard/v1/dashboards | 대시보드 통계 조회 | ✅ | 전체 |

---

# 📌 권한(Role) 정의

| Role | 설명 |
|------|------|
| SUPER_ADMIN | 최고 관리자 (승인/삭제/권한변경 가능) |
| OPERATION_ADMIN | 상품/재고/주문 운영 관리 |
| CS_ADMIN | 주문 상태 변경/취소 관리 |

---

# 📌 상태값(Enum) 정의

## AdminStatus
- PENDING
- ACTIVE
- REJECTED
- SUSPENDED

## CustomerStatus
- ACTIVE
- SUSPENDED
- WITHDRAWN

## ProductStatus
- ON_SALE
- OUT_OF_STOCK
- DISCONTINUED

## OrderStatus
- READY
- SHIPPING
- COMPLETED
- CANCELED

</details>

# 📌 API 명세서

<details>
<summary>👉 클릭해서 펼치기</summary>

<br>



````
# 📌 API 명세서 (SevenTeam Backoffice) — 최신화

> **Base URL:** `/`
> **API Prefix:** `/api`
> **Version:** `v1` (각 엔드포인트에 `/v1/...` 포함)
> **Response Wrapper:** `ApiResponse<T>` (성공/실패 공통 포맷)

---

## ✅ 공통 규칙

### 1) 인증/인가

- 인증 방식: **JWT (Bearer Token)**
- 보호된 API 호출 시 헤더 포함

```http
Authorization: Bearer {accessToken}
````

-   권한(Role) 제어는 `@PreAuthorize` 기반 (예: `SUPER_ADMIN`, `OPERATION_ADMIN`, `CS_ADMIN`)
-   `회원가입/로그인`은 인증 없이 호출

---

### 2) 공통 성공 응답 형식

```
{
  "success": true,
  "data": {},
  "error": null
}
```

### 3) 공통 실패 응답 형식

```
{
  "success": false,
  "data": null,
  "error": {
    "timestamp": "2026-02-19T10:00:00",
    "status": 400,
    "error": "Bad Request",
    "code": "VALIDATION_ERROR",
    "message": "요청값이 올바르지 않습니다.",
    "path": "/api/order/v1/orders"
  }
}
```

---

### 4) 페이징 응답 형식 (`PageResponse<T>`)

> 프로젝트 공통 페이징 래퍼를 사용합니다.

```
{
  "content": [],
  "page": 0,
  "size": 10,
  "totalElements": 0,
  "totalPages": 0
}
```

---

# 0\. AUTH (관리자 회원가입/로그인)

## 0-1) 관리자 회원가입 신청

### Description

-   관리자가 백오피스 계정을 **신청**합니다. (승인 흐름은 Admin API에서 처리)

### Endpoint

```
POST /api/auth/v1/admin/signup
```

### Header

-   없음

### Request Body

```
{
  "email": "admin@test.com",
  "password": "12345678",
  "name": "홍길동",
  "phone": "010-1234-5678",
  "role": "CS_ADMIN"
}
```

### Response Field (`200/201`)

```
{
  "success": true,
  "data": {
    "id": 1,
    "email": "admin@test.com",
    "name": "홍길동",
    "role": "CS_ADMIN",
    "status": "PENDING",
    "createdAt": "2026-02-19T10:00:00",
    "modifiedAt": "2026-02-19T10:00:00"
  },
  "error": null
}
```

### Response Code

-   `201 CREATED` 회원가입 신청 성공
-   `400 BAD REQUEST` Validation 실패

### ERROR

-   `VALIDATION_ERROR` (email 형식/비밀번호 길이/phone 패턴 등)

---

## 0-2) 관리자 로그인

### Description

-   이메일/비밀번호 검증 후 **JWT 발급**

### Endpoint

```
POST /api/auth/v1/admin/login
```

### Header

-   없음

### Request Body

```
{
  "email": "admin@test.com",
  "password": "12345678"
}
```

### Response Field

```
{
  "success": true,
  "data": {
    "jwt": "eyJhbGciOiJIUzI1NiJ9..."
  },
  "error": null
}
```

### Response Code

-   `200 OK` 로그인 성공
-   `401 UNAUTHORIZED` 로그인 실패

### ERROR

-   `AUTH_FAILED` (이메일/비밀번호 불일치 등)

---

## 0-3) 관리자 로그아웃

### Description

-   서버가 상태를 저장하지 않는 JWT 구조라면 “클라이언트 토큰 폐기” 개념 (서버 블랙리스트 등을 쓰지 않으면 무효화는 클라 책임)

### Endpoint

```
POST /api/auth/v1/admin/logout
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

-   없음

### Response Field

```
{
  "success": true,
  "data": "logout",
  "error": null
}
```

### Response Code

-   `200 OK`

---

# 1\. ADMIN (관리자/슈퍼관리자)

## 1-1) 내 프로필 조회

### Endpoint

```
GET /api/admin/v1/admin/me
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response Field

```
{
  "success": true,
  "data": {
    "id": 1,
    "email": "admin@test.com",
    "name": "홍길동",
    "phone": "010-1234-5678",
    "role": "CS_ADMIN",
    "status": "ACTIVE",
    "createdAt": "2026-02-19T10:00:00",
    "modifiedAt": "2026-02-19T10:30:00",
    "approvedAt": "2026-02-19T11:00:00",
    "rejectedAt": null,
    "rejectedReason": null
  },
  "error": null
}
```

### Response Code

-   `200 OK`
-   `401 UNAUTHORIZED`

---

## 1-2) 내 프로필 수정

### Endpoint

```
PUT /api/admin/v1/admin/me
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "name": "홍길동",
  "email": "admin@test.com",
  "phone": "010-1234-5678"
}
```

### Response Field

-   `AdminResponse` 동일

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `401 UNAUTHORIZED`

---

## 1-3) 내 비밀번호 변경

### Endpoint

```
PATCH /api/admin/v1/admin/me/password
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "currentPassword": "12345678",
  "newPassword": "87654321"
}
```

### Response Field

```
{
  "success": true,
  "data": "비밀번호 변경 완료",
  "error": null
}
```

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `401 UNAUTHORIZED`

---

## 1-4) 관리자 승인 (SUPER\_ADMIN)

### Endpoint

```
PATCH /api/admin/v1/admin/{adminId}/approve
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response Field

```
{
  "success": true,
  "data": "승인 완료",
  "error": null
}
```

### Response Code

-   `200 OK`
-   `403 FORBIDDEN` (SUPER\_ADMIN 아님)
-   `404 NOT FOUND` (adminId 없음)

---

## 1-5) 관리자 거부 (SUPER\_ADMIN)

### Endpoint

```
PATCH /api/admin/v1/admin/{adminId}/reject
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "reason": "승인 요건 미충족"
}
```

### Response Field

```
{
  "success": true,
  "data": "거부 완료",
  "error": null
}
```

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `403 FORBIDDEN`
-   `404 NOT FOUND`

---

## 1-6) 관리자 목록 조회 (SUPER\_ADMIN) — 페이징

### Endpoint

```
GET /api/admin/v1/admin
```

### Header

```
Authorization: Bearer {accessToken}
```

### Query Parameter

| name | type | required | desc |
| --- | --- | --: | --- |
| page | int | 선택 | (Spring 기본 pageable) |
| size | int | 선택 |   |
| sort | string | 선택 |   |
| keyword | string | 선택 | 검색어 |
| role | string | 선택 | `SUPER_ADMIN`/`OPERATION_ADMIN`/`CS_ADMIN` 등 |
| status | string | 선택 | `ACTIVE`/`PENDING`/`REJECTED`/`SUSPENDED` 등 |

### Response Field

```
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "email": "admin@test.com",
        "name": "홍길동",
        "phone": "010-1234-5678",
        "role": "CS_ADMIN",
        "status": "ACTIVE",
        "createdAt": "2026-02-19T10:00:00",
        "modifiedAt": "2026-02-19T10:30:00",
        "approvedAt": "2026-02-19T11:00:00",
        "rejectedAt": null,
        "rejectedReason": null
      }
    ],
    "page": 0,
    "size": 10,
    "totalElements": 1,
    "totalPages": 1
  },
  "error": null
}
```

### Response Code

-   `200 OK`
-   `403 FORBIDDEN`

---

## 1-7) 관리자 상세 조회 (SUPER\_ADMIN)

### Endpoint

```
GET /api/admin/v1/admin/{adminId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response Field

-   `AdminResponse` 동일

### Response Code

-   `200 OK`
-   `403 FORBIDDEN`
-   `404 NOT FOUND`

---

## 1-8) 관리자 정보 수정 (SUPER\_ADMIN)

### Endpoint

```
PUT /api/admin/v1/admin/{adminId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "name": "수정이름",
  "email": "updated@test.com",
  "phone": "010-9999-8888"
}
```

### Response Field

-   `AdminResponse` 동일

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `403 FORBIDDEN`
-   `404 NOT FOUND`

---

## 1-9) 관리자 상태 변경 (SUPER\_ADMIN)

### Endpoint

```
PATCH /api/admin/v1/admin/{adminId}/status
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "status": "SUSPENDED"
}
```

### Response Field

```
{
  "success": true,
  "data": "상태 변경 완료",
  "error": null
}
```

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `403 FORBIDDEN`
-   `404 NOT FOUND`

---

## 1-10) 관리자 역할 변경 (SUPER\_ADMIN)

### Endpoint

```
PATCH /api/admin/v1/admin/{adminId}/role
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "role": "OPERATION_ADMIN"
}
```

### Response Field

```
{
  "success": true,
  "data": "역할 변경 완료",
  "error": null
}
```

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `403 FORBIDDEN`
-   `404 NOT FOUND`

---

## 1-11) 관리자 삭제 (SUPER\_ADMIN)

### Description

-   프로젝트는 **Soft Delete 전략**(deleted flag)을 사용하므로, “삭제 API”는 논리삭제(삭제 처리)입니다.

### Endpoint

```
DELETE /api/admin/v1/admin/{adminId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response Field

```
{
  "success": true,
  "data": "삭제 완료",
  "error": null
}
```

### Response Code

-   `200 OK`
-   `403 FORBIDDEN`
-   `404 NOT FOUND`

---

# 2\. CUSTOMER (고객 관리)

## 2-1) 고객 리스트 조회 — 페이징/검색/필터

### Endpoint

```
GET /api/customer/v1/customers
```

### Header

```
Authorization: Bearer {accessToken}
```

### Query Parameter

| name | type | required | desc |
| --- | --- | --: | --- |
| page | int | 선택 | 기본값 존재 |
| size | int | 선택 | 기본값 존재 |
| sort | string | 선택 |   |
| keyword | string | 선택 | 이름/이메일 검색용 |
| stat | string | 선택 | 고객 상태 필터 (예: `ACTIVE`) |

### Response Field (집계 포함)

```
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "name": "고객1",
        "email": "user1@test.com",
        "phone": "010-1111-2222",
        "status": "ACTIVE",
        "totalOrderCount": 3,
        "totalOrderItemCount": 5,
        "totalPayment": 150000,
        "createdAt": "2026-02-19T10:00:00"
      }
    ],
    "page": 0,
    "size": 10,
    "totalElements": 1,
    "totalPages": 1
  },
  "error": null
}
```

### Response Code

-   `200 OK`
-   `401 UNAUTHORIZED`
-   `403 FORBIDDEN` (권한 정책에 따라)

---

## 2-2) 고객 상세 조회 (집계 포함)

### Endpoint

```
GET /api/customer/v1/customers/{customerId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response Field

```
{
  "success": true,
  "data": {
    "id": 1,
    "name": "고객1",
    "email": "user1@test.com",
    "phone": "010-1111-2222",
    "status": "ACTIVE",
    "totalOrderCount": 3,
    "totalOrderItemCount": 5,
    "totalPayment": 150000,
    "createdAt": "2026-02-19T10:00:00"
  },
  "error": null
}
```

### Response Code

-   `200 OK`
-   `404 NOT FOUND`

---

## 2-3) 고객 정보 수정

### Endpoint

```
PUT /api/customer/v1/customers/{customerId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "name": "수정이름",
  "email": "updated@test.com",
  "phone": "010-2222-3333"
}
```

### Response Field (`CustomerResponse`)

```
{
  "success": true,
  "data": {
    "id": 1,
    "name": "수정이름",
    "email": "updated@test.com",
    "phone": "010-2222-3333",
    "status": "ACTIVE",
    "createdAt": "2026-02-19T10:00:00"
  },
  "error": null
}
```

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `404 NOT FOUND`

---

## 2-4) 고객 상태 변경

### Endpoint

```
PATCH /api/customer/v1/customers/{customerId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "status": "SUSPENDED"
}
```

### Response Field

-   `CustomerResponse` 또는 상태 변경 결과 메시지(서비스 정책에 맞춤)

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `404 NOT FOUND`

---

## 2-5) 고객 삭제 (Soft Delete)

### Endpoint

```
DELETE /api/customer/v1/customers/{customerId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response

-   응답 바디 없음

### Response Code

-   `204 NO CONTENT`
-   `404 NOT FOUND`

---

# 3\. PRODUCT - CATEGORY (카테고리)

## 3-1) 카테고리 생성

### Endpoint

```
POST /api/product/v1/categorys
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "code": "ELEC",
  "name": "전자기기",
  "status": "ACTIVE"
}
```

### Response Field (`CategoryResponse`)

```
{
  "success": true,
  "data": {
    "id": 1,
    "code": "ELEC",
    "name": "전자기기",
    "status": "ACTIVE",
    "createdBy": 1,
    "createdAt": "2026-02-19T10:00:00",
    "modifiedBy": 1,
    "modifiedAt": "2026-02-19T10:00:00"
  },
  "error": null
}
```

### Response Code

-   `201 CREATED`
-   `400 BAD REQUEST`

---

## 3-2) 카테고리 전체 조회

### Endpoint

```
GET /api/product/v1/categorys
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response Field

```
{
  "success": true,
  "data": [
    {
      "id": 1,
      "code": "ELEC",
      "name": "전자기기",
      "status": "ACTIVE",
      "createdBy": 1,
      "createdAt": "2026-02-19T10:00:00",
      "modifiedBy": 1,
      "modifiedAt": "2026-02-19T10:00:00"
    }
  ],
  "error": null
}
```

### Response Code

-   `200 OK`

---

## 3-3) 카테고리 삭제 (Soft Delete)

### Endpoint

```
DELETE /api/product/v1/categorys/{id}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response

-   응답 바디 없음

### Response Code

-   `204 NO CONTENT`
-   `404 NOT FOUND`

---

# 4\. PRODUCT (상품)

## 4-1) 상품 생성

### Endpoint

```
POST /api/product/v1/products
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "name": "무선 이어폰",
  "categoryId": 1,
  "price": 129000,
  "qty": 50,
  "status": "ON_SALE"
}
```

### Response Field (`ProductResponse`)

```
{
  "success": true,
  "data": {
    "id": 10,
    "name": "무선 이어폰",
    "categoryName": "전자기기",
    "price": 129000,
    "qty": 50,
    "status": "ON_SALE",
    "adminEmail": "admin@test.com",
    "reviews": null,
    "createdAt": "2026-02-19T10:00:00"
  },
  "error": null
}
```

### Response Code

-   `201 CREATED`
-   `400 BAD REQUEST`

---

## 4-2) 상품 리스트 조회 — 검색/필터/페이징

### Endpoint

```
GET /api/product/v1/products
```

### Header

```
Authorization: Bearer {accessToken}
```

### Query Parameter

| name | type | required | desc |
| --- | --- | --: | --- |
| name | string | 선택 | 상품명 검색 |
| categoryId | long | 선택 | 카테고리 필터 |
| status | string | 선택 | 상태 필터 |
| page | int | 선택 | 기본 0 |
| size | int | 선택 | 기본 10 |
| sort | string | 선택 | 기본 `id,asc` |

### Response Field (`PageResponse<ProductsResponse>`)

```
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 10,
        "name": "무선 이어폰",
        "category": "전자기기",
        "price": 129000,
        "totalQty": 50,
        "status": "ON_SALE",
        "createdAt": "2026-02-19T10:00:00",
        "adminName": "홍길동"
      }
    ],
    "page": 0,
    "size": 10,
    "totalElements": 1,
    "totalPages": 1
  },
  "error": null
}
```

### Response Code

-   `200 OK`

---

## 4-3) 상품 상세 조회

### Endpoint

```
GET /api/product/v1/products/{productId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response Field (`ProductResponse`)

> `reviews` 필드는 “상품별 리뷰 요약”이 붙을 수 있습니다(프로젝트 설계 반영).

```
{
  "success": true,
  "data": {
    "id": 10,
    "name": "무선 이어폰",
    "categoryName": "전자기기",
    "price": 129000,
    "qty": 50,
    "status": "ON_SALE",
    "adminEmail": "admin@test.com",
    "reviews": {
      "averageRating": 4.3,
      "totalReviewCount": 58,
      "starCounts": {
        "5": 30,
        "4": 15,
        "3": 8,
        "2": 3,
        "1": 2
      },
      "latestReviews": [
        {
          "id": 101,
          "product": "무선 이어폰",
          "order": "ORD-20260210-00123",
          "customer": "홍길동",
          "rating": 5,
          "content": "배송도 빠르고 품질이 뛰어납니다.",
          "createdAt": "2026-02-18T14:30:00"
        }
      ]
    },
    "createdAt": "2026-02-19T10:00:00"
  },
  "error": null
}
```

### Response Code

-   `200 OK`
-   `404 NOT FOUND`

---

## 4-4) 상품 수정

### Endpoint

```
PUT /api/product/v1/products/{productId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "name": "무선 이어폰 Pro",
  "categoryId": 1,
  "price": 159000
}
```

### Response Field (`ProductResponse`)

-   상품 상세 응답과 동일 구조

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `404 NOT FOUND`

---

## 4-5) 상품 상태 변경

### Endpoint

```
PATCH /api/product/v1/products/{productId}/status
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "status": "DISCONTINUED"
}
```

### Response Field (`ProductResponse`)

-   상품 상세 응답과 동일 구조

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `404 NOT FOUND`

---

## 4-6) 상품 삭제 (Soft Delete)

### Endpoint

```
DELETE /api/product/v1/products/{productId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response

-   응답 바디 없음

### Response Code

-   `204 NO CONTENT`
-   `404 NOT FOUND`

---

# 5\. SKU / 재고 수량 변경

## 5-1) SKU 수량 변경

### Description

-   SKU의 `qty`를 변경합니다.

### Endpoint

```
PATCH /api/product/v1/skus/{skuId}/qty
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "qty": 25
}
```

### Response Field (`SkuResponse`)

```
{
  "success": true,
  "data": {
    "id": 100,
    "productName": "무선 이어폰",
    "adminName": "홍길동",
    "price": 129000,
    "qty": 25,
    "status": "ACTIVE",
    "createdBy": 1,
    "createdAt": "2026-02-19T10:00:00",
    "modifiedBy": 1,
    "modifiedAt": "2026-02-19T10:30:00"
  },
  "error": null
}
```

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `404 NOT FOUND`

---

# 6\. ORDER (주문)

## 6-1) 주문 생성 (다품목)

### Endpoint

```
POST /api/order/v1/orders
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "customerId": 1,
  "items": [
    { "skuId": 100, "quantity": 2 },
    { "skuId": 101, "quantity": 1 }
  ]
}
```

### Response Field (`OrderResponse`)

```
{
  "success": true,
  "data": {
    "orderId": 1,
    "orderNumber": "ORD-20260219-00001",
    "orderStatus": "READY",
    "orderedAt": "2026-02-19T10:00:00",
    "totalAmount": 387000,
    "items": [
      { "productId": 10, "productName": "무선 이어폰", "quantity": 2, "orderPrice": 129000, "totalAmount": 258000 },
      { "productId": 11, "productName": "스마트 워치", "quantity": 1, "orderPrice": 129000, "totalAmount": 129000 }
    ],
    "createdBy": 1,
    "createdAt": "2026-02-19T10:00:00",
    "modifiedBy": 1,
    "modifiedAt": "2026-02-19T10:00:00"
  },
  "error": null
}
```

### Response Code

-   `201 CREATED`
-   `400 BAD REQUEST`

---

## 6-2) 주문 리스트 조회 (다품목 노출)

### Endpoint

```
GET /api/order/v1/orders
```

### Header

```
Authorization: Bearer {accessToken}
```

### Query Parameter (`OrderSearchRequest`)

| name | type | required | default | desc |
| --- | --- | --: | --- | --- |
| page | int | 선택 | 1 | (요청은 1부터, 내부는 0부터 변환) |
| size | int | 선택 | 10 |   |
| sortBy | string | 선택 | orderedAt | `quantity` / `totalAmount(amount)` / `orderedAt` |
| direction | string | 선택 | desc | `asc` / `desc` |
| keyword | string | 선택 | \- | 주문번호/고객명 검색 |
| status | string | 선택 | \- | 주문상태 |

### Response Field (`PageResponse<OrderListResponse>`)

```
{
  "success": true,
  "data": {
    "content": [
      {
        "orderId": 1,
        "orderNumber": "ORD-20260219-00001",
        "customerName": "고객1",
        "items": [
          { "productId": 10, "productName": "무선 이어폰", "quantity": 2, "orderPrice": 129000, "totalAmount": 258000 }
        ],
        "totalQuantity": 3,
        "totalAmount": 387000,
        "orderedAt": "2026-02-19T10:00:00",
        "status": "READY",
        "managerName": "홍길동"
      }
    ],
    "page": 0,
    "size": 10,
    "totalElements": 1,
    "totalPages": 1
  },
  "error": null
}
```

### Response Code

-   `200 OK`

---

## 6-3) 주문 단건 조회 (상세)

### Endpoint

```
GET /api/order/v1/orders/{orderId}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response Field (`GetOneOrderResponse`)

```
{
  "success": true,
  "data": {
    "orderNumber": "ORD-20260219-00001",
    "customerName": "고객1",
    "customerEmail": "user1@test.com",
    "items": [
      { "productId": 10, "productName": "무선 이어폰", "quantity": 2, "orderPrice": 129000, "totalAmount": 258000 }
    ],
    "orderStatus": "READY",
    "createdBy": 1,
    "createdAt": "2026-02-19T10:00:00",
    "modifiedBy": 1,
    "modifiedAt": "2026-02-19T10:00:00",
    "managerName": "홍길동",
    "managerEmail": "admin@test.com",
    "managerRole": "CS_ADMIN"
  },
  "error": null
}
```

### Response Code

-   `200 OK`
-   `404 NOT FOUND`

---

## 6-4) 주문 상태 변경 (운영/슈퍼)

### Endpoint

```
PATCH /api/order/v1/orders/{orderId}/status
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "orderStatus": "SHIPPING"
}
```

### Response Field (`OrderResponse`)

-   주문 생성 응답과 동일 구조

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `404 NOT FOUND`
-   `403 FORBIDDEN`

---

## 6-5) 주문 취소

### Endpoint

```
PATCH /api/order/v1/orders/{orderId}/cancel
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "cancellationReason": "고객 요청"
}
```

### Response Field (`OrderResponse`)

-   주문 생성 응답과 동일 구조 (상태가 `CANCELED`로 변경)

### Response Code

-   `200 OK`
-   `400 BAD REQUEST` (이미 배송중/완료 등 취소 불가)
-   `404 NOT FOUND`

---

## 6-6) (과제용) 단일 상품 주문 리스트

### Endpoint

```
GET /api/order/v1/orders/single
```

### Header

```
Authorization: Bearer {accessToken}
```

### Query Parameter

-   `OrderSearchRequest` 동일

### Response Field (`PageResponse<OrderNotListResponse>`)

```
{
  "success": true,
  "data": {
    "content": [
      {
        "orderId": 1,
        "orderNumber": "ORD-20260219-00001",
        "customerName": "고객1",
        "productName": "무선 이어폰",
        "totalQuantity": 2,
        "totalAmount": 258000,
        "orderedAt": "2026-02-19T10:00:00",
        "status": "READY",
        "managerName": "홍길동"
      }
    ],
    "page": 0,
    "size": 10,
    "totalElements": 1,
    "totalPages": 1
  },
  "error": null
}
```

### Response Code

-   `200 OK`

---

## 6-7) (과제용) 단일 상품 주문 생성

### Endpoint

```
POST /api/order/v1/orders/single
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "customerId": 1,
  "skuId": 100,
  "quantity": 2
}
```

### Response Field (`OrderResponse`)

-   주문 생성 응답과 동일 구조

### Response Code

-   `201 CREATED`
-   `400 BAD REQUEST`

---

# 7\. REVIEW (리뷰)

## 7-1) 리뷰 리스트 조회 (페이징/검색/필터)

### Endpoint

```
GET /api/review/v1/reviews
```

### Header

```
Authorization: Bearer {accessToken}
```

### Query Parameter

| name | type | required | desc |
| --- | --- | --: | --- |
| page | int | 선택 | 기본 0 |
| size | int | 선택 | 기본 10 |
| sort | string | 선택 | 기본 `createdAt,desc` |
| keyword | string | 선택 | 고객/상품 검색 |
| rating | int | 선택 | 별점 필터 |

### Response Field (`PageResponse<ReviewResponse>`)

```
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 101,
        "product": "무선 이어폰",
        "order": "ORD-20260210-00123",
        "customer": "홍길동",
        "rating": 5,
        "content": "배송도 빠르고 품질이 뛰어납니다.",
        "createdAt": "2026-02-18T14:30:00"
      }
    ],
    "page": 0,
    "size": 10,
    "totalElements": 1,
    "totalPages": 1
  },
  "error": null
}
```

### Response Code

-   `200 OK`

---

## 7-2) 리뷰 상세 조회

### Endpoint

```
GET /api/review/v1/reviews/{id}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response Field (`ReviewResponse`)

```
{
  "success": true,
  "data": {
    "id": 101,
    "product": "무선 이어폰",
    "order": "ORD-20260210-00123",
    "customer": "홍길동",
    "rating": 5,
    "content": "배송도 빠르고 품질이 뛰어납니다.",
    "createdAt": "2026-02-18T14:30:00"
  },
  "error": null
}
```

### Response Code

-   `200 OK`
-   `404 NOT FOUND`

---

## 7-3) 리뷰 수정

### Endpoint

```
PUT /api/review/v1/reviews/{id}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Request Body

```
{
  "content": "내용 수정"
}
```

### Response Field (`ReviewResponse`)

-   리뷰 상세 응답과 동일 구조

### Response Code

-   `200 OK`
-   `400 BAD REQUEST`
-   `404 NOT FOUND`

---

## 7-4) 리뷰 삭제 (Soft Delete)

### Endpoint

```
DELETE /api/review/v1/reviews/{id}
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response

-   응답 바디 없음

### Response Code

-   `204 NO CONTENT`
-   `404 NOT FOUND`

---

## 7-5) 리뷰 복구 (Restore)

### Endpoint

```
PUT /api/review/v1/reviews/{id}/restore
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response

-   응답 바디 없음 (프로젝트 코드상 body 없이 OK 반환)

### Response Code

-   `200 OK`
-   `404 NOT FOUND`

---

# 8\. DASHBOARD (대시보드)

## 8-1) 대시보드 조회

### Endpoint

```
GET /api/dashboard/v1/dashboards
```

### Header

```
Authorization: Bearer {accessToken}
```

### Response Field (`Dashboard`)

```
{
  "success": true,
  "data": {
    "summary": {
      "adminCount": 10,
      "activeAdminCount": 7,
      "customerCount": 100,
      "activeCustomerCount": 80,
      "productCount": 200,
      "oosProductCount": 12,
      "orderCount": 500,
      "todayOrderCount": 23,
      "reviewCount": 58,
      "reviewAvg": 4.3
    },
    "widgets": {
      "totalAmount": 123456789,
      "todayTotalAmount": 123450,
      "readyOrderCount": 10,
      "shippingOrderCount": 5,
      "completedOrderCount": 8,
      "oosProductCount": 12,
      "soldOutProductCount": 3
    },
    "charts": {
      "ratingCounts": [
        { "rating": 5, "count": 30 }
      ],
      "customerStatusCounts": [
        { "status": "ACTIVE", "count": 80 }
      ],
      "categoryProductCounts": [
        { "categoryName": "전자기기", "productCount": 55 }
      ]
    },
    "recentOrders": [
      {
        "orderNumber": "ORD-20260219-00001",
        "customerName": "고객1",
        "productName": "무선 이어폰",
        "totalAmount": 258000,
        "status": "READY"
      }
    ]
  },
  "error": null
}
```

### Response Code

-   `200 OK`
-   `401 UNAUTHORIZED`


---


</details>


# 🏗️ 시스템 아키텍처

## 3-Layer Architecture

- Controller
- Service
- Repository
- DTO
- Entity
- Security
- Exception
- Common

---

# 🔐 인증 및 인가

## JWT 기반 인증

- 로그인 성공 시 Access Token 발급
- Authorization Header 사용

```
Authorization: Bearer {accessToken}
```

### JWT Payload

- adminId
- email
- role
- issuedAt
- expiredAt

### 특징

- Stateless 구조
- JwtAuthenticationFilter 적용
- PrincipalUser / PrincipalUserService 기반 인증 처리

---

## RBAC (Role-Based Access Control)

Spring Security 기반 URL 접근 제어

### 관리자 역할

- SUPER_ADMIN
- OPERATION_ADMIN
- CS_ADMIN

권한 부족 시 403 반환

---

# 📦 도메인 설계

---

## 👤 Admin

### 주요 필드

- id
- name
- email (unique)
- password (BCrypt)
- role (AdminRoleEnum)
- status (AdminStatusEnum)
- approvedAt
- rejectedAt
- rejectReason
- deleted (Soft Delete)
- createdAt
- modifiedAt

### 기능

- 관리자 회원가입
- 로그인
- 승인 / 거부
- 역할 변경
- 상태 변경
- 비밀번호 변경
- 프로필 수정

---

## 👥 Customer

### 주요 필드

- id
- name
- email
- phoneNumber
- status
- deleted
- createdAt

### 기능

- 고객 조회
- 검색 / 정렬 / 페이징
- 상태 변경
- Soft Delete

---

## 📦 Product

### 구조

- Product
- Category
- Sku
- Inventory

### Product

- id
- name
- category
- status (ProductStatus)
- deleted

### Sku

- id
- qty
- status

### Inventory

- 재고 관리 로직 분리

### 특징

- 재고 수량 변경 시 상태 자동 반영
- 단종 상태 예외 처리
- QueryDSL 기반 검색

---

## 🛒 Order

### 구조

- Order (1)
- OrderItem (N)

### Order

- id
- orderNumber
- customer
- status (OrderStatus)
- totalPrice
- cancelReason
- version (낙관적 락)
- deleted
- orderedAt

### OrderItem

- id
- order
- product
- quantity
- price

### 주요 로직

- 주문 생성 시:
  - 재고 검증
  - 재고 차감
  - 총 금액 계산
- 주문 취소 시:
  - 상태 검증 (준비중만 가능)
  - 재고 복구
  - 상품 상태 재계산
- @Version 기반 낙관적 락 적용

---

## ⭐ Review

### 주요 필드

- id
- product
- customer
- rating
- content
- deleted

### 기능

- 리뷰 조회
- 상품별 평균 평점 계산
- 별점 분포 집계
- 관리자 삭제

---

# 📊 Dashboard

DashboardJdbcRepository 기반 통계 처리

### 제공 데이터

- 전체 관리자 수
- 전체 고객 수
- 전체 상품 수
- 재고 부족 상품 수
- 전체 주문 수
- 오늘 주문 수
- 총 매출
- 오늘 매출
- 리뷰 평균 평점
- 리뷰 평점 분포
- 고객 상태 분포
- 카테고리별 상품 수
- 최근 주문 10건

JPA가 아닌 JDBC 사용 → 집계 성능 고려 설계

---

# 🔎 검색 / 페이징 / 정렬

- PageableConfig 적용
- PageResponse 공통 구조 사용
- QueryDSL 기반 동적 검색
- RepositoryCustom + Impl 구조 사용

---

# 🗑 Soft Delete 전략

SoftDeletableEntity 상속 구조

- deleted = true
- 물리 삭제 없음
- 모든 조회는 deleted = false 조건 포함

---

# ⚠️ 전역 예외 처리

- GlobalExceptionHandler
- ErrorCode Enum
- ApiResponse / ErrorResponse 통일

---

# 📦 공통 응답 구조

## 성공 응답

```json
{
  "status": 200,
  "message": "성공 메시지",
  "data": {}
}
```

## 페이지 응답

```json
{
  "content": [],
  "page": 0,
  "size": 10,
  "totalElements": 100,
  "totalPages": 10
}
```

---

# 📚 기술 스택

- Java 17
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- QueryDSL
- MySQL
- Lombok
- Swagger

---

# 🧠 학습 포인트

- JWT 인증 흐름 이해
- RBAC 설계
- QueryDSL 동적 쿼리
- Soft Delete 전략
- 낙관적 락 기반 동시성 제어
- Order + OrderItem 구조 설계
- 집계 쿼리 성능 고려 (JDBC 활용)
- 공통 응답 구조 통일

---

# ✅ 프로젝트 의의

본 프로젝트는 단순 CRUD 구현이 아닌,

- 실무형 도메인 설계
- 인증/인가 구조 설계
- 상태 전이 설계
- 동시성 제어 전략
- 통계 집계 설계
- 확장 가능한 구조 설계

를 목표로 한 실전형 백오피스 시스템입니다.
