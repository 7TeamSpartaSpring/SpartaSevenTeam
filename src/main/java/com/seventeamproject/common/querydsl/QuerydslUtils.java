package com.seventeamproject.common.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class QuerydslUtils {
    public static <T extends Comparable> OrderSpecifier<?>[] getSort(
            Sort sort,
            Map<String, Expression<?>> sortMap,
            OrderSpecifier<?> defaultSort
    ) {
        List<OrderSpecifier<T>> orders = sort.stream()
                .map(order -> {

                    Expression<?> expr = sortMap.get(order.getProperty());
                    if (expr == null) return null;

                    return new OrderSpecifier<T>(
                            order.isAscending() ? Order.ASC : Order.DESC,
                            (Expression<T>) expr
                    );
                })
                .filter(Objects::nonNull)
                .toList();

        return orders.isEmpty()
                ? new OrderSpecifier[]{defaultSort}
                : orders.toArray(new OrderSpecifier[0]);
    }

    public static BooleanExpression like(StringPath field, String val) {
        return val != null ? field.contains(val) : null;
    }

    public static BooleanExpression eq(NumberPath<Long> field, Long val) {
        return val != null ? field.eq(val) : null;
    }

    public static BooleanExpression eq(StringPath field, String val) {
        return val != null ? field.eq(val) : null;
    }

    public static BooleanExpression eq(EnumPath field, Enum val) {
        return val != null ? field.eq(val) : null;
    }
}
