package com.seventeamproject.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

/**
 * 애플리케이션에서 발생할 수 있는 에러들을 정의한 Enum 클래스입니다.
 * 에러 코드, 상태 코드, 메시지를 한 곳에서 관리하여 일관된 에러 응답을 보장합니다.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // [C] COMMON - 공통
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 내부 오류가 발생했습니다."),

    // [M] DOMAIN(공통/회원) - 요청/검증 관련 (400)
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "M001", "이미 존재하는 이메일입니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "M002", "이미 존재하는 사용자 이름입니다."),
    DUPLICATE_PHONE(HttpStatus.BAD_REQUEST, "M003", "이미 존재하는 전화번호입니다."),
    ORDER_NUMBER_REQUIRED(HttpStatus.BAD_REQUEST, "O002", "주문번호는 필수입니다."),
    ORDER_CUSTOMER_REQUIRED(HttpStatus.BAD_REQUEST, "O003", "고객은 필수입니다."),
    ORDER_MANAGER_REQUIRED(HttpStatus.BAD_REQUEST, "O004", "담당자 ID는 필수입니다."),
    ORDER_ITEMS_REQUIRED(HttpStatus.BAD_REQUEST, "O005", "주문 항목은 최소 1개 필요합니다."),
    ORDER_ITEM_REQUIRED(HttpStatus.BAD_REQUEST, "O006", "주문 항목은 필수입니다."),
    ORDER_STATUS_REQUIRED(HttpStatus.BAD_REQUEST, "O007", "주문 상태는 필수입니다."),
    ORDER_CANCEL_REASON_REQUIRED(HttpStatus.BAD_REQUEST, "O008", "취소 사유는 필수입니다."),
    ORDER_ITEM_PRODUCT_REQUIRED(HttpStatus.BAD_REQUEST, "O011", "상품은 필수입니다."),
    ORDER_ITEM_QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "O012", "수량은 1 이상이어야 합니다."),
    ORDER_ITEM_PRICE_INVALID(HttpStatus.BAD_REQUEST, "O013", "주문 가격은 0 이상이어야 합니다."),
    ORDER_ITEM_SKU_REQUIRED(HttpStatus.BAD_REQUEST, "O014", "SKU는 필수입니다."),
    ORDER_STATUS_BAD_REQUEST(HttpStatus.BAD_REQUEST, "O015", "상태는 준비 -> 배송중 -> 완료 순서로 변경가능합니다"),



    // [M] NOT_FOUND - 리소스를 찾을 수 없음 (404)
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M004", "회원을 찾을 수 없습니다."),
    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND,"M006", "해당하는 고객을 찾을 수 없습니다."),
    STATUS_NOT_FOUND(HttpStatus.NOT_FOUND,"M007","해당하는 상태 값을 찾을 수 없습니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "M008", "관리자를 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "M009", "리뷰를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "P002", "상품을 찾을 수 없습니다."),
    SKU_NOT_FOUND(HttpStatus.NOT_FOUND, "P005", "SKU를 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "P006", "카테고리를 찾을 수 없습니다."),
    STOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "P007", "재고를 찾을 수 없습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "O001", "주문을 찾을 수 없습니다."),

    // [P] AUTH - 인증 (401)
    STATE_NOT_LOGIN(HttpStatus.UNAUTHORIZED, "P008", "로그인되어 있지 않습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "P009", "이메일 또는 비밀번호가 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "M005", "비밀번호가 일치하지 않습니다."),

    // [P] AUTHZ - 인가/권한 (403)
    // FORBIDDEN(403) - 접근/상태 제한
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "P010", "접근 권한이 없습니다."),
    INVALID_PROFILE(HttpStatus.BAD_REQUEST, "P007", "사용자가 일치하지 않습니다."),

    // [A] ADMIN - 관리자 로그인 정책/상태 제한 (403)
    ADMIN_PENDING(HttpStatus.FORBIDDEN, "A001", "계정 승인대기 중입니다."),
    ADMIN_REJECTED(HttpStatus.FORBIDDEN, "A002", "계정 신청이 거부되었습니다."),
    ADMIN_SUSPENDED(HttpStatus.FORBIDDEN, "A003", "계정이 정지되었습니다."),
    ADMIN_INACTIVE(HttpStatus.FORBIDDEN, "A004", "계정이 비활성화되었습니다."),


    // [M] CONFLICT - 상태 충돌/규칙 위반 (409)
    INVALID_STATUS_CHANGE(HttpStatus.CONFLICT, "M010", "허용되지 않은 상태 전환입니다."),
    ORDER_OUT_OF_STOCK(HttpStatus.CONFLICT, "P001", "요청수량이 재고보다 많습니다."),
    ORDER_CANCEL_FAIL(HttpStatus.CONFLICT, "P004", "재고 조정 오류로 주문 취소에 실패 했습니다."),
    ORDER_UNAVAILABLE_STATUS(HttpStatus.CONFLICT, "P003", "사용 할 수 없는 상태 입니다."),
    ORDER_ALREADY_CANCELED(HttpStatus.CONFLICT, "O009", "이미 취소된 주문입니다."),
    ORDER_CANCEL_NOT_ALLOWED(HttpStatus.CONFLICT, "O010", "준비중 상태에서만 취소 가능합니다.");

    private final HttpStatus status; // HTTP 상태 코드 (예: 400, 404, 500)
    private final String code;       // 우리가 정의한 고유 에러 코드 (예: M001)
    private final String message;    // 사용자에게 보여줄 에러 메시지
}
