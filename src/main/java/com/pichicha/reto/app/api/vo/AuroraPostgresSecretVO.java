package com.pichicha.reto.app.api.vo;

public record AuroraPostgresSecretVO(String host, String port, String dbname,
                                     String username, String password,
                                     String engine, String dbClusterIdentifier) {
}
