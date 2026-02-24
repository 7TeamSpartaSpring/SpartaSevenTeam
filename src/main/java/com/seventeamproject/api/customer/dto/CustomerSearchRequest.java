package com.seventeamproject.api.customer.dto;

import lombok.Builder;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

public record CustomerSearchRequest(
        String keyword,
        String stat
) {}