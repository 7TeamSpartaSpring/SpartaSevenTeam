package com.seventeamproject.api.dashboard.service;

import com.seventeamproject.api.dashboard.dto.Charts;
import com.seventeamproject.api.dashboard.dto.Dashboard;
import com.seventeamproject.api.dashboard.repository.DashboardJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {
    private final DashboardJdbcRepository repository;

    public Dashboard getDashboard() {
        return new Dashboard(repository.getSummary(),
                repository.getWidgets(),
                new Charts(repository.getRatingStats(),
                        repository.getCustomerStatusStats(),
                        repository.getCategoryProductStats()),
                repository.getRecentOrders()) ;
    }
}
