package com.dzkj.order.report.web;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.mysqlclient.MySQLPool;

/**
 * 描述：main router
 * 作者：zgc
 * 时间：2022/7/13 21:27
 */
public class MainRoute {

    public Router create(Vertx vertx, MySQLPool pool) {
        Router mainRouter = Router.router(vertx);
        mainRouter.route().consumes("application/json; charset=utf-8");
        mainRouter.route().produces("application/json; charset=utf-8");
        mainRouter.route().handler(BodyHandler.create());
        mainRouter.mountSubRouter("/order", new OrderRoute().create(vertx, pool));
        return mainRouter;
    }

}
