package com.seventeamproject.api.dashboard.dto;

import java.util.List;

public record Dashboard(Summary summary,
                        Widgets widgets,
                        Charts charts,
                        List<RecentOrder> recentOrders
) {
}
