package com.seventeamproject.api.dashboard.dto;

import java.util.List;

public record Charts(List<RatingCount> ratingCounts,
                     List<CustomerStatusCount> customerStatusCounts,
                     List<CategoryProductCount> categoryProductCounts
) {
}
