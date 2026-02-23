package com.seventeamproject.api.order.dto;

public record OrderItemRequest (
    Long productId,
    Long quantity) {}
