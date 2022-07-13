package com.dzkj.order.report.web;

import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：order router
 * 作者：zgc
 * 时间：2022/7/13 21:29
 */
@Slf4j
public class OrderRoute {

    public Router create(Vertx vertx) {
        Router orderRouter = Router.router(vertx);

        Handler<RoutingContext> handler = this::report;
        orderRouter.get("/report").handler(handler);

        return orderRouter;
    }

    private void report(RoutingContext ctx) {
        String host = ctx.request().host();
        log.info(host);
        Map<String, String> map = new HashMap<>();
        map.put("name", "zgc");
        ctx.json(map);
    }

}
