package com.seventeamproject.api.order.dto;

import com.seventeamproject.api.order.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class OrderSearchRequest {

    private Integer page = 1;
    private Integer size = 10;
    private String sortBy = "orderedAt";
    private String direction = "desc";
    private String keyword;
    private OrderStatus status;

    public Pageable toPageable() {
        int safePage = Math.max(pageOrDefault(), 1) - 1;
        return PageRequest.of(safePage, sizeOrDefault(), from(sortBy, direction));
    }

    public static Sort from(String sortBy, String direction) {
        String property = switch (sortBy) {
            case "quantity" -> "quantity";
            case "amount", "totalAmount" -> "totalAmount";
            case "orderedAt" -> "orderedAt";
            default -> "orderedAt";
        };

        return "asc".equalsIgnoreCase(direction)
                ? Sort.by(property).ascending()
                : Sort.by(property).descending();
    }

    private int pageOrDefault() {
        return page == null ? 1 : page;
    }

    private int sizeOrDefault() {
        return size == null ? 10 : size;
    }
}
