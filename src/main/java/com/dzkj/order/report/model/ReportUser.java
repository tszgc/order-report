package com.dzkj.order.report.model;

import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Row;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ReportUser {

    private Long id;

    private Long userId;

    private Integer totalNum;

    private BigDecimal totalAmount;

  private LocalDateTime createAt;

  private LocalDateTime updateAt;

    public ReportUser() {}

    public ReportUser(Row row) {
        this.id = row.getLong("id");
        this.userId = row.getLong("userId");
        this.totalNum = row.getInteger("totalNum");
        this.totalAmount = row.getBigDecimal("totalAmount");
        this.createAt = row.getLocalDateTime("createAt");
        this.updateAt = row.getLocalDateTime("updateAt");
    }

    public JsonObject toJson() {
        return new JsonObject().put("id", this.id)
            .put("userId", this.userId)
            .put("totalNum", this.totalNum)
            .put("totalAmount", this.totalAmount)
            .put("createAt", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this.createAt))
            .put("updateAt", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(this.updateAt));
    }

}
