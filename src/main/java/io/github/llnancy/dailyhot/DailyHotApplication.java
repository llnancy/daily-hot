package io.github.llnancy.dailyhot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.reactive.function.server.RouterFunction;

@SpringBootApplication
@Slf4j
public class DailyHotApplication {

    public static void main(String[] args) {
        GenericApplicationContext context = (GenericApplicationContext) SpringApplication.run(DailyHotApplication.class, args);
        printRegisteredRoutes(context);
    }

    public static void printRegisteredRoutes(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanNamesForType(RouterFunction.class);
        for (String beanName : beanNames) {
            RouterFunction<?> routerFunction = applicationContext.getBean(beanName, RouterFunction.class);
            if (log.isDebugEnabled()) {
                log.debug("Router Function: \n{}", routerFunction);
            }
        }
    }
}
