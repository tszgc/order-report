package com.dzkj.order.report;

import com.dzkj.order.report.db.DbHelper;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述：mysql application
 * 作者：zgc
 * 时间：2022/7/10 15:34
 */
@Slf4j
public class OrderReportApplication {


    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        ConfigRetriever retriever = readYaml(vertx);

        retriever.getConfig(json -> {
            try {
                JsonObject object = json.result();
                DbHelper dbHelper = new DbHelper(object.getJsonObject("mysql"), vertx);
                dbHelper.afterPropertiesSet();

                DeploymentOptions options = new DeploymentOptions().setConfig(object);
                vertx.deployVerticle(OrderReportVerticle.class.getName(), options);
            } catch (Exception ex) {
                log.error("===> Vertx start fail: ", ex);
            }
        });
    }

    private static ConfigRetriever readYaml(Vertx vertx) {
        ConfigStoreOptions store = new ConfigStoreOptions()
            .setType("file")
            .setFormat("yaml")
            .setOptional(true)
            .setConfig(new JsonObject().put("path", "application.yaml"));

        return ConfigRetriever.create(vertx, new ConfigRetrieverOptions().addStore(store));
    }
}
