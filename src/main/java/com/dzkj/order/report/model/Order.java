package com.dzkj.order.report.model;

import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Order {

    private Long id;

    private Long userId;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Integer status;

    public Order() {}

    public Order(Row row) {
        this.id = row.getLong("id");
        this.userId = row.getLong("userId");
        this.createAt = row.getLocalDateTime("createAt");
        this.updateAt = row.getLocalDateTime("updateAt");
        this.status = row.getInteger("status");
    }

    public JsonObject toJson() {
        return new JsonObject().put("id", this.id)
                .put("title", this.userId)
                .put("createAt", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this.createAt))
                .put("updateAt", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this.updateAt))
                .put("status", this.status);
    }
}
