package io.github.llnancy.dailyhot.client;

import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

/**
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2023/7/1
 */
public interface BasePostExchangeClient<BODY, R> {

    Mono<R> dailyHot(@RequestBody BODY body);
}
