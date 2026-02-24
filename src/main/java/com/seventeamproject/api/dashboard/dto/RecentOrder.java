package com.seventeamproject.api.dashboard.dto;

import java.math.BigDecimal;

public record RecentOrder(
        String orderNumber,
        String customerName,
        String productName,
        BigDecimal totalAmount,
        String status
) {
}