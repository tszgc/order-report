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

/**
 * 描述：main verticle
 * 作者：zgc
 * 时间：2022/7/12 20:36
 */
@Slf4j
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        ConfigRetriever retriever = readYaml(vertx);
        retriever.getConfig(json -> {
            try {
                DeploymentOptions opts = new DeploymentOptions()
                    .setConfig(json.result());
                vertx.deployVerticle(new OrderReportVerticle(), opts);
                startPromise.complete();
            } catch (Exception ex) {
                log.error("===> Vertx start fail: ", ex);
                startPromise.fail("unable to load configuration.");
            }
        });
    }

    private static ConfigRetriever readYaml(Vertx vertx) {
        ConfigStoreOptions store = new ConfigStoreOptions()
            .setType("file")
            .setFormat("yaml")
            .setOptional(true)
            .setConfig(new JsonObject().put("path", "application.yaml"));
        ConfigRetrieverOptions opts = new ConfigRetrieverOptions().addStore(store);
        return ConfigRetriever.create(vertx, opts);
    }
}
