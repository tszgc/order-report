package com.dzkj.order.report.web;

import com.dzkj.order.report.service.OrderService;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.mysqlclient.MySQLPool;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述：order router
 * 作者：zgc
 * 时间：2022/7/13 21:29
 */
@Slf4j
public class OrderRoute {

    public Router create(Vertx vertx, MySQLPool mySQLPool) {
        Router orderRouter = Router.router(vertx);
        OrderService orderService = new OrderService(mySQLPool);
        orderRouter.get("/report").handler(orderService::report);
        return orderRouter;
    }

}
