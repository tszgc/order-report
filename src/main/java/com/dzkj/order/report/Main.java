package com.dzkj.order.report;

import io.vertx.core.Vertx;

/**
 * 描述：Main
 * 作者：zgc
 * 时间：2022/7/13 23:14
 */
public class Main {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MainVerticle());
    }
}
