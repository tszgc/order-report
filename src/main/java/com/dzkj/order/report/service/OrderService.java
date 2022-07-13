package com.dzkj.order.report.service;

import com.dzkj.order.report.OrderReportVerticle;
import com.dzkj.order.report.db.DbHelper;
import com.dzkj.order.report.dto.JSONResult;
import com.dzkj.order.report.dto.ReportOrderDto;
import com.dzkj.order.report.model.ReportOrder;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.web.RoutingContext;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：OrderService
 * 作者：zgc
 * 时间：2022/7/13 21:52
 */
@AllArgsConstructor
@Slf4j
public class OrderService {
    private MySQLPool pool;

    public void report(RoutingContext ctx) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        queryReportIds(localDateTime.format(dateTimeFormatter), 1, 50)
            .compose(this::queryReportOrderByOrderIds)
            .compose(this::insertReportOrderBatch)
            .onComplete(ar -> {
                if (ar.succeeded()) {
                    ctx.json(JSONResult.success("共统计订单数=" + ar.result()));
                    log.info("订单统计成功单数=" + ar.result());
                } else {
                    log.error("订单统计失败", ar.cause());
                    ctx.json(JSONResult.fail("统计失败", ar.cause().getMessage()));
                }
            });
    }

    public Future<List<Long>> queryReportIds (String updateAt, long start, long size) {
        Promise<List<Long>> promise = Promise.promise();
        String sql = "select o.id from t_order o where o.status = 1 and o.update_at <= ? order by o.update_at limit ?, ? ";
        pool.preparedQuery(sql).execute(Tuple.of(updateAt, start, size), ar -> {
            if (ar.succeeded()) {
                List<Long> list = new ArrayList<>();
                RowSet<Row> rows = ar.result();
                for (Row row : rows) {
                    list.add(row.getLong("id"));
                }
                promise.complete(list);
            } else {
                promise.fail(ar.cause());
            }
        });
        return promise.future();
    }

    public Future<List<ReportOrder>> queryReportOrderByOrderIds(List<Long> orderIds) {
        Promise<List<ReportOrder>> promise = Promise.promise();
        String sql = "select d.order_id as orderId, sum(d.price) as totalAmount, sum(d.num) as totalNum from t_order_detail d where d.order_id in (" +
            StringUtils.join(orderIds, ",") +
            ") group by d.order_id";
        pool.preparedQuery(sql)
            .execute(ar -> {
                if (ar.succeeded()) {
                    RowSet<Row> rows = ar.result();
                    List<ReportOrder> list = new ArrayList<>(rows.size());
                    for (Row row : rows) {
                        list.add(new ReportOrderDto(row).toReportOrder());
                    }
                    promise.complete(list);
                } else {
                    promise.fail(ar.cause());
                }
            });
        return promise.future();
    }

    public Future<Integer> insertReportOrderBatch(List<ReportOrder> reportOrderList) {
        Promise<Integer> promise = Promise.promise();
        String sql = "insert into t_report_order (order_id, total_num, total_amount) values (?, ?, ?)";
        List<Tuple> batch = new ArrayList<>(reportOrderList.size());
        reportOrderList.forEach(reportOrder -> batch.add(Tuple.of(reportOrder.getOrderId(), reportOrder.getTotalNum(), reportOrder.getTotalAmount())));
        pool.preparedQuery(sql)
            .executeBatch(batch, ar-> {
                if (ar.succeeded()) {
                    RowSet<Row> rows = ar.result();
                    for (Row row : rows) {
                        System.out.println(row.toJson().toString());
                    }
                    promise.complete(ar.result().rowCount());
                } else {
                    promise.fail(ar.cause());
                }
            });
        return promise.future();
    }

}
