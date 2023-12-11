package com.github.jarvvski.wisestart.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OnApplicationStart {

    private final ApplicationContext applicationContext;

    public OnApplicationStart(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void listener() {
        var clazz = this.applicationContext.getBeanDefinitionNames();
        log.info("(set a breakpoint here and explore applicationContext methods)");
    }
}
