package com.pichicha.reto.app.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pichicha.reto.app.api.config.EnvironmentLoader;
import com.pichicha.reto.app.api.config.hints.MessagesRuntimeHints;
import com.pichicha.reto.app.api.config.hints.PostgresRuntimeHints;
import com.pichicha.reto.app.api.dto.ErrorDetailsDTO;
import com.pichicha.reto.app.api.vo.AuroraPostgresSecretVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@ImportRuntimeHints({PostgresRuntimeHints.class, MessagesRuntimeHints.class})
@RegisterReflectionForBinding({AuroraPostgresSecretVO.class, ErrorDetailsDTO.class})
public class RetoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(RetoApplication.class);

	public static void main(String[] args) throws JsonProcessingException {
		LOGGER.debug("main() - START");
		EnvironmentLoader.load();
		SpringApplication.run(RetoApplication.class, args);
		LOGGER.debug("main() - END");
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding(StandardCharsets.ISO_8859_1.name());
		return messageSource;
	}

}
