package io.github.llnancy.dailyhot.config;

import io.github.llnancy.dailyhot.entity.R;
import io.github.llnancy.dailyhot.enums.PlatformEnum;
import io.github.llnancy.dailyhot.factory.DailyHotServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * router function config
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/2
 */
@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final DailyHotServiceFactory serviceFactory;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return Arrays.stream(PlatformEnum.values())
                .map(this::createRoute)
                .reduce(RouterFunction::and)
                .orElseThrow(IllegalStateException::new);
    }

    private RouterFunction<ServerResponse> createRoute(PlatformEnum platformEnum) {
        return RouterFunctions.route(
                RequestPredicates.method(HttpMethod.GET)
                        .and(RequestPredicates.path("/" + platformEnum)),
                request -> handleEndpoint(platformEnum)
        );
    }

    private Mono<ServerResponse> handleEndpoint(PlatformEnum platformEnum) {
        return ServerResponse.ok()
                .body(
                        BodyInserters.fromPublisher(
                                Mono.justOrEmpty(serviceFactory.fetchDailyHotService(platformEnum))
                                        .flatMap(service -> service.dailyHot(platformEnum).collectList())
                                        .map(data -> R.builder().code(200)
                                                .message("success")
                                                .requestTime(LocalDateTime.now())
                                                .data(data)
                                                .build()
                                        ),
                                ParameterizedTypeReference.forType(Flux.class)
                        )
                );
    }
}
