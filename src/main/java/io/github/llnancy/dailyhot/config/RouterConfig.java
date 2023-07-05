package io.github.llnancy.dailyhot.config;

import io.github.llnancy.dailyhot.entity.BaiduData;
import io.github.llnancy.dailyhot.entity.BiliBiliData;
import io.github.llnancy.dailyhot.entity.ItHomeData;
import io.github.llnancy.dailyhot.entity.JueJinData;
import io.github.llnancy.dailyhot.entity.Kr36Data;
import io.github.llnancy.dailyhot.entity.NewsQQData;
import io.github.llnancy.dailyhot.entity.R;
import io.github.llnancy.dailyhot.entity.SSPaiData;
import io.github.llnancy.dailyhot.entity.ThePaperData;
import io.github.llnancy.dailyhot.entity.TieBaData;
import io.github.llnancy.dailyhot.entity.TouTiaoData;
import io.github.llnancy.dailyhot.entity.WeReadData;
import io.github.llnancy.dailyhot.entity.WeiboData;
import io.github.llnancy.dailyhot.entity.ZhiHuData;
import io.github.llnancy.dailyhot.enums.PlatformEnum;
import io.github.llnancy.dailyhot.factory.DailyHotServiceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
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
@Slf4j
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
                )
                .filter(debugLoggingFilter());
    }

    @RegisterReflectionForBinding({
            R.class,
            BaiduData.class,
            BiliBiliData.class,
            ItHomeData.class,
            JueJinData.class,
            Kr36Data.class,
            NewsQQData.class,
            SSPaiData.class,
            ThePaperData.class,
            TieBaData.class,
            TouTiaoData.class,
            WeiboData.class,
            WeReadData.class,
            ZhiHuData.class
    })
    private Mono<ServerResponse> handleEndpoint(PlatformEnum platformEnum) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        Mono.justOrEmpty(serviceFactory.fetchDailyHotService(platformEnum))
                                .flatMap(service -> service.dailyHot(platformEnum).collectList())
                                .map(data -> R.builder()
                                        .code(HttpStatus.OK.value())
                                        .message(HttpStatus.OK.getReasonPhrase())
                                        .requestTime(LocalDateTime.now())
                                        .data(data)
                                        .build()
                                ),
                        R.class
                );
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> debugLoggingFilter() {
        return (request, next) -> {
            if (log.isDebugEnabled()) {
                log.debug("Received Request: {}", request);
            }
            return next.handle(request)
                    .doOnSuccess(response -> {
                        if (log.isDebugEnabled()) {
                            log.debug("Response for {}: {}, headers: {}", request, response.statusCode(), response.headers());
                        }
                    });
        };
    }
}
