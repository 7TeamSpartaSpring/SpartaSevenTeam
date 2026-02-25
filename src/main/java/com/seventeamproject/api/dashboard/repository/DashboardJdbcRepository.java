package com.seventeamproject.api.dashboard.repository;

import com.seventeamproject.api.dashboard.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DashboardJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public Summary getSummary() {
        String sql = """
            SELECT
               (SELECT COUNT(id) FROM admin WHERE deleted_at IS NULL) AS adminCount,
               (SELECT COUNT(id) FROM admin WHERE deleted_at IS NULL AND status = 'ACTIVE') AS activeAdminCount,
               (SELECT COUNT(id) FROM customers WHERE deleted_at IS NULL) AS customerCount,
               (SELECT COUNT(id) FROM customers WHERE deleted_at IS NULL AND status = 'ACTIVATED') AS activeCustomerCount,
               (SELECT COUNT(id) FROM products WHERE deleted_at IS NULL) AS productCount,
               COALESCE((
                   SELECT COUNT(*)
                   FROM (
                       SELECT A.id
                       FROM products A
                       LEFT JOIN skus B ON A.id = B.product_id
                       LEFT JOIN inventorys C ON B.id = C.sku_id
                      WHERE A.deleted_at IS NULL
                       GROUP BY A.id
                       HAVING SUM(C.qty) <= 5
                   ) t
               ),0) AS oosProductCount,
               (SELECT COUNT(id) FROM orders WHERE deleted_at IS NULL) AS orderCount,
               (SELECT COUNT(id) FROM orders WHERE deleted_at IS NULL AND DATE(created_at)=CURRENT_DATE) AS todayOrderCount,
               (SELECT COUNT(id) FROM reviews WHERE deleted_at IS NULL) AS reviewCount,
               COALESCE((SELECT ROUND(AVG(rating),1) FROM reviews WHERE deleted_at IS NULL),0.0) AS reviewAvg
        """;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Summary(
                rs.getLong("adminCount"),
                rs.getLong("activeAdminCount"),
                rs.getLong("customerCount"),
                rs.getLong("activeCustomerCount"),
                rs.getLong("productCount"),
                rs.getLong("oosProductCount"),
                rs.getLong("orderCount"),
                rs.getLong("todayOrderCount"),
                rs.getLong("reviewCount"),
                rs.getDouble("reviewAvg")
        ));
    }

    public Widgets getWidgets() {
        String sql = """
            SELECT
               COALESCE((SELECT SUM(total_amount) FROM orders WHERE deleted_at IS NULL),0) AS totalAmount,
               COALESCE((SELECT SUM(total_amount) FROM orders WHERE deleted_at IS NULL AND DATE(created_at)=CURRENT_DATE),0) AS todayTotalAmount,
               (SELECT COUNT(id) FROM orders WHERE deleted_at IS NULL AND status = 'READY') AS readyOrderCount,
               (SELECT COUNT(id) FROM orders WHERE deleted_at IS NULL AND status = 'SHIPPING') AS shippingOrderCount,
               (SELECT COUNT(id) FROM orders WHERE deleted_at IS NULL AND status = 'COMPLETED') AS completedOrderCount,
               COALESCE((
                   SELECT COUNT(*)
                   FROM (
                       SELECT A.id
                       FROM products A
                       LEFT JOIN skus B ON A.id = B.product_id
                       LEFT JOIN inventorys C ON B.id = C.sku_id
                      WHERE A.deleted_at IS NULL
                       GROUP BY A.id
                       HAVING SUM(C.qty) <= 5
                   ) t
               ),0) AS oosProductCount,
               (SELECT COUNT(id) FROM products WHERE deleted_at IS NULL AND status = 'SOLD_OUT') AS soldOutProductCount
        """;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Widgets(
                rs.getBigDecimal("totalAmount"),
                rs.getBigDecimal("todayTotalAmount"),
                rs.getLong("readyOrderCount"),
                rs.getLong("shippingOrderCount"),
                rs.getLong("completedOrderCount"),
                rs.getLong("oosProductCount"),
                rs.getLong("soldOutProductCount")
        ));
    }

    public List<RatingCount> getRatingStats() {
        String sql = """
            SELECT A.rating, COUNT(B.id) AS count
              FROM (SELECT 1 AS rating
          UNION ALL SELECT 2
          UNION ALL SELECT 3
          UNION ALL SELECT 4
          UNION ALL SELECT 5 ) A
         LEFT JOIN reviews B ON A.rating = B.rating AND B.deleted_at IS NULL
          GROUP BY A.rating
          ORDER BY A.rating
        """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new RatingCount(
                        rs.getInt("rating"),
                        rs.getLong("count")
                ));
    }

    public List<CustomerStatusCount> getCustomerStatusStats() {
        String sql = """
            SELECT A.status, COUNT(B.id) AS count
              FROM (SELECT 'ACTIVE'  AS status
          UNION ALL SELECT 'INACTIVE'
          UNION ALL SELECT 'SUSPENDED') A
         LEFT JOIN customers B ON A.status = B.status AND B.deleted_at IS NULL
            GROUP BY A.status
            ORDER BY A.status
        """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new CustomerStatusCount(
                        rs.getString("status"),
                        rs.getLong("count")
                ));
    }

    public List<CategoryProductCount> getCategoryProductStats() {
        String sql = """
            SELECT A.name, COUNT(B.id) AS count
            FROM categorys A
            LEFT JOIN products B ON B.category_id = A.id
           WHERE B.deleted_at IS NULL
            GROUP BY A.id
            ORDER BY A.code
        """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new CategoryProductCount(
                        rs.getString("name"),
                        rs.getLong("count")
                ));
    }

    public List<RecentOrder> getRecentOrders() {
        String sql = """
            SELECT A.order_number AS orderNumber,
                   B.name AS customerName,
                   D.name AS productName,
                   A.total_amount AS totalAmount,
                   A.status
            FROM orders A
            LEFT JOIN customers B ON A.customer_id = B.id
            LEFT JOIN (
                SELECT order_id, MIN(product_id) AS product_id
                FROM order_items
                GROUP BY order_id
            ) C ON A.id = C.order_id
            LEFT JOIN products D ON C.product_id = D.id
             WHERE A.deleted_at IS NULL
            ORDER BY A.created_at DESC
            LIMIT 10
        """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new RecentOrder(
                        rs.getString("orderNumber"),
                        rs.getString("customerName"),
                        rs.getString("productName"),
                        rs.getBigDecimal("totalAmount"),
                        rs.getString("status")
                ));
    }
}