package com.seventeamproject.api.dashboard.dto;

import java.math.BigDecimal;

public record Summary(
        Long adminCount,
        Long activeAdminCount,
        Long customerCount,
        Long activeCustomerCount,
        Long productCount,
        Long oosProductCount,
        Long orderCount,
        Long todayOrderCount,
        Long reviewCount,
        Double reviewAvg
) {
}