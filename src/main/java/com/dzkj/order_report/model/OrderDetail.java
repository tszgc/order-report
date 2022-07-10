package com.dzkj.order_report.model;

import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class OrderDetail {

    private Long id;

    private Long orderId;

    private BigDecimal price;

    private Integer num;

    private Long goodsId;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    public OrderDetail() {}

    public OrderDetail(Row row) {
        this.id = row.getLong("id");
        this.orderId = row.getLong("orderId");
        this.price = row.getBigDecimal("price");
        this.num = row.getInteger("num");
        this.goodsId = row.getLong("goodsId");
        this.createAt = row.getLocalDateTime("createAt");
        this.updateAt = row.getLocalDateTime("updateAt");
    }

    public JsonObject toJson() {
        return new JsonObject().put("id", this.id)
          .put("orderId", this.orderId)
          .put("price", this.price)
          .put("num", this.num)
          .put("goodsId", goodsId)
          .put("createAt", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this.createAt))
          .put("updateAt", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this.updateAt));
    }

}
