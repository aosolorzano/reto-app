package com.pichicha.reto.app.api.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pichicha.reto.app.api.utils.EnvironmentUtil;
import com.pichicha.reto.app.api.vo.AuroraPostgresSecretVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Objects;

public final class EnvironmentLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentLoader.class);
    private static final String JDBC_SQL_CONNECTION = "jdbc:postgresql://{0}:{1}/{2}";

    private EnvironmentLoader() {
        // Private constructor.
    }

    public static void load() throws JsonProcessingException {
        settingJdbcConnection();
        settingDefaultTimeZone();
    }

    private static void settingJdbcConnection() throws JsonProcessingException {
        AuroraPostgresSecretVO auroraSecretVO = EnvironmentUtil.getAuroraSecretVO();
        if (Objects.nonNull(auroraSecretVO)) {
            String sqlConnection = MessageFormat.format(JDBC_SQL_CONNECTION, auroraSecretVO.host(),
                    auroraSecretVO.port(), auroraSecretVO.dbname());
            LOGGER.debug("JDBC Connection found: {}", sqlConnection);
            System.setProperty("spring.datasource.url", sqlConnection);
            System.setProperty("spring.datasource.username", auroraSecretVO.username());
            System.setProperty("spring.datasource.password", auroraSecretVO.password());
        }
    }

    private static void settingDefaultTimeZone() {
        String timeZoneId = EnvironmentUtil.getTimeZoneId();
        if (Objects.nonNull(timeZoneId)) {
            LOGGER.debug("Time Zone ID found: {}", timeZoneId);
            System.setProperty("reto.time.zone.id", timeZoneId);
        }
    }
}
