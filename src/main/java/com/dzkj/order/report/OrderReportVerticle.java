package com.dzkj.order.report;

import com.dzkj.order.report.db.DbHelper;
import com.dzkj.order.report.prop.ConfigProperties;
import com.dzkj.order.report.web.MainRoute;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.mysqlclient.MySQLPool;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderReportVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        configureRouter()
            .compose(this::startHttpServer)
            .onSuccess(s -> startPromise.complete())
            .onFailure(startPromise::fail);
    }

    private Future<Void> startHttpServer(Router router) {
        ConfigProperties properties = config().mapTo(ConfigProperties.class);
        int port = properties.getServer().getPort();
        log.info("===>json: {}, port: {}", config(), port);
        HttpServer httpServer = vertx.createHttpServer().requestHandler(router);
        return Future.future(promise -> httpServer.listen(port));
    }

    private Future<Router> configureRouter() {
        DbHelper dbHelper = new DbHelper(config().getJsonObject("mysql"), vertx);
        dbHelper.afterPropertiesSet();
        MySQLPool pool = dbHelper.client();
        return Future.succeededFuture(new MainRoute().create(vertx, pool));
    }

    private Future<Void> initDbHelper(Void unused) {
        DbHelper dbHelper = new DbHelper(config().getJsonObject("mysql"), vertx);
        dbHelper.afterPropertiesSet();
        return Future.future(Promise::complete).mapEmpty();
    }
}
