package com.seventeamproject.api.dashboard.dto;

public record CustomerStatusCount(
        String status,
        Long count
) {
}