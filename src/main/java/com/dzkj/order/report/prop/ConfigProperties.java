package com.dzkj.order.report.prop;

import lombok.Data;

/**
 * 描述：配置文件类
 * 作者：zgc
 * 时间：2022/7/10 14:50
 */
@Data
public class ConfigProperties {

    /**
     * mysql config
     */
    private MysqlProperties mysql;
    /**
     * server config
     */
    private ServerProperties server;

    @Data
    public static class MysqlProperties {
        private String host;
        private int port = 3306;
        private String database;
        private String username;
        private String password;
        private String charset = "utf8";
        private String collation = "utf8_general_ci";

        private int maxSize = 10;
        private int reconnectAttempts = 3;
        private int reconnectInterval = 1000;
        private String poolName = "p-mysql";
    }

    @Data
    public static class ServerProperties {
        private int port;
    }

}
