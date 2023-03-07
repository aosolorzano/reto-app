package com.pichicha.reto.app.api.config.hints;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

public class MessagesRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        hints.resources().registerPattern("messages.properties");
        hints.resources().registerPattern("messages_en.properties");
    }
}
