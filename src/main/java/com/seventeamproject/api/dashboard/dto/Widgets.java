package com.seventeamproject.api.dashboard.dto;

import java.math.BigDecimal;

public record Widgets(
        BigDecimal totalAmount,
        BigDecimal todayTotalAmount,
        Long readyOrderCount,
        Long shippingOrderCount,
        Long completedOrderCount,
        Long oosProductCount,
        Long soldOutProductCount) {
}
