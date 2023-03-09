package com.pichicha.reto.app.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pichicha.reto.app.api.vo.AuroraPostgresSecretVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public final class EnvUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnvUtil.class);

    private EnvUtil() {
        // Empty constructor.
    }

    public static AuroraPostgresSecretVO getAuroraSecretVO() throws JsonProcessingException {
        String auroraSecret = System.getenv("RETO_DB_CLUSTER_SECRET");
        if (Objects.isNull(auroraSecret) || auroraSecret.isBlank()) {
            LOGGER.warn("RETO_DB_CLUSTER_SECRET not found. Using defaults.");
            return null;
        }
        return new ObjectMapper().readValue(auroraSecret, AuroraPostgresSecretVO.class);
    }

    public static String getTimeZoneId() {
        String timeZoneId = System.getenv("RETO_TIME_ZONE_ID");
        if (Objects.isNull(timeZoneId) || timeZoneId.isBlank()) {
            LOGGER.warn("RETO_TIME_ZONE_ID not found. Using defaults.");
            return null;
        }
        return timeZoneId;
    }
}
