package com.dzkj.order.report;

import com.dzkj.order.report.db.DbHelper;
import com.dzkj.order.report.prop.ConfigProperties;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderReportVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        ConfigProperties properties = config().mapTo(ConfigProperties.class);
        int port = properties.getServer().getPort();
        log.info("===>json: {}, port: {}", properties, port);
        vertx.createHttpServer().requestHandler(req -> {
            req.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!");
        }).listen(port, http -> {
            if (http.succeeded()) {
                startPromise.complete();
                log.info("HTTP server started on port {}", port);
            } else {
                startPromise.fail(http.cause());
            }
        });
    }
}
